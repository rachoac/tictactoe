import React from 'react';
import Flux from 'flux';
import $ from 'jquery';

var Dispatcher = require('../Dispatcher');
var Emitter = require('../Emitter');
var Main = require('../components/Main.js');

var LobbyStore = function() {
    Dispatcher.register( function(payload) {
        switch (payload.type) {
            case "load-player-list" : {
                this._loadPlayerList();
                break;
            }

            case "join-player" : {
                this._joinPlayer(payload.playerName);
                break;
            }

            case "leave-player" : {
                this._leavePlayer(payload.playerName);
                break;
            }

            case "challenge-player" : {
                this._challengePlayer( payload.challengedPlayer );
                break;
            }

            case "challenge-cancel" : {
                this._challengeCancel();
                break;
            }

            case "challenge-status" : {
                this._challengeStatus();
                break;
            }

            case "active-challenge" : {
                this._activeChallenge();
                break;
            }

            case "active-challenge-status" : {
                this._activeChallenge();
                break;
            }

            case "active-challenge-accept" : {
                this._activeChallengeResponse(true);
                break;
            }
            case "active-challenge-reject" : {
                this._activeChallengeResponse(false);
                break;
            }
        }

    }.bind(this) );

    this._loadPlayerList = function() {
        $.get('http://localhost:9090/lobby/roster', function(players) {
            this._notify(players);
        }.bind(this));
    };

    this._joinPlayer = function(playerName) {
        $.post('http://localhost:9090/lobby/roster?playerName=' + playerName, function(response) {
            this._doneJoinedPlayer(playerName);
        }.bind(this));
    };

    this._leavePlayer = function(playerName) {
        var self = this;
        $.ajax({
            url: 'http://localhost:9090/lobby/roster?playerName=' + playerName,
            type: 'DELETE',
            success: function(response) {
                this.playerName = undefined;
                this.challenge = undefined;
                this.activeChallenge = undefined;
                this._doneLeavePlayer(playerName);
            }.bind(self)
        });
    };

    this._doneJoinedPlayer = function(playerName) {
        this.playerName = playerName;
        React.render(<Main visible='roster' username={playerName}/>, document.getElementById('main'));
        Dispatcher.dispatch({ type: "load-player-list" });
    };

    this._doneLeavePlayer = function(playerName) {
        React.render(<Main visible='join'/>, document.getElementById('main'));
    };

    this._challengePlayer = function(challengedPlayer ) {
        var self = this;

        $.post('http://localhost:9090/lobby/roster/' + challengedPlayer + '/challenge?challengerPlayerName=' + this.playerName, function(response) {
            self.challenge = response;
            React.render(<Main visible='challenge'/>, document.getElementById('main'));
        });
    };

    this._challengeCancel = function( ) {
        var self = this;
        $.ajax({
            url: 'http://localhost:9090/lobby/challenge/' + self.challenge.challengeID,
            type: 'DELETE',
            success: function(response) {
                self.challenge = undefined;
                React.render(<Main visible='roster'/>, document.getElementById('main'));
            }.bind(self)
        });
    };

    this._challengeStatus = function() {
        if ( self.challenge ) {
            $.get('http://localhost:9090/lobby/challenge/' + self.challenge.challengeID, function(challenge) {
                if ( challenge.challengeStatus === 'accepted' ) {
                    self.challenge = undefined;
                    React.render(<Main visible='gameboard'/>, document.getElementById('main'));
                }
                if ( challenge.challengeStatus === 'rejected' || challenge.challengeExpired ) {
                    self._challengeCancel();
                    return;
                }
            }.bind(this))
            .fail(function() {
                self.challenge = undefined;
                React.render(<Main/>, document.getElementById('main'));
            });
        }
    };

    this._activeChallenge = function() {
        var self = this;
        $.get('http://localhost:9090/lobby/roster/' + self.playerName + '/challenge', function(challenge) {
            self.activeChallenge = challenge;
            React.render(<Main/>, document.getElementById('main'));
        }.bind(this))
        .fail(function() {
            self.activeChallenge = undefined;
            React.render(<Main/>, document.getElementById('main'));
        });
    };

    this._activeChallengeResponse = function(accepted) {
        var self = this;
        $.ajax({
            url: 'http://localhost:9090/lobby/challenge/' + self.activeChallenge.challengeID + '?response=' + (accepted ? 'accepted' : 'rejected'),
            type: 'PUT',
            success: function(response) {
                self.challenge = undefined;
                self.activeChallenge = undefined;

                if ( response.challengeStatus == 'accepted' ) {
                    React.render(<Main visible='gameboard'/>, document.getElementById('main'));
                }

                if ( response.challengeStatus == 'rejected' ) {
                    alert('rejected');
                    React.render(<Main visible='roster'/>, document.getElementById('main'));
                }

            }.bind(self)
        });
    };

    this._notify = function(players) {
        Emitter.emit("player-list-changed", players);
    };

    var self = this;
    this.getPlayer =  function() {
        return self.playerName;
    };

    this.getChallengedPlayer =  function() {
        return self.challenge ? self.challenge.challengedPlayer : undefined;
    };

    this.getChallenge =  function() {
        return self.challenge;
    };

    this.getActiveChallenge =  function() {
        return self.activeChallenge;
    };
};

module.exports = new LobbyStore();
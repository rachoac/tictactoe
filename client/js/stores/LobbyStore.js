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
                this.challengedPlayer = undefined;
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
        this.challengedPlayer = challengedPlayer;

        $.post('http://localhost:9090/lobby/roster/' + this.challengedPlayer + '/challenge?challengerPlayerName=' + this.playerName, function(response) {
            React.render(<Main visible='challenge'/>, document.getElementById('main'));
        });
    }

    this._challengeCancel = function(challengedPlayer ) {
        this.challengedPlayer = undefined;
        React.render(<Main visible='roster'/>, document.getElementById('main'));
    }

    this._notify = function(players) {
        Emitter.emit("player-list-changed", players);
    };

    var self = this;
    this.getPlayer =  function() {
        return self.playerName;
    }

    this.getChallengedPlayer =  function() {
        debugger;
        return self.challengedPlayer;
    }
};

module.exports = new LobbyStore();
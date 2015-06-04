import React from 'react';
var Dispatcher = require('../Dispatcher');
var Emitter = require('../Emitter');
var GameBoardCell = require('./GameBoardCell');
var GameBoardStatus = require('./GameBoardStatus');
var Game = require('../Game');
var cx = require('classnames');

export default class ChallengeInterface extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            myTurn: undefined
        }
    }

    componentDidMount() {
        var self = this;
        this.daemon = setInterval( function() {

            Dispatcher.dispatch( { type: 'update-game-state'} );

        }, 1000);

        Emitter.on("game-state-changed", function(gameStatus) {
            if ( gameStatus.winner || (gameStatus.match && gameStatus.state === 'stalemate') ) {
                // all done
                console.log("Winner detected, killing state check daemon");

                clearInterval( this.daemon );
                self.setState( {
                    winner : gameStatus.winner,
                    stopped : gameStatus.match && (gameStatus.match.state === 'stopped' || gameStatus.state === 'stalemate')
                });
            }
        }.bind(this) );
    }

    componentWillUnmount() {
        clearInterval(this.daemon);
    }

    doBackToLobby() {
        Dispatcher.dispatch( { type: 'back-to-lobby'} );
    }

    doQuitGame() {
        Dispatcher.dispatch( { type: 'quit-game'} );
    }

    render() {
        var self = this;

        var lobbyButtonCssClasses = cx({
            hidden : !self.state.winner && !self.state.stopped
        });

        var quitButtonCssClasses = cx({
            hidden : self.state.winner || self.state.stopped
        });

        var backToLobby = function() {
            self.doBackToLobby();
        };
        var quitGame = function() {
            self.doQuitGame();
        };

        return (
            <div id="board">
                <GameBoardStatus status={status}/>
                <button className={lobbyButtonCssClasses} onClick={backToLobby}>Lobby</button>
                <button className={quitButtonCssClasses} onClick={quitGame}>Quit</button>

                <table>
                    <tr id="row1">
                        <GameBoardCell x='0' y='0'/>
                        <GameBoardCell x='1' y='0' v='true'/>
                        <GameBoardCell x='2' y='0' />
                    </tr>
                    <tr id="row2">
                        <GameBoardCell x='0' y='1' h='true'/>
                        <GameBoardCell x='1' y='1' h='true' v='true'/>
                        <GameBoardCell x='2' y='1' h='true'/>
                    </tr>
                    <tr id="row3">
                        <GameBoardCell x='0' y='2' />
                        <GameBoardCell x='1' y='2' v='true'/>
                        <GameBoardCell x='2' y='2' />
                    </tr>
                </table>
            </div>
        );
    }
}

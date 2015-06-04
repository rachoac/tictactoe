import React from 'react';

var Dispatcher = require('../Dispatcher');
var Emitter = require('../Emitter');

var Game = require('../Game');

export default class GameBoardStatus extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        var self = this;

        Emitter.on("game-state-changed", function(gameStatus) {
            console.log(gameStatus );
            var myTurn = Game.isMyTurn();
            self.setState({
                myTurn : myTurn ? "true" : "false",
                winner : gameStatus.winner,
                stalemate : gameStatus.match && gameStatus.state === 'stalemate',
                stopped : gameStatus.match && gameStatus.match.state === 'stopped'
            })
        }.bind(this) );
    }

    componentWillUnmount() {
    }

    render() {
        var status;
        if ( this.state.stalemate) {
            status = "GAME OVER! Stalemate. No one wins.";
        } else if ( this.state.stopped) {
            status = "GAME OVER! Your opponent quit.";
        } else if (this.state.winner ) {
            status = "GAME OVER! Winner: " + this.state.winner;
        } else {
            var symbol = Game.getSymbolForPlayer( require('../stores/LobbyStore').getPlayer() );
            status = this.state.myTurn ? ("true" === this.state.myTurn ? "Your turn (" + symbol + ")!" : "His turn ...") : "Calculating status...";
        }

        return (
            <div>
                {status}
            </div>
        );
    }


}
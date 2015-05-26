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
            var myTurn = Game.isMyTurn();
            self.setState({
                myTurn : myTurn ? "true" : "false",
                winner : gameStatus.winner
            })
        }.bind(this) );
    }

    componentWillUnmount() {
    }

    render() {
        var status;
        if ( this.state.winner ) {
            status = "GAME OVER! Winner: " + this.state.winner;
        } else {
            status = this.state.myTurn ? ("true" === this.state.myTurn ? "Your turn" : "His turn") : "Calculating status...";
        }

        return (
            <div>
                {status}
            </div>
        );
    }


}
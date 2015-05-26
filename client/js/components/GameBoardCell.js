import React from 'react';
var Dispatcher = require('../Dispatcher');
var Emitter = require('../Emitter');
var cx = require('classnames');

var Game = require('../Game');

export default class GameBoardCell extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        var self = this;

        Emitter.on("game-state-changed", function(gameStatus) {
            var myTurn = Game.isMyTurn();
            self.setState({
                myTurn : myTurn ? "true" : "false"
            });

            var boardData = gameStatus.boardData;

            var cellID = self.props.x + '_' + self.props.y;
            var cellPlayer = boardData[cellID];
            if ( cellPlayer ) {
                // whose mark is it?
                var symbol = Game.getSymbolForPlayer( cellPlayer );
                this.state.player = symbol;
            }

        }.bind(this) );
    }

    componentWillUnmount() {
    }

    doClick() {
        if ( this.state.myTurn === 'true' ) {
            var mySymbol = Game.getMySymbol();
            this.state.player = mySymbol;

            Dispatcher.dispatch( { type: 'perform-move', x: this.props.x, y : this.props.y } );
        }
    }

    render() {
        var self = this;

        var player = this.state.player;
        var classes = {
            square : true,
            v : this.props.v,
            h : this.props.h
        };

        if ( this.state.player ) {
            classes[' player' + this.state.player] = true;
            player = this.state.player;
        }

        var click = function() {
            self.doClick();
        }

        var className = cx(classes);

        return (
            <td className={className} onClick={click}>{player}</td>
        );
    }
}

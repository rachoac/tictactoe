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
                myTurn : myTurn ? "true" : "false",
                winner : gameStatus.winner
            });

            var boardData = gameStatus.boardData;

            var cellID = self.props.x + '_' + self.props.y;
            var cellPlayer = boardData[cellID];
            if ( cellPlayer ) {
                // whose mark is it?
                this.state.player = Game.getSymbolForPlayer(cellPlayer);
            }

            if ( gameStatus.winner ) {

                for ( var i = 0; i < gameStatus.winningCells.length; i++ ) {
                    var cell = gameStatus.winningCells[i];
                    if ( cell === cellID ) {
                        this.setState({
                            selectable: false,
                            won: true
                        });
                        break;
                    }
                }

             }

        }.bind(this) );
    }

    componentWillUnmount() {
    }

    doClick() {
        if ( !this.state.winner && this.state.myTurn === 'true' && !this.state.player ) {
            var mySymbol = Game.getMySymbol();
            this.setState({
                player: mySymbol
            });
            this.state.player = mySymbol;

            Dispatcher.dispatch( { type: 'perform-move', x: this.props.x, y : this.props.y } );
        }
    }

    doHover() {
        if ( !this.state.winner && this.state.myTurn === 'true' && !this.state.player ) {
            var mySymbol = Game.getMySymbol();
            this.setState({
                selectable: true,
                preview: mySymbol
            })
        }
    }

    doRemoveHover() {
        this.setState({
            selectable: false,
            preview: ""
        });
    }

    render() {
        var self = this;

        var player = this.state.player;
        var classes = {
            square : true,
            v : this.props.v,
            h : this.props.h,
            selectable : this.state.selectable,
            won : this.state.won
        };

        if ( this.state.player || this.state.preview ) {
            classes[' player' + this.state.player || this.state.preview] = true;
            player = this.state.player ? this.state.player : this.state.preview;
        }

        var className = cx(classes);

        return (
            <td className={className}
                onMouseOver={this.doHover.bind(this)}
                onMouseOut={this.doRemoveHover.bind(this)}
                onClick={this.doClick.bind(this)}>{player}</td>
        );
    }
}

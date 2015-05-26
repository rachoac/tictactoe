import React from 'react';
var Dispatcher = require('../Dispatcher');
var Emitter = require('../Emitter');
var GameBoardCell = require('./GameBoardCell');
var GameBoardStatus = require('./GameBoardStatus');
var Game = require('../Game');

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

       
    }

    componentWillUnmount() {
        clearInterval(this.daemon);
    }

    render() {
        var self = this;

        return (
            <div id="board">
                <GameBoardStatus status={status}/>

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

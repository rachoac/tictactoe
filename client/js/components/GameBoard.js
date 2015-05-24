import React from 'react';
var Dispatcher = require('../Dispatcher');
var GameBoardCell = require('./GameBoardCell');
var GameBoardStatus = require('./GameBoardStatus');

export default class ChallengeInterface extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        var self = this;
    }

    componentWillUnmount() {
    }

    render() {
        var self = this;
        return (
            <div id="board">
                <GameBoardStatus />

                <table>
                    <tr id="row1">
                        <GameBoardCell/>
                        <GameBoardCell v='true'/>
                        <GameBoardCell/>
                    </tr>
                    <tr id="row2">
                        <GameBoardCell h='true'/>
                        <GameBoardCell h='true' v='true' player='X'/>
                        <GameBoardCell h='true'/>
                    </tr>
                    <tr id="row3">
                        <GameBoardCell/>
                        <GameBoardCell v='true'/>
                        <GameBoardCell/>
                    </tr>
                </table>
            </div>
        );
    }
}

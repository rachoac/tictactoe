import React from 'react';

var Dispatcher = require('../Dispatcher');

export default class Player extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            player : props.player
        }
    }

    doChallenge() {
        Dispatcher.dispatch( { type: 'challenge-player', challengedPlayer : this.props.player.playerName });
    }

    render() {
        var challenge = function() {
            this.doChallenge();
        }.bind(this);

        var challengeButton = this.props.canChallenge ? <button onClick={challenge}>Challenge</button> : '';

        return (
            <li>
                {this.state.player.playerName}
                <br/>
                {challengeButton}
            </li>
        );
    }
}

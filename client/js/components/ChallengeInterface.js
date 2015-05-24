import React from 'react';
var Dispatcher = require('../Dispatcher');
var Main = require('./Main.js');

export default class ChallengeInterface extends React.Component {
    constructor(props) {
        super(props);
    }

    doCancel() {
        Dispatcher.dispatch({ type: "challenge-cancel" });
    }

    componentDidMount() {
        var self = this;
        self.daemon = setInterval( function() {
            var hasChallenge = require('../stores/LobbyStore').getChallenge();
            if ( hasChallenge ) {
                console.log('Checking...');
                Dispatcher.dispatch({ type: "challenge-status" });
            } else {
                clearInterval(self.daemon);
            }
        }, 1000);
    }

    componentWillUnmount() {
        console.log('... all done');
        clearInterval(this.daemon);
    }

    render() {
        var self = this;
        var cancel = function() {
            self.doCancel();
        };

        return (
            <div>
                Waiting for the player to respond ...
                <button onClick={cancel}>Cancel</button>
            </div>
        );
    }
}

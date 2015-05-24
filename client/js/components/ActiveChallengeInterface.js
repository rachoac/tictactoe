import React from 'react';
var Dispatcher = require('../Dispatcher');
var Main = require('./Main.js');

export default class ActiveChallengeInterface extends React.Component {
    constructor(props) {
        super(props);
    }

    doAccept() {
        Dispatcher.dispatch({ type: "active-challenge-accept" });
    }

    doReject() {
        Dispatcher.dispatch({ type: "active-challenge-reject" });
    }

    componentDidMount() {
        var self = this;
        self.daemon = setInterval( function() {
            var hasChallenge = require('../stores/LobbyStore').getActiveChallenge();
            if ( hasChallenge ) {
                console.log('Checking...');
                Dispatcher.dispatch({ type: "active-challenge-status" });
            } else {
                debugger;
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
        var accept = function() {
            self.doAccept();
        };
        var reject = function() {
            self.doReject();
        };

        return (
            <div>
                A player challenged you!!
                <button onClick={accept}>Accept</button>
                <button onClick={reject}>Reject</button>
            </div>
        );
    }
}

import React from 'react';
var Dispatcher = require('../Dispatcher');

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
            <div>
                Game!
            </div>
        );
    }
}

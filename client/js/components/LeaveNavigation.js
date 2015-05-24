import React from 'react';
var Dispatcher = require('../Dispatcher');

export default class LeaveNavigation extends React.Component {
    constructor(props) {
        super(props);
    }

    doLeave() {
        var username = this.props.username;
        Dispatcher.dispatch({ type: "leave-player", playerName: username });
    }

    render() {
        var leave = function() {
            this.doLeave();
        }.bind(this);

        return (
            <a href='#' onClick={leave}>Leave</a>
        );
    }
}

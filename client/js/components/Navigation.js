import React from 'react';

var LeaveNavigation = require('./LeaveNavigation.js');

export default class Navigation extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <LeaveNavigation username={this.props.username}/>
            </div>
        );
    }
}
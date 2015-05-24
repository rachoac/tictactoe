import React from 'react';
var Dispatcher = require('../Dispatcher');
var Main = require('./Main.js');

export default class JoinInterface extends React.Component {
    constructor(props) {
        super(props);
    }

    doSubmit() {
        var username = this.refs.username.getDOMNode().value;
        if ( username ) {
            Dispatcher.dispatch({ type: "join-player", playerName: username });
        } else {
            React.render(<Main visible='roster'/>, document.getElementById('main'));
        }
    }

    componentDidMount() {
        this.refs.username.getDOMNode().focus();
    }

    componentDidUpdate() {
        this.refs.username.getDOMNode().focus();
    }

    render() {
        var self = this;
        var submit = function() {
            self.doSubmit();
        };

        return (
            <div>
                <span>Username</span>
                <br/>
                <input type='text' ref='username'/>
                <br/>
                <button onClick={submit}>Submit</button>
            </div>
        );
    }
}

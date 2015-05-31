import React from 'react';
var Dispatcher = require('../Dispatcher');
var Main = require('./Main.js');
var Cookie = require('react-cookie');

export default class JoinInterface extends React.Component {
    constructor(props) {
        super(props);
    }

    doSubmit() {
        var username = this.refs.username.getDOMNode().value;
        if ( username ) {
            Cookie.save('username', username);
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

        return (
            <div>
                <span>Username</span>
                <br/>
                <input type='text' ref='username' defaultValue={Cookie.load('username')}/>
                <br/>
                <button onClick={this.doSubmit.bind(this)}>Submit</button>
            </div>
        );
    }
}

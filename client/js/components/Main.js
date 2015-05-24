import React from 'react';
var Lobby = require('./Lobby.js');
var JoinInterface = require('./JoinInterface.js');

export default class Main extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        var username = this.props.username;
        var visible = this.props.visible;
        var roster = <Lobby username={username}/>;
        var join = <JoinInterface/>;

        var toShow = ( !visible || visible == 'join' ) ? join : roster;

        return (
            <div>
                {toShow}
            </div>
        );
    }

}
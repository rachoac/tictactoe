import React from 'react';

var Navigation = require('./Navigation.js');
var PlayerList = require('./PlayerList.js');

export default class Lobby extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            players : props.players
        }
    }

    render() {
        return (
            <div>
                <Navigation username={this.props.username}/>
                <PlayerList players={this.state.players}/>
            </div>
        );
    }
}

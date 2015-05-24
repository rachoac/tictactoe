import React from 'react';

var Dispatcher = require('../Dispatcher');
var Emitter = require('../Emitter');
var Player = require('./Player.js');

export default class PlayerList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            players : props.players || []
        }
    }

    componentWillMount() {
        Emitter.on("player-list-changed", function(players) {
            this.setState({ players: players });
        }.bind(this) );
    }

    componentDidMount() {
        console.log("Started roster updater.");
        this.daemon = setInterval( function() {
            Dispatcher.dispatch({ type: "load-player-list" });
        }, 1000 );

        Dispatcher.dispatch({ type: "load-player-list" });
    }

    componentWillUnmount() {
        console.log('Stopped roster updater.');
        clearInterval(this.daemon);
    }

    render() {
        var thisPlayer = this.props.username;
        var players = this.state.players.map(function (player) {
            return (
                <Player player={player} key={player.playerName} canChallenge={ thisPlayer !== player.playerName }/>
            );
        });

        return (
            <ul>
                {players}
            </ul>
        );
    }
}

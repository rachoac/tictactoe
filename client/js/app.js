import React from 'react';
import Flux from 'flux';
import $ from 'jquery';
import Events from 'events';

var Dispatcher = new Flux.Dispatcher();
var Emitter = new Events.EventEmitter();

class Player extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            player : props.player
        }
    }

    render() {
        return (
            <li>{this.state.player.playerName}</li>
        );
    }
}

class PlayerList extends React.Component {
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
        Dispatcher.dispatch({ type: "load-player-list" });
    }

    render() {
        var players = this.state.players.map(function (player) {
            return (
                <Player player={player} key={player.playerName}/>
            );
        });

        return (
            <ul>
                {players}
            </ul>
        );
    }
}

class Lobby extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            players : props.players
        }
    }

    render() {

        return (
            <div>
                <PlayerList players={this.state.players}/>
            </div>
        );
    }
}

var LobbyStore = function() {
    Dispatcher.register( function(payload) {
        switch (payload.type) {
            case "load-player-list" : {
                this._loadPlayerList();
                break;
            }
        }

    }.bind(this) );

    this._loadPlayerList = function() {
        $.get('http://localhost:9090/lobby/players', function(players) {
            this._notify(players);
        }.bind(this));
    };

    this._notify = function(players) {
        Emitter.emit("player-list-changed", players);
    }

}

var lobbyStore = new LobbyStore();

React.render(<Lobby/>, document.body);

setInterval( function() {
    Dispatcher.dispatch({ type: "load-player-list" });
}, 1000 );
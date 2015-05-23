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
            name : props.name
        }
    }

    render() {
        return (
            <li>{this.state.name}</li>

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
        Emitter.on("load-player-list", function(players) {
            debugger;
            this.setState({ players: players });
        }.bind(this) );
    }

    render() {

        var players = this.state.players.map(function (player) {
            return (
                <Player name={player.name}/>
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
    }

    render() {
        var players = [
            {
                'name' : 'aron'
            },

            {
                'name' : 'sarah'
            },

            {
                'name' : 'samuel'
            },

            {
                'name' : 'adam'
            }
        ];

        return (
            <div>
                <PlayerList players={players}/>
            </div>
        );
    }
}

var LobbyStore = function() {
    debugger;
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
        Emitter.emit("player-list-changed", items);

    }

}

var lobbyStore = new LobbyStore();

React.render(<Lobby/>, document.body);
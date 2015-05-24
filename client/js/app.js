import React from 'react';
import Flux from 'flux';
import $ from 'jquery';
import Events from 'events';

var Dispatcher = new Flux.Dispatcher();
var Emitter = new Events.EventEmitter();

class Navigation extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <JoinNavigation/>
            </div>
        );
    }
}

class JoinInterface extends React.Component {
    constructor(props) {
        super(props);
    }

    componentWillMount() {
        debugger;
    }

    componentDidMount() {
        debugger;
    }

    render() {
        return (
            <input type='text'/>
        );
    }
}

class JoinNavigation extends React.Component {
    constructor(props) {
        super(props);
    }

    doJoin() {
        React.render(<Main visible='join'/>, document.getElementById('main'));
    }

    render() {
        return (
            <a href='#' onClick={this.doJoin}>Join</a>
        );
    }
}

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

class Main extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var visible = this.props.visible;
        if ( !visible || visible == 'roster' ) {
            return (
                <Lobby/>
            );
        }
        if ( visible == 'join' ) {
            return (
                <JoinInterface/>
            );
        }
    }

}

var LobbyStore = function() {
    Dispatcher.register( function(payload) {
        switch (payload.type) {
            case "do-join" : {
                debugger;
                break;
            }
            case "load-player-list" : {
                this._loadPlayerList();
                break;
            }
        }

    }.bind(this) );

    this._loadPlayerList = function() {
        $.get('http://localhost:9090/lobby/roster', function(players) {
            this._notify(players);
        }.bind(this));
    };

    this._notify = function(players) {
        Emitter.emit("player-list-changed", players);
    }
}

var lobbyStore = new LobbyStore();

React.render(<Navigation/>, document.getElementById('navigation'));
React.render(<Main/>, document.getElementById('main'));

setInterval( function() {
    Dispatcher.dispatch({ type: "load-player-list" });
}, 1000 );
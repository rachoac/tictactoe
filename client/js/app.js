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

    doSubmit() {
        var username = this.refs.username.getDOMNode().value;
        Dispatcher.dispatch({ type: "join-player", playerName: username });
    }

    render() {
        var self = this;
        var submit = function() {
            self.doSubmit();
        };

        return (
            <div>
                <input type='text' ref='username'/>
                <button onClick={submit}>Submit</button>
            </div>
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
                <Navigation/>
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
        var roster = <Lobby/>;
        var join = <JoinInterface/>;

        var toShow = ( !visible || visible == 'roster' ) ? roster : join;

        return (
            <div>
                {toShow}
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

            case "join-player" : {
                this._joinPlayer(payload.playerName);
                break;
            }
        }

    }.bind(this) );

    this._loadPlayerList = function() {
        $.get('http://localhost:9090/lobby/roster', function(players) {
            this._notify(players);
        }.bind(this));
    };

    this._joinPlayer = function(playerName) {
        $.post('http://localhost:9090/lobby/roster?playerName=' + playerName, function(response) {
            this._doneJoinedPlayer(response);
        }.bind(this));
    }

    this._doneJoinedPlayer = function(players) {
        React.render(<Main visible='roster'/>, document.getElementById('main'));
        Dispatcher.dispatch({ type: "load-player-list" });
    }

    this._notify = function(players) {
        Emitter.emit("player-list-changed", players);
    }
}

var lobbyStore = new LobbyStore();

React.render(<Main/>, document.getElementById('main'));

setInterval( function() {
    Dispatcher.dispatch({ type: "load-player-list" });
}, 1000 );
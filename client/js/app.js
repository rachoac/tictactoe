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
                <LeaveNavigation username={this.props.username}/>
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

class LeaveNavigation extends React.Component {
    constructor(props) {
        super(props);
    }

    doLeave() {
        var username = this.props.username;
        Dispatcher.dispatch({ type: "leave-player", playerName: username });
    }

    render() {
        var leave = function() {
            this.doLeave();
        }.bind(this);
        return (
            <a href='#' onClick={leave}>Leave</a>
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
                <Navigation username={this.props.username}/>
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

            case "leave-player" : {
                debugger;
                this._leavePlayer(payload.playerName);
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
            this._doneJoinedPlayer(playerName);
        }.bind(this));
    };

    this._leavePlayer = function(playerName) {
        var self = this;
        $.ajax({
            url: 'http://localhost:9090/lobby/roster?playerName=' + playerName,
            type: 'DELETE',
            success: function(response) {
                this._doneLeavePlayer(playerName);
            }.bind(self)
        });
    };

    this._doneJoinedPlayer = function(playerName) {
        React.render(<Main visible='roster' username={playerName}/>, document.getElementById('main'));
        Dispatcher.dispatch({ type: "load-player-list" });
    };

    this._doneLeavePlayer = function(playerName) {
        React.render(<Main visible='join'/>, document.getElementById('main'));
    };

    this._notify = function(players) {
        Emitter.emit("player-list-changed", players);
    };
};

var lobbyStore = new LobbyStore();

React.render(<Main/>, document.getElementById('main'));

setInterval( function() {
    Dispatcher.dispatch({ type: "load-player-list" });
}, 1000 );
import React from 'react';

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
            }
        ];

        return (
            <div>
                <PlayerList players={players}/>
            </div>
        );
    }
}

React.render(<Lobby/>, document.body);
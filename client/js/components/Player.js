import React from 'react';

export default class Player extends React.Component {
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

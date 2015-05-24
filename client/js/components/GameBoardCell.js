import React from 'react';
var Dispatcher = require('../Dispatcher');

export default class GameBoardCell extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        var self = this;
    }

    componentWillUnmount() {
    }

    render() {
        var self = this;

        var className = 'square';
        var player;
        if ( this.props.v ) {
            className += ' v';
        }
        if ( this.props.h ) {
            className += ' h';
        }
        if ( this.props.player ) {
            className += ' player' + this.props.player;
            player = this.props.player;
        }

        return (
            <td className={className}>{player}</td>
        );
    }
}

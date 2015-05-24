import React from 'react';
var Lobby = require('./Lobby.js');
var JoinInterface = require('./JoinInterface.js');
var ChallengeInterface = require('./ChallengeInterface.js');

export default class Main extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        var username = this.props.username || require('../stores/LobbyStore').getPlayer();
        var challengedPlayer = require('../stores/LobbyStore').getChallengedPlayer();
        var visible = this.props.visible;

        if ( challengedPlayer ) {
            toShow = <ChallengeInterface/>;
        } else {
            var toShow;
            switch( visible ) {
                case 'roster' : {
                    toShow = <Lobby username={username}/>;
                    break;
                }
                case 'join' : {
                    toShow = <JoinInterface/>;
                    break;
                }
                case 'challenge' : {
                    toShow = <ChallengeInterface/>;
                    break;
                }
                default:
                    toShow = <JoinInterface/>;
            }
        }

        return (
            <div>
                {toShow}
            </div>
        );
    }

}
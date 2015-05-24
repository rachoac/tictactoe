import React from 'react';
var Lobby = require('./Lobby.js');
var JoinInterface = require('./JoinInterface.js');
var ChallengeInterface = require('./ChallengeInterface.js');
var ActiveChallengeInterface = require('./ActiveChallengeInterface.js');
var GameBoard = require('./GameBoard.js');

export default class Main extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        var username = this.props.username || require('../stores/LobbyStore').getPlayer();
        var challengedPlayer = require('../stores/LobbyStore').getChallengedPlayer();
        var activeChallenge  = require('../stores/LobbyStore').getActiveChallenge();
        var visible = this.props.visible;

        if ( challengedPlayer ) {
            toShow = <ChallengeInterface/>;
        } else {
            if ( activeChallenge ) {
                toShow = <ActiveChallengeInterface/>;
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
                    case 'challenge' : {
                        toShow = <ChallengeInterface/>;
                        break;
                    }
                    case 'gameboard' : {
                        toShow = <GameBoard/>;
                        break;
                    }
                    default:
                        if ( username ) {
                            toShow = <Lobby username={username}/>;
                        } else {
                            toShow = <JoinInterface/>;
                        }
                }
            }

        }

        return (
            <div>
                {toShow}
            </div>
        );
    }

}
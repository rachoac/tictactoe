import React from 'react';
import Flux from 'flux';
import $ from 'jquery';

var Dispatcher = require('./Dispatcher');
var Emitter = require('./Emitter');
var Main = require('./components/Main.js');
var LobbyStore = require('./stores/LobbyStore.js');

React.render(<Main/>, document.getElementById('main'));

setInterval( function() {
    Dispatcher.dispatch({ type: "load-player-list" });
}, 1000 );
function Game() {
    this.state = 'stopped';
}

Game.prototype.isStopped = function() {
    return this.state === 'stopped';
};

Game.prototype.isActive = function() {
    return this.state === 'active';
};

Game.prototype.reset = function(matchID) {
    this.state = 'active';
    this.matchID = matchID;
};

Game.prototype.isMyTurn = function() {
    return this.isActive()
        && this.status != null
        && require('./stores/LobbyStore').getPlayer() === this.status.turnOwner;
};

Game.prototype.getMatchID = function() {
    return this.matchID;
};

Game.prototype.getMySymbol = function() {
    if ( !this.isActive() || this.status == null ) {
        return undefined;
    }

    return require('./stores/LobbyStore').getPlayer() === this.status.match.challengerPlayer?
        'X' : 'O'

};

Game.prototype.getSymbolForPlayer = function(cellPlayer) {
    if ( !this.isActive() || this.status == null ) {
        return undefined;
    }

    return cellPlayer === this.status.match.challengerPlayer?
        'X' : 'O'
};

Game.prototype.setServerStatus = function(status ) {
    this.status = status;
};

module.exports = new Game();
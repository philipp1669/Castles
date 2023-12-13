package com.gutscheweb.castles.game;

public class GameStateManager {
    private GameState currentState;

    public GameStateManager() {
        this.currentState = GameState.LOBBY;
    }

    public void setGameState(GameState state) {
        this.currentState = state;
    }

    public GameState getGameState() {
        return this.currentState;
    }
}

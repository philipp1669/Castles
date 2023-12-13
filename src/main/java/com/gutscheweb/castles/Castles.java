package com.gutscheweb.castles;

import com.gutscheweb.castles.game.GameState;
import com.gutscheweb.castles.game.GameStateManager;
import com.gutscheweb.castles.loader.CommandLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Castles extends JavaPlugin {

    public static Castles instance;
    private GameStateManager gameStateManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        gameStateManager = new GameStateManager();
        CommandLoader commandLoader = new CommandLoader("com.gutscheweb.castles.command");
        commandLoader.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void startGame() {
        gameStateManager.setGameState(GameState.LOBBY);
    }
}

package com.gutscheweb.castles.command;

import com.gutscheweb.castles.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StartGameCommand extends Command {
    public StartGameCommand() {
        super("start", "starts the game", "/start", List.of("s"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Game.startCountDown(player, 10);
        }
        return false;
    }
}

package com.gutscheweb.castles.command;

import com.gutscheweb.castles.util.SpawnUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StartGameCommand extends Command {
    protected StartGameCommand(@NotNull String name) {
        super("start", "starts the game", "/start", List.of("s"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        World world = Bukkit.getWorld("void");
        //SpawnUtil.placeBarrier();
        return false;
    }
}

package com.gutscheweb.castles.command;

import com.gutscheweb.castles.game.Map;
import com.gutscheweb.castles.util.SchematicHandler;
import com.gutscheweb.castles.util.SpawnUtil;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static com.gutscheweb.castles.Castles.instance;

public class LoadMapCommand extends Command {
    public LoadMapCommand() {
        super("loadmap", "loads the map (CANNOT BE REVERTED)", "/lm", List.of("lm"));
    }

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(mm.deserialize("<red>You must be a player to execute that command!</red>"));
            return false;
        }

        String lastArg;

        if (strings.length == 0) lastArg = "48";
        else
            lastArg = strings[strings.length - 1];
        int distance;

        try {
            distance = Integer.parseInt(lastArg);
        }
        catch(NumberFormatException e) {
            distance = 0;
        }

        if (distance < 0 || distance >= 1024) {
            commandSender.sendMessage(mm.deserialize("<red>Invalid distance, set to 48</red>"));
            distance = 48;
        }

        Player player = (Player) commandSender;

        Location center = new Location(
                player.getWorld(),
                0, 64, 0
        );


        double castleRadius = 24.5, smallIslandRadius = 11.5;
        Location[] spawns = new Location[]{
                center.clone().add((smallIslandRadius + castleRadius) * -1, 0, 0),
                center.clone().add(smallIslandRadius + castleRadius, 0, 0)
        };

        Map map = new Map(distance, center, spawns);
        map.loadMap();
        map.placeBarrier(Material.BARRIER);

        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> map.placeBarrier(Material.AIR), 200L);

        return false;
    }
}

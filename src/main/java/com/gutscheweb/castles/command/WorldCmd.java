package com.gutscheweb.castles.command;

import com.gutscheweb.castles.Castles;
import com.gutscheweb.castles.util.WorldUtil;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCmd extends Command {
    public WorldCmd() {
        super("world");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        World world;

        if (strings.length == 1 && strings[0].equals("goback")
            && Castles.instance.getServer().getWorld("world") != null)
                world = Castles.instance.getServer().getWorld("world");

        else if (Castles.instance.getServer().getWorld("void") == null) {
            WorldCreator creator = WorldUtil.getVoidCreator("void");
            world = creator.createWorld();
        } else
            world = Castles.instance.getServer().getWorld("void");
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must b playa idiod");
            return false;
        }

        assert world != null;
        if (world.getName().equals("void")) {
            configureWorld(world);
        }
        Player player = (Player) commandSender;
        player.teleport(new Location(world, 0, 64, 0));
        player.sendMessage("Woosh!");
        return false;
    }

    public static void configureWorld(World world) {
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        world.setGameRule(GameRule.DO_VINES_SPREAD, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        world.setSpawnLocation(0, 100, 0);
        //world.setSpawnLocation(new Location(world, 0.5, 100, 0.5));
    }
}

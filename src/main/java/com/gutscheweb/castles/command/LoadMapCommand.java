package com.gutscheweb.castles.command;

import com.gutscheweb.castles.util.SchematicHandler;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
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

        String sep = File.separator;
        File castleFile = new File(instance.getDataFolder() + sep + "castle.schem");
        File smallIslandFile = new File(instance.getDataFolder() + sep + "small island.schem");

        double castleRadius = 24.5, smallIslandRadius = 11.5;

        if (!castleFile.exists() || !smallIslandFile.exists()) {
            instance.saveResource("schematics/castle.schem", true); //24.5
            instance.saveResource("schematics/smallisland.schem", true); //11.5
        }

        SchematicHandler castleHandler = new SchematicHandler(castleFile);
        SchematicHandler smallIslandHandler = new SchematicHandler(smallIslandFile);

        Location castle1 = center.clone().add((smallIslandRadius + distance + castleRadius) * -1, 0, 0);
        Location castle2 = center.clone().add(smallIslandRadius + distance + castleRadius, 0, 0);

        instance.getLogger().info(center + "\n" + castle1 + "\n" + castle2);

        castleHandler.paste(castle1, 0);
        castleHandler.paste(castle2, 180);
        smallIslandHandler.paste(center, 0);


        return false;
    }
}

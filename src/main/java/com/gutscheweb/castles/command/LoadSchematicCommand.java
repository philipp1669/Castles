package com.gutscheweb.castles.command;

import com.gutscheweb.castles.Castles;
import com.gutscheweb.castles.util.FormatUtil;
import com.gutscheweb.castles.util.SchematicHandler;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class LoadSchematicCommand extends Command {
    public LoadSchematicCommand() {
        super("loadschematic", "load schematic", "", Collections.singletonList("ls"));
    }

    private final Castles castles = Castles.instance;
    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player idot");
            return false;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            File folder = castles.getDataFolder();
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) {
                commandSender.sendMessage("There aren't any schematics in " + folder.getPath());
                return false;
            }
            ArrayList<String> schematicNames = new ArrayList<>();
            for (File f : files) {
                if (!f.getName().endsWith(".schem")) break;
                String str = f.getName();
                String query = "<green>- <hover:show_text:Click me to load " + str.split("\\.")[0] + "!><click:run_command:/ls "
                        + str + " 0>" + str + "</click></hover></green>";
                schematicNames.add(query);
            }
            commandSender.sendMessage(
                    FormatUtil.getFormattedList(
                            "List of detected schematics", schematicNames.toArray(new String[0])
                    )
            );
            return false;
        }
        String lastArg = strings[strings.length - 1];
        double rotation = Double.parseDouble(lastArg);
        if (Double.isNaN(rotation)) rotation = 0;

        String[] stringsCropped = new String[strings.length - 1];
        System.arraycopy(strings, 0, stringsCropped, 0, strings.length - 1);


        String stringsJoined = String.join(" ", stringsCropped);
        if (stringsJoined.equals("reset")) {
            castles.saveResource("schematics/House.schem", true); //.5
            castles.saveResource("schematics/castle.schem", true); //24.5
            castles.saveResource("schematics/smallisland.schem", true); //11.5
            return false;
        }
        File schemFile = new File(castles.getDataFolder() + File.separator + stringsJoined);
        if (!schemFile.exists()) {
            player.sendMessage(mm.deserialize("<red>Could not find schematic " + stringsJoined + "</red>"));
            return false;
        }
        SchematicHandler handler = new SchematicHandler(schemFile);
        if (!handler.paste(player.getLocation(), rotation)) player.sendMessage("Could not paste the schematic!");
        else player.sendMessage("Schematic pasted successfully");

        return false;
    }


}

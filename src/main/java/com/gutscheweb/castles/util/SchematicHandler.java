package com.gutscheweb.castles.util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionBuilder;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.bukkit.Bukkit.getLogger;

public class SchematicHandler {
    private final File schemFile;

    public SchematicHandler(File schemFile) {
        this.schemFile = schemFile;
    }

    private Clipboard loadSchematic() {

        ClipboardFormat format = ClipboardFormats.findByFile(schemFile);
        getLogger().info(schemFile.getPath());
        try {
            if (format == null) return null;
            try (ClipboardReader reader = format.getReader(Files.newInputStream(schemFile.toPath()))) {
                return reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean paste(Location loc, double rotation) {
        getLogger().info("Rotating " + rotation + "deg");
        Clipboard clipboard = loadSchematic();
        if (clipboard == null) return false;
        ClipboardHolder holder = new ClipboardHolder(clipboard);
        holder.setTransform(new AffineTransform().rotateY(rotation));
        World w = BukkitAdapter.adapt(loc.getWorld());
        BlockVector3 vector = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(w)) {
            Operation operation = holder
                    .createPaste(editSession)
                    .to(vector)
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
            return true;
        } catch (WorldEditException e) {
            getLogger().severe("Error while pasting schematic: " + e);
            return false;
        }
    }
}

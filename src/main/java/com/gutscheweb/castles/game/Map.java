package com.gutscheweb.castles.game;

import com.gutscheweb.castles.util.SchematicHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;

import static com.gutscheweb.castles.Castles.instance;

public class Map {
    private final int size;
    private final Location center;
    private Location[] spawns;

//    public enum MapSize {
//        SMALL, MEDIUM, LARGE;
//    }

    public enum Team {
        RED(1), BLUE(2);
        private final int id;

        Team(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
    }
    public Map(int size, Location center, Location[] spawns) {
        this.size = size;
        this.center = center;
        this.spawns = spawns;
    }

    public Location getSpawn(Team team) {
        return spawns[team.getId()];
    }

    public void placeBarrier(Material mat) {
        for (Location loc : spawns) {
            Block[] blocks = new Block[4];
            blocks[0] = loc.clone().add(1, 1, 0).getBlock();
            blocks[1] = loc.clone().add(-1, 1, 0).getBlock();
            blocks[2] = loc.clone().add(0, 1, 1).getBlock();
            blocks[3] = loc.clone().add(0, 1, -1).getBlock();

            for (Block b : blocks)
                b.setType(mat);
        }

    }

    private File[] getSchematics() {
        String sep = File.separator;
        File castleFile = new File(instance.getDataFolder() + sep + "castle.schem");
        File smallIslandFile = new File(instance.getDataFolder() + sep + "small island.schem");


        if (!castleFile.exists() || !smallIslandFile.exists()) {
            instance.saveResource("schematics/castle.schem", true); //24.5
            instance.saveResource("schematics/smallisland.schem", true); //11.5
        }
        return new File[]{castleFile, smallIslandFile};
    }

    public void loadMap() {
        File[] schematics = getSchematics();
        SchematicHandler castleHandler = new SchematicHandler(schematics[0]);
        SchematicHandler smallIslandHandler = new SchematicHandler(schematics[1]);

        castleHandler.paste(spawns[0], 0);
        castleHandler.paste(spawns[1], 180);
        smallIslandHandler.paste(center, 0);
    }
}

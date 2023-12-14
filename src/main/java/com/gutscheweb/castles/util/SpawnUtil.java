package com.gutscheweb.castles.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SpawnUtil {
    public static void placeBarrier(Location loc, Material mat) {
        Block[] blocks = new Block[4];
        blocks[0] = loc.clone().add(1, 1, 0).getBlock();
        blocks[1] = loc.clone().add(-1, 1, 0).getBlock();
        blocks[2] = loc.clone().add(0, 1, 1).getBlock();
        blocks[3] = loc.clone().add(0, 1, -1).getBlock();

        for (Block b : blocks)
            b.setType(mat);
    }
}

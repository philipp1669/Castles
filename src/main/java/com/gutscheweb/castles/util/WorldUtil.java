package com.gutscheweb.castles.util;

import com.gutscheweb.castles.command.WorldCmd;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class WorldUtil {
    public static WorldCreator getVoidCreator(String name) {
        WorldCreator creator = new WorldCreator(name);
        creator.generator(new ChunkGenerator() {
            public byte[] generate(World world, Random random, int x, int z) {
                WorldCmd.configureWorld(world);
                return new byte[32768]; //Empty byte array
            }
        });
        return creator;
    }
}

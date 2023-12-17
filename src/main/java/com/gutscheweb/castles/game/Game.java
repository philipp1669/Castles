package com.gutscheweb.castles.game;

import com.gutscheweb.castles.Castles;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static void startCountDown(Player player, int max) {
        final AtomicInteger[] sec = {new AtomicInteger()};
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Castles.instance,
                () -> {
                    player.showBossBar(updateBossBar(sec[0].get(), max));
                    sec[0].getAndIncrement();
                },
                0, 20);
    }

    public static BossBar updateBossBar(int sec, int max) {
        return BossBar.bossBar(
                mm.deserialize(max - sec + "s remaining"),
                getProgress(sec, max),
                BossBar.Color.YELLOW,
                BossBar.Overlay.NOTCHED_10
        );
    }

    public static float getProgress(int sec, int max) {
        return 1 - (float) sec / max;
    }
}

package com.gutscheweb.castles.game;

import com.gutscheweb.castles.Castles;
import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

import static com.gutscheweb.castles.Castles.instance;

public class Game {
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static void startCountDown(Player player, int max) {

        final BossBar bar = BossBar.bossBar(
                mm.deserialize(max + "s remaining"),
                1-getProgress(0, max),
                BossBar.Color.YELLOW,
                BossBar.Overlay.PROGRESS
        );
        player.showBossBar(bar);
        BukkitRunnable runnable = new BukkitRunnable() {
            int current = 0;
            @Override
            public void run() {
                bar.name(mm.deserialize(max - current + "s remaining"));
                bar.progress(1-getProgress(current, max));
                current++;
                if (current > max) {
                    bar.viewers().forEach(v -> bar.removeViewer((Audience) v));
                    this.cancel();
                }
            }
        };

        runnable.runTaskTimer(instance, 0L, 20L);

        /*
        * () -> {
                    if (max >= sec[0].get()) Bukkit.getScheduler().cancelTask(tid);
                    for (BossBar bar : player.activeBossBars()) {
                        if (bar.name().contains(mm.deserialize("s remaining"))) {
                            bar.progress(getProgress(sec[0].get(), max));
                        }
                    }

                    sec[0].getAndIncrement();
                }
        * */
    }

    public static BossBar updateBossBar(int sec, int max, @Nullable BossBar current) {
        if (current == null)
            return BossBar.bossBar(
                mm.deserialize(max - sec + "s remaining"),
                getProgress(sec, max),
                BossBar.Color.YELLOW,
                BossBar.Overlay.PROGRESS
            );
        else {
            current.name(mm.deserialize(max - sec + "s remaíning"));
            current.progress(getProgress(sec, max));
            return current;
        }
    }

    public static float getProgress(int sec, int max) {
        return 1 - (float) sec / max;
    }
}

package dev.nuer.proudly.data;

import dev.nuer.proudly.BattlePass;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class AsyncDataFunctions {
    private static BukkitTask task;

    public AsyncDataFunctions() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(BattlePass.instance, () -> {

        }, 0L, 1L);
    }

    public static void queue() {

    }
}

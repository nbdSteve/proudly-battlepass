package dev.nuer.proudly.utils;

import dev.nuer.proudly.BattlePass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class CustomEventUtil {

    public static void fire(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void fireSync(Event event) {
        Bukkit.getScheduler().runTask(BattlePass.instance, () -> {
            Bukkit.getPluginManager().callEvent(event);
        });
    }
}
package dev.nuer.proudly.utils;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.enable.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TierCommandUtil {

    public static void execute(String directory, String path, Player player) {
        for (String command : FileManager.get(directory).getStringList(path)) {
            Bukkit.getScheduler().runTask(BattlePass.instance, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
            });
        }
    }
}
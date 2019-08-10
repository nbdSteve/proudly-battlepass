package dev.nuer.proudly.cmd.adminSub;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.Cluster;
import dev.nuer.proudly.enable.ClusterManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCmd {

    public static void onCmd(CommandSender sender) {
        FileManager.reload();
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            cluster.reload();
        }
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            cluster.reload();
        }
        if (sender instanceof Player) {
            MessageUtil.message("messages", "admin-cmd-reload", (Player) sender);
        } else {
            BattlePass.log.info("Successfully reloaded all configuration files.");
        }
    }
}

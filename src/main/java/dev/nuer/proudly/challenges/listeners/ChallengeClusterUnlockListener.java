package dev.nuer.proudly.challenges.listeners;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.events.ChallengeClusterUnlockEvent;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.utils.BroadcastUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChallengeClusterUnlockListener implements Listener {

    @EventHandler
    public void unlock(ChallengeClusterUnlockEvent event) {
        if (event.isCancelled()) return;
        event.getCluster().setUnlocked(true);
        BattlePass.log.info("running_3");
        if (FileManager.get("config").getBoolean("challenge-cluster-unlock-broadcast.enabled")) {
            BattlePass.log.info("unlocked cluster: " + event.getCluster().getClusterID());
            BroadcastUtil.broadcast("challenge-cluster-unlock-broadcast.message",
                    "{week}", String.valueOf(event.getCluster().getClusterID()));
        }
    }
}
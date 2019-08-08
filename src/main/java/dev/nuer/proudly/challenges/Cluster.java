package dev.nuer.proudly.challenges;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.events.ChallengeClusterUnlockEvent;
import dev.nuer.proudly.enable.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private int cluster;
    private ClusterType type;
    private List<Challenge> challenges;
    private boolean unlocked;
    private BukkitTask counterTaskId;

    public Cluster(int cluster, ClusterType type) {
        this.cluster = cluster;
        this.type = type;
        challenges = new ArrayList<>();
        this.unlocked = false;
        loadChallengeCluster();
    }

    public void loadChallengeCluster() {
        String cType = clusterType(this.type);
        BattlePass.log.info("Loading challenges for " + cType + "cluster_" + this.cluster);
        for (int i = 1; i <= 100; i++) {
            if (FileManager.get(cType + "cluster_" + this.cluster).getString("challenge." + i + ".challenge-id") == null)
                break;
            YamlConfiguration config = FileManager.get(cType + "cluster_" + this.cluster);
            Challenge challenge = new Challenge(cluster,
                    ChallengeType.valueOf(config.getString("challenge." + i + ".type").toUpperCase()),
                    config.getString("challenge." + i + ".challenge-id"),
                    config.getString("challenge." + i + ".active.element"),
                    Short.parseShort(config.getString("challenge." + i + ".active.data-value")),
                    config.getInt("challenge." + i + ".completion-requirements.total"),
                    config.getInt("challenge." + i + ".completion-requirements.payout"), type);
            this.challenges.add(challenge);
        }
    }

    public void countdown() {
        //Check to make sure that the countdown should run
        String cType = clusterType(this.type);
        if (isUnlocked()) return;
        if (!FileManager.get("config").getBoolean("do-cluster-countdowns")) return;
        if (FileManager.get(cType + "data").getInt("timers.cluster-" + this.cluster) <= 0) {
            Bukkit.getPluginManager().callEvent(new ChallengeClusterUnlockEvent(this));
            this.counterTaskId.cancel();
            return;
        }
        //Do the countdown timer
        this.counterTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(BattlePass.instance, () -> {
            //Check that the week is still locked
            if (unlocked) {
                //Fire the custom event
                Bukkit.getPluginManager().callEvent(new ChallengeClusterUnlockEvent(this));
                //Cancel the task
                this.counterTaskId.cancel();
            }
            //Store the time remaining
            int timeRemaining = FileManager.get(cType + "data").getInt("timers.cluster-" + this.cluster) - 1;
            //Check that the time remaining is greater than 0
            if (timeRemaining >= 0) {
                FileManager.get(cType + "data").set("timers.cluster-" + this.cluster, timeRemaining);
                FileManager.save(cType + "data");
            } else {
                //Fire the custom event
                Bukkit.getPluginManager().callEvent(new ChallengeClusterUnlockEvent(this));
                //Cancel the task
                this.counterTaskId.cancel();
            }
        }, 0L, 20L);
    }

    public static String clusterType(ClusterType type) {
        if (type.equals(ClusterType.COAL)) return "coal_";
        return "gold_";
    }

    public void reload() {
        loadChallengeCluster();
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getClusterID() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public ClusterType getType() {
        return type;
    }

    public void setType(ClusterType type) {
        this.type = type;
    }

    public BukkitTask getCounterTaskId() {
        return counterTaskId;
    }

    public void setCounterTaskId(BukkitTask counterTaskId) {
        this.counterTaskId = counterTaskId;
    }
}
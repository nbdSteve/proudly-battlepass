package dev.nuer.proudly.challenges;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.events.ChallengeClusterUnlockEvent;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.utils.CustomEventUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private int clusterID;
    private ClusterType type;
    private List<Challenge> challenges;
    private boolean unlocked;
    private BukkitTask counterTaskId;

    public Cluster(int clusterID, ClusterType type) {
        this.clusterID = clusterID;
        this.type = type;
        this.challenges = new ArrayList<>();
        this.unlocked = false;
        loadChallengeCluster();
    }

    public void loadChallengeCluster() {
        String cType = clusterIDType(this.type);
        BattlePass.log.info("Loading challenges for " + cType + "cluster_" + this.clusterID);
        for (int i = 1; i <= 100; i++) {
            if (FileManager.get(cType + "cluster_" + this.clusterID).getString("challenges." + i + ".challenge-id") == null)
                break;
            YamlConfiguration config = FileManager.get(cType + "cluster_" + this.clusterID);
            Challenge challenge = new Challenge(this.clusterID,
                    ChallengeType.valueOf(config.getString("challenges." + i + ".type").toUpperCase()),
                    config.getString("challenges." + i + ".challenge-id"),
                    config.getString("challenges." + i + ".active.element"),
                    Short.parseShort(config.getString("challenges." + i + ".active.data-value")),
                    config.getInt("challenges." + i + ".completion-requirements.total"),
                    config.getInt("challenges." + i + ".completion-requirements.payout"), type, i);
            this.challenges.add(challenge);
        }
    }

    public void countdown() {
        //Get the cluster type as a string
        String cType = clusterIDType(this.type);
        //Check if the cluster if unlocked
        if (isUnlocked()) return;
        //Check that the cooldown should run
        if (!FileManager.get("config").getBoolean("do-cluster-countdowns")) return;
        //See if the timer is already less than 0
        if (FileManager.get(cType + "data").getInt("timers.cluster-" + this.clusterID) <= 0) {
            //Fire custom event
            setUnlocked(true);
            //Cancel the runnable if it exists
            if (this.counterTaskId != null) this.counterTaskId.cancel();
            return;
        }
        //Do the countdown timer
        this.counterTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(BattlePass.instance, () -> {
            //Check that the week is still locked
            if (unlocked) {
                //Fire the custom event
                CustomEventUtil.fire(new ChallengeClusterUnlockEvent(this));
                //Cancel the task
                this.counterTaskId.cancel();
            }
            //Store the time remaining
            int timeRemaining = FileManager.get(cType + "data").getInt("timers.cluster-" + this.clusterID) - 1;
            //Check that the time remaining is greater than 0
            if (timeRemaining >= 0) {
                FileManager.get(cType + "data").set("timers.cluster-" + this.clusterID, timeRemaining);
                FileManager.save(cType + "data");
            } else {
                //Fire the custom event
                CustomEventUtil.fire(new ChallengeClusterUnlockEvent(this));
                //Cancel the task
                this.counterTaskId.cancel();
            }
        }, 0L, 20L);
    }

    public static String clusterIDType(ClusterType type) {
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
        return clusterID;
    }

    public void setCluster(int clusterID) {
        this.clusterID = clusterID;
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
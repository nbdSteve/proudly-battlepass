package dev.nuer.proudly.challenges;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.enable.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private List<Challenge> challenges;
    private boolean unlocked;
    private int cluster;

    public Cluster(int cluster) {
        this.cluster = cluster;
        challenges = new ArrayList<>();
        this.unlocked = false;
        loadChallengeCluster();
    }

    public void loadChallengeCluster() {
        BattlePass.log.info("Loading challenges for cluster: " + this.cluster);
        for (int i = 1; i<= 100; i++) {
            if (FileManager.get("challenge_cluster_" + this.cluster).getString("challenge." + i + ".challenge-id") == null) break;
            YamlConfiguration config = FileManager.get("challenge_cluster_" + this.cluster);
            Challenge challenge = new Challenge();
            this.challenges.add(challenge);
        }
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

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
}
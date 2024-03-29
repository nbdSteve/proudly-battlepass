package dev.nuer.proudly.enable;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.Challenge;
import dev.nuer.proudly.challenges.Cluster;
import dev.nuer.proudly.challenges.ClusterType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ClusterManager {
    public static Map<Integer, Cluster> coalClusters;
    public static Map<Integer, Cluster> goldClusters;

    public static void loadCoalClusters(FileManager fileManager) {
        coalClusters = new HashMap<>();
        //Load the clusters configuration for the challenges
        for (int i = 1; i <= FileManager.get("coal_data").getInt("total-coal-challenge-clusters"); i++) {
            fileManager.add("coal_cluster_" + i, "coal-pass" + File.separator + "challenges" + File.separator + "challenge-cluster-" + i + ".yml");
            coalClusters.put(i, new Cluster(i, ClusterType.COAL));
            coalClusters.get(i).countdown();
        }
    }

    public static void loadGoldClusters(FileManager fileManager) {
        goldClusters = new HashMap<>();
        //Load the clusters configuration for the challenges
        for (int i = 1; i <= FileManager.get("gold_data").getInt("total-gold-challenge-clusters"); i++) {
            fileManager.add("gold_cluster_" + i, "gold-pass" + File.separator + "challenges" + File.separator + "challenge-cluster-" + i + ".yml");
            goldClusters.put(i, new Cluster(i, ClusterType.GOLD));
            goldClusters.get(i).countdown();
        }
    }

    public static Cluster getCluster(int cluster, boolean gold) {
        if (gold) return goldClusters.get(cluster);
        return coalClusters.get(cluster);
    }

    public static Challenge getChallenge(String challengeId) {
        for (Cluster cluster : coalClusters.values()) {
            for (Challenge challenge : cluster.getChallenges()) {
                if (challenge.getID().equalsIgnoreCase(challengeId)) return challenge;
            }
        }
        for (Cluster cluster : goldClusters.values()) {
            for (Challenge challenge : cluster.getChallenges()) {
                if (challenge.getID().equalsIgnoreCase(challengeId)) return challenge;
            }
        }
        return null;
    }
}
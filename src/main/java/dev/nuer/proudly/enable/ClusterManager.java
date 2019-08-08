package dev.nuer.proudly.enable;

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
            Cluster cluster = new Cluster(i, ClusterType.COAL);
            cluster.countdown();
            coalClusters.put(i, cluster);
        }
    }

    public static void loadGoldClusters(FileManager fileManager) {
        goldClusters = new HashMap<>();
        //Load the clusters configuration for the challenges
        for (int i = 1; i <= FileManager.get("gold_data").getInt("total-gold-challenge-clusters"); i++) {
            fileManager.add("gold_cluster_" + i, "gold-pass" + File.separator + "challenges" + File.separator + "challenge-cluster-" + i + ".yml");
            Cluster cluster = new Cluster(i, ClusterType.GOLD);
            cluster.countdown();
            goldClusters.put(i, cluster);
        }
    }

    public static Cluster getCluster(int cluster, boolean gold) {
        if (gold) return goldClusters.get(cluster);
        return coalClusters.get(cluster);
    }

//    public static Challenge getChallenge(String challengeId) {
//        for (ChallengeWeek weeks : weeks.values()) {
//            for (Challenge challenge : weeks.challenges) {
//                if (challenge.getId().equalsIgnoreCase(challengeId)) return challenge;
//            }
//        }
//        return null;
//    }
}
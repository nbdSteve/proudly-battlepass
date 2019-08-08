package dev.nuer.proudly.data.utils;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.Challenge;
import dev.nuer.proudly.challenges.Cluster;
import dev.nuer.proudly.enable.ClusterManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Class that handles creating and loading player files
 */
public class PlayerFileUtil {
    //Store the file name string
    private String fileName;
    //Store the player file
    private File file;
    //Store the yaml config
    private YamlConfiguration config;

    public PlayerFileUtil(String fileName) {
        //Set instance variable
        this.fileName = fileName;
        //Get the player file
        file = new File(BattlePass.instance.getDataFolder(), fileName);
        //Load the configuration for the file
        config = YamlConfiguration.loadConfiguration(file);
        //If the file doesn't exist then set the defaults
        if (!file.exists()) {
            setupPlayerFileDefaults(config);
        }
        setupCoalClusters();
        setupGoldClusters();
        save();
    }

    private void setupPlayerFileDefaults(YamlConfiguration config) {
        //Set defaults for the information about the players tiers and currency
        config.createSection("pass-info");
        config.set("pass-info.gold-pass", false);
        config.set("pass-info.tier", 0);
        config.set("pass-info.points", 0);
        config.set("pass-info.challenges-completed", 0);
        //Set defaults for the information about the players current & completed challenges
        config.createSection("coal-challenges");
        config.createSection("gold-challenges");
        //Send a nice message
        BattlePass.log.info("Successfully created a new player-data file: " + fileName + ", actively creating / setting defaults.");
    }

    public void setupCoalClusters() {
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (config.get("coal-challenges.cluster-" + cluster.getClusterID() + "." + challenge.getCluster()) == null) {
                        config.set("coal-challenges.cluster-" + cluster.getClusterID() + "." + challenge.getCluster(), 0);
                    }
                }
            }
        }
    }

    public void setupGoldClusters() {
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (config.get("gold-challenges.cluster-" + cluster.getClusterID() + "." + challenge.getCluster()) == null) {
                        config.set("gold-challenges.cluster-" + cluster.getClusterID() + "." + challenge.getCluster(), 0);
                    }
                }
            }
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            BattlePass.log.severe("Critical error saving the file: " + fileName + ", please contact nbdSteve#0583 on discord.");
        }
        reload();
    }

    public void reload() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            BattlePass.log.severe("Critical error loading the file: " + fileName + ", please contact nbdSteve#0583 on discord.");
        }
    }

    public YamlConfiguration get() {
        return config;
    }
}

package dev.nuer.proudly.enable;

import dev.nuer.proudly.BattlePass;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        //General files
        fileManager.add("config", "proudly-pass.yml");
        fileManager.add("messages", "messages.yml");
        //Coal pass config
        fileManager.add("coal_data", "coal-pass" + File.separator + "data.yml");
        fileManager.add("coal_tiers", "coal-pass" + File.separator + "tiers.yml");
        ClusterManager.loadCoalClusters(fileManager);
        //Gold pass config
        fileManager.add("gold_data", "gold-pass" + File.separator + "data.yml");
        fileManager.add("gold_tiers", "gold-pass" + File.separator + "tiers.yml");
        ClusterManager.loadGoldClusters(fileManager);
    }

    public static void registerCommands(BattlePass instance) {
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
//        pm.registerEvents(new DataCreationOnJoin(), instance);
//        pm.registerEvents(new PlayerTierListener(), instance);
//        pm.registerEvents(new GuiClickListener(), instance);
//        pm.registerEvents(new ExperienceTierListener(), instance);
//        pm.registerEvents(new ChallengeWeekUnlockListener(), instance);
//        pm.registerEvents(new PlayerChallengeCompletedListener(), instance);
//        pm.registerEvents(new ChallengeListener(), instance);
//        pm.registerEvents(new BrewChallengeListener(), instance);
    }
}

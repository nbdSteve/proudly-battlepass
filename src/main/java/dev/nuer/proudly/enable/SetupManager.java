package dev.nuer.proudly.enable;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.listeners.ChallengeClusterUnlockListener;
import dev.nuer.proudly.challenges.listeners.ChallengeListener;
import dev.nuer.proudly.challenges.listeners.PlayerChallengeCompletionListener;
import dev.nuer.proudly.cmd.BpAdminCmd;
import dev.nuer.proudly.cmd.BpCmd;
import dev.nuer.proudly.cmd.ChallengesCmd;
import dev.nuer.proudly.cmd.TiersCmd;
import dev.nuer.proudly.data.listeners.DataCreationOnJoin;
import dev.nuer.proudly.guis.listener.GuiClickListener;
import dev.nuer.proudly.points.listeners.PointTierListener;
import dev.nuer.proudly.tiers.listeners.PlayerTierListener;
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
        //Load tiers
        fileManager.add("tier_config", "tiers" + File.separator + "config.yml");
        fileManager.add("tier_gui", "tiers" + File.separator + "gui.yml");
        //Coal pass config
        fileManager.add("coal_data", "coal-pass" + File.separator + "data.yml");
        fileManager.add("coal_config", "coal-pass" + File.separator + "config.yml");
        ClusterManager.loadCoalClusters(fileManager);
        //Gold pass config
        fileManager.add("gold_data", "gold-pass" + File.separator + "data.yml");
        fileManager.add("gold_config", "gold-pass" + File.separator + "config.yml");
        ClusterManager.loadGoldClusters(fileManager);
    }

    public static void registerCommands(BattlePass instance) {
        instance.getCommand("battlepass").setExecutor(new BpCmd());
        instance.getCommand("battlepass-admin").setExecutor(new BpAdminCmd());
//        instance.getCommand("challenges").setExecutor(new ChallengesCmd());
//        instance.getCommand("tiers").setExecutor(new TiersCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new DataCreationOnJoin(), instance);
        pm.registerEvents(new PlayerTierListener(), instance);
        pm.registerEvents(new GuiClickListener(), instance);
        pm.registerEvents(new PointTierListener(), instance);
        pm.registerEvents(new ChallengeClusterUnlockListener(), instance);
        pm.registerEvents(new PlayerChallengeCompletionListener(), instance);
        pm.registerEvents(new ChallengeListener(), instance);
//        pm.registerEvents(new BrewChallengeListener(), instance);
    }
}

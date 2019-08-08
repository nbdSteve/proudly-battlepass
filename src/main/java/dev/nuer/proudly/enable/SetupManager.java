package dev.nuer.proudly.enable;

import dev.nuer.pp.challenges.listeners.BrewChallengeListener;
import dev.nuer.pp.challenges.listeners.ChallengeListener;
import dev.nuer.pp.challenges.listeners.ChallengeWeekUnlockListener;
import dev.nuer.pp.challenges.listeners.PlayerChallengeCompletedListener;
import dev.nuer.pp.experience.listeners.ExperienceTierListener;
import dev.nuer.pp.gui.listener.GuiClickListener;
import dev.nuer.pp.playerData.listeners.DataCreationOnJoin;
import dev.nuer.pp.tiers.listeners.PlayerTierListener;
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
        fileManager.add("config", "pass+.yml");
        fileManager.add("messages", "messages.yml");
        fileManager.add("tier_config", "tiers" + File.separator + "tier-config.yml");
        fileManager.add("tier_gui", "tiers" + File.separator + "tier-gui.yml");
        fileManager.add("challenge_config", "challenges" + File.separator + "challenge-config.yml");
        fileManager.add("unlock_timers", "challenges" + File.separator + "unlock-timers.yml");
        //Load the week configuration for the challenges.
        WeeklyChallengeManager.load(fileManager);
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
        pm.registerEvents(new ExperienceTierListener(), instance);
        pm.registerEvents(new ChallengeWeekUnlockListener(), instance);
        pm.registerEvents(new PlayerChallengeCompletedListener(), instance);
        pm.registerEvents(new ChallengeListener(), instance);
        pm.registerEvents(new BrewChallengeListener(), instance);
    }
}

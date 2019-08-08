package dev.nuer.proudly;

import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.logging.Logger;

public final class BattlePass extends JavaPlugin {
    public static BattlePass instance;
    public static Logger log;
    public static DecimalFormat df = new DecimalFormat("#,###.##");
    public static String version = "0.0.1-BETA";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        log = getLogger();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package dev.nuer.proudly.guis.challenge;

import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.guis.AbstractGui;
import dev.nuer.proudly.guis.menu.MainMenuGui;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChallengeMenuGui extends AbstractGui {
    private YamlConfiguration config;

    /**
     * Constructor the create a new Gui
     *
     * @param player Player, the person who open the gui
     */
    public ChallengeMenuGui(Player player, String type) {
        super(FileManager.get(type + "_config").getInt("gui.size"),
                ColorUtil.colorize(FileManager.get(type + "_config").getString("gui.name")));
        config = FileManager.get(type + "_config");
        for (int i = 0; i < config.getInt("gui.size"); i++) {
            try {
                int configID = i;
                setItemInSlot(config.getInt("gui." + configID + ".slot"), buildItem(configID, player, config, type), player1 -> {
                    if (config.getBoolean("gui." + configID + ".open.enabled")) {
                        if (FileManager.get(type + "_data").getInt("timers.cluster-" + config.getString("gui." + configID + ".cluster-id")) <= 0) {
                            new ClusterGui(config.getInt("gui." + configID + ".open.cluster-id"), player1, type).open(player1);
                        }
                    }
                    if (config.getBoolean("gui." + configID + ".exit-button")) {
                        new MainMenuGui(player1).open(player1);
                    }
                });
            } catch (Exception e) {
                //Do nothing, item just is not loaded for this slot
            }
        }
    }

    public ItemStack buildItem(int configID, Player player, YamlConfiguration config, String type) {
        //Create the item
        ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString("gui." + configID + ".material"),
                config.getString("gui." + configID + ".data-value"));
        //Set the item name
        ibu.addName(ColorUtil.colorize(config.getString("gui." + configID + ".name")));
        //Add the first section of lore, replace the relevant placeholders
        ibu.addLore(config.getStringList("gui." + configID + ".lore"));
        ibu.replaceLorePlaceholder("{player}", player.getName());
        ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
        ibu.replaceLorePlaceholder("{status}", getStatus(configID, type));
        TimeUtil tu = new TimeUtil(FileManager.get(type + "_data").getInt("timers.cluster-" + config.getString("gui." + configID + ".cluster-id")));
        ibu.replaceLorePlaceholder("{days}", tu.getDays());
        ibu.replaceLorePlaceholder("{hours}", tu.getHours());
        ibu.replaceLorePlaceholder("{minutes}", tu.getMinutes());
        ibu.replaceLorePlaceholder("{seconds}", tu.getSeconds());
        //Add item enchantments
        ibu.addEnchantments(config.getStringList("gui." + configID + ".enchantments"));
        //Add item flags
        ibu.addItemFlags(config.getStringList("gui." + configID + ".item-flags"));
        return ibu.getItem();
    }

    public String getStatus(int clusterID, String type) {
        try {
            if (FileManager.get(type + "_data").getInt("timers.cluster-" + clusterID) <= 0) {
                return ColorUtil.colorize(FileManager.get(type + "_config").getString("status.unlocked"));
            } else {
                return ColorUtil.colorize(FileManager.get(type + "_config").getString("status.locked"));
            }
        } catch (Exception e) {
            return "debug";
        }
    }
}

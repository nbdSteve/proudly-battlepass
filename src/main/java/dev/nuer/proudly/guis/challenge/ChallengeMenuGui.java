package dev.nuer.proudly.guis.challenge;

import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.guis.AbstractGui;
import dev.nuer.proudly.guis.menu.MainMenuGui;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.ColorUtil;
import dev.nuer.proudly.utils.ItemBuilderUtil;
import dev.nuer.proudly.utils.TimeUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChallengeMenuGui extends AbstractGui {

    /**
     * Constructor the create a new Gui
     *
     * @param player Player, the person who open the gui
     */
    public ChallengeMenuGui(Player player) {
        super(FileManager.get("challenge_config").getInt("challenge-menu-gui.size"),
                ColorUtil.colorize(FileManager.get("challenge_config").getString("challenge-menu-gui.name")));
        for (int i = 0; i < FileManager.get("challenge_config").getInt("challenge-menu-gui.size"); i++) {
            try {
                int id = i;
                setItemInSlot(FileManager.get("challenge_config").getInt("challenge-menu-gui." + i + ".slot"), buildItem(i, player), player1 -> {
                    if (FileManager.get("challenge_config").getBoolean("challenge-menu-gui." + id + ".open.enabled")) {
                        if (FileManager.get("unlock_timers").getInt("timers.week-" + FileManager.get("challenge_config").getString("challenge-menu-gui." + id + ".week")) <= 0) {
                            new ClusterGui(FileManager.get("challenge_config").getString("challenge-menu-gui." + id + ".open.week"), player1).open(player1);
                        }
                    }
                    if (FileManager.get("challenge_config").getBoolean("challenge-menu-gui." + id + ".exit-button")) {
                        new MainMenuGui(player1).open(player1);
                    }
                });
            } catch (Exception e) {
                //Do nothing, item just is not loaded for this slot
            }
        }
    }

    public ItemStack buildItem(int i, Player player) {
        YamlConfiguration config = FileManager.get("challenge_config");
        //Create the item
        ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString("challenge-menu-gui." + i + ".material"),
                config.getString("challenge-menu-gui." + i + ".data-value"));
        //Set the item name
        ibu.addName(ColorUtil.colorize(config.getString("challenge-menu-gui." + i + ".name")));
        //Add the first section of lore, replace the relevant placeholders
        ibu.addLore(config.getStringList("challenge-menu-gui." + i + ".lore"));
        ibu.replaceLorePlaceholder("{player}", player.getName());
        ibu.replaceLorePlaceholder("{experience-name}", FileManager.get("config").getString("experience-name"));
        ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
        ibu.replaceLorePlaceholder("{status}", getStatus(i));
        TimeUtil tu = new TimeUtil(FileManager.get("unlock_timers").getInt("timers.week-" + FileManager.get("challenge_config").getString("challenge-menu-gui." + i + ".week")));
        ibu.replaceLorePlaceholder("{days}", tu.getDays());
        ibu.replaceLorePlaceholder("{hours}", tu.getHours());
        ibu.replaceLorePlaceholder("{minutes}", tu.getMinutes());
        ibu.replaceLorePlaceholder("{seconds}", tu.getSeconds());
        //Add item enchantments
        ibu.addEnchantments(config.getStringList("challenge-menu-gui." + i + ".enchantments"));
        //Add item flags
        ibu.addItemFlags(config.getStringList("challenge-menu-gui." + i + ".item-flags"));
        return ibu.getItem();
    }

    public String getStatus(int week) {
        try {
            if (FileManager.get("unlock_timers").getInt("timers.week-" + week) <= 0) {
                return ColorUtil.colorize(FileManager.get("challenge_config").getString("status.unlocked"));
            } else {
                return ColorUtil.colorize(FileManager.get("challenge_config").getString("status.locked"));
            }
        } catch (Exception e) {
            return "debug";
        }
    }
}

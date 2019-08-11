package dev.nuer.proudly.guis.menu;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.guis.AbstractGui;
import dev.nuer.proudly.guis.challenge.ChallengeMenuGui;
import dev.nuer.proudly.guis.tier.TierMenuGui;
import dev.nuer.proudly.points.PlayerPointManager;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.ColorUtil;
import dev.nuer.proudly.utils.ItemBuilderUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainMenuGui extends AbstractGui {

    /**
     * Constructor the create a new Gui
     */
    public MainMenuGui(Player player) {
        super(FileManager.get("config").getInt("main-menu.size"),
                ColorUtil.colorize(FileManager.get("config").getString("main-menu.name")));
        for (int i = 0; i < FileManager.get("config").getInt("main-menu.size"); i++) {
            try {
                int id = i;
                setItemInSlot(FileManager.get("config").getInt("main-menu." + i + ".slot"), buildItem(i, player), player1 -> {
                    if (FileManager.get("config").getBoolean("main-menu." + id + ".open-tiers")) {
//                        if (PlayerDataManager.hasCopy(player1)) {
//                            new TierMenuGui(player1, 1).open(player1);
//                        } else {
//                            MessageUtil.message("messages", "invalid-pass", player1);
//                            player1.closeInventory();
//                        }
                        new TierMenuGui(player1, 1).open(player1);
                    }
                    if (FileManager.get("config").getBoolean("main-menu." + id + ".open-coal-challenges")) {
//                        if (PlayerDataManager.hasCopy(player1)) {
//                            new ChallengeMenuGui(player1).open(player1);
//                        } else {
//                            MessageUtil.message("messages", "invalid-pass", player1);
//                            player1.closeInventory();
//                        }
                        new ChallengeMenuGui(player1).open(player1);
                    }
                    if (FileManager.get("config").getBoolean("main-menu." + id + ".open-gold-challenges")) {

                    }
                    if (FileManager.get("config").getBoolean("main-menu." + id + ".exit-button")) {
                        player1.closeInventory();
                    }
                });
            } catch (Exception e) {
                //Do nothing, item just is not loaded for this slot
            }
        }
    }

    public ItemStack buildItem(int i, Player player) {
        YamlConfiguration config = FileManager.get("config");
        //Create the item
        ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString("main-menu." + i + ".material"),
                config.getString("main-menu." + i + ".data-value"));
        //Set the item name
        ibu.addName(ColorUtil.colorize(config.getString("main-menu." + i + ".name")));
        //Add the first section of lore, replace the relevant placeholders
        ibu.addLore(config.getStringList("main-menu." + i + ".lore"));
        ibu.replaceLorePlaceholder("{player}", player.getName());
        ibu.replaceLorePlaceholder("{experience-name}", config.getString("experience-name"));
        ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
        ibu.replaceLorePlaceholder("{points}", BattlePass.df.format(PlayerPointManager.getPoints(player)));
        //Check to see if the item has status lore
        if (config.getBoolean("main-menu." + i + ".status.add-lore")) {
            if (PlayerDataManager.isGold(player)) {
                ibu.addLore(config.getStringList("main-menu." + i + ".status.unlocked-lore"));
                ibu.replaceLorePlaceholder("{player}", player.getName());
                ibu.replaceLorePlaceholder("{experience-name}", config.getString("experience-name"));
                ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
                ibu.replaceLorePlaceholder("{exp}", BattlePass.df.format(PlayerPointManager.getPoints(player)));
                ibu.replaceLorePlaceholder("{challenges-completed}", String.valueOf(PlayerDataManager.getChallengesCompleted(player)));
            } else {
                ibu.addLore(config.getStringList("main-menu." + i + ".status.locked-lore"));
                ibu.replaceLorePlaceholder("{player}", player.getName());
                ibu.replaceLorePlaceholder("{experience-name}", config.getString("experience-name"));
                ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
                ibu.replaceLorePlaceholder("{exp}", BattlePass.df.format(PlayerPointManager.getPoints(player)));
                ibu.replaceLorePlaceholder("{challenges-completed}", String.valueOf(PlayerDataManager.getChallengesCompleted(player)));
            }
        }
        //Add item enchantments
        ibu.addEnchantments(config.getStringList("main-menu." + i + ".enchantments"));
        //Add item flags
        ibu.addItemFlags(config.getStringList("main-menu." + i + ".item-flags"));
        return ibu.getItem();
    }
}
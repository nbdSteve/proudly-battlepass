package dev.nuer.proudly.guis.tier;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.guis.AbstractGui;
import dev.nuer.proudly.guis.menu.MainMenuGui;
import dev.nuer.proudly.points.PlayerPointManager;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TierMenuGui extends AbstractGui {
    YamlConfiguration config;
    private int points;

    /**
     * Constructor the create a new Gui
     *
     * @param player Player, the person who open the gui
     */
    public TierMenuGui(Player player, int page) {
        super(FileManager.get("tier_gui_page_" + page).getInt("size"),
                ColorUtil.colorize(FileManager.get("tier_gui_page_" + page).getString("name")));
        config = FileManager.get("tier_gui_page_" + page);
        points = PlayerPointManager.getPoints(player);
        for (int i = 1; i <= config.getInt("size"); i++) {
            try {
                int configID = i;
                setItemInSlot(config.getInt(configID + ".slot"), buildItem(configID, player), player1 -> {
                    if (config.getBoolean(configID + ".exit-button")) {
                        new MainMenuGui(player1).open(player1);
                    }
                    if (config.getBoolean(configID + ".previous-page.enabled")) {
                        new TierMenuGui(player1, config.getInt(configID + ".previous-page.page")).open(player1);
                    }
                    if (config.getBoolean(configID + ".next-page.enabled")) {
                        new TierMenuGui(player1, config.getInt(configID + ".next-page.page")).open(player1);
                    }
                    if (config.getBoolean(configID + ".tier-item.enabled")) {
                        if (config.getBoolean(configID + ".tier-item.gold") && !PlayerDataManager.isGold(player)) return;
                        if (points <= FileManager.get("tier_config").getInt(config.getInt(configID + ".tier") + ".points-required")) return;
                        if (PlayerTierManager.hasClaimedReward(player, config.getInt(configID + ".tier"))) return;
                        PlayerTierManager.setClaimed(player, config.getInt(configID + ".tier"));
                        TierCommandUtil.execute("tier_gui_page_" + page, configID + "tier-item.commands", player);
                        MessageUtil.message("tier_gui_page_" + page, configID + "tier-item.message", player,
                                "{player}", player.getName());
                    }
                });
            } catch (Exception e) {
                //Do nothing, item just is not loaded for this slot
            }
        }
    }

    public ItemStack buildItem(int configID, Player player) {
        //Create the item
        ItemBuilderUtil ibu = null;
        if (config.getBoolean(configID + ".adaptable")) {
            if (PlayerTierManager.hasRewardPending(player, config.getInt(configID + ".tier"))) {
                ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                        FileManager.get("tier_config").getString("adaptable-glass.reward-pending"));
            } else if (PlayerTierManager.getTier(player) >= config.getInt(configID + ".tier")) {
                ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                        FileManager.get("tier_config").getString("adaptable-glass.complete"));
            } else {
                ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                        FileManager.get("tier_config").getString("adaptable-glass.active"));
            }
        } else {
            ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                    config.getString(configID + ".data-value"));
        }
        //Set the item name
        ibu.addName(ColorUtil.colorize(config.getString(configID + ".name")));
        //Add the first section of lore, replace the relevant placeholders
        ibu.addLore(config.getStringList(configID + ".lore"));
        ibu.replaceLorePlaceholder("{player}", player.getName());
        ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
        if (completed(config.getInt(configID + ".tier"), PlayerTierManager.getTier(player))) {
            ibu.replaceLorePlaceholder("{points}", "MAX");
        } else {
            ibu.replaceLorePlaceholder("{points}", BattlePass.df.format(PlayerPointManager.getPoints(player)));
        }
        try {
            ibu.replaceLorePlaceholder("{progress-bar}",
                    ProgressBarUtil.bar(PlayerPointManager.getPoints(player),
                            FileManager.get("tier_config").getInt(configID + ".points-required")));
        } catch (Exception e) {
            ibu.replaceLorePlaceholder("{progress-bar}", "debug");
        }
        try {
            ibu.replaceLorePlaceholder("{status}", getStatus(config.getInt(configID + ".tier"), PlayerTierManager.getTier(player)));
        } catch (Exception e) {
            ibu.replaceLorePlaceholder("{status}", "debug");
        }
        //Add item enchantments
        ibu.addEnchantments(config.getStringList(configID + ".enchantments"));
        //Add item flags
        ibu.addItemFlags(config.getStringList(configID + ".item-flags"));
        return ibu.getItem();
    }

    public String getStatus(int i, int tier) {
        if (i - 1 < tier) {
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.complete"));
        } else if (i == tier + 1) {
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.active"));
        } else {
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.locked"));
        }
    }
    public boolean completed(int i, int tier) {
        return i - 1 < tier;
    }
}
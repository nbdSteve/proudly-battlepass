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
    String type;

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
                if (config.getBoolean(configID + ".tier-item.gold")) {
                    type = "gold";
                } else {
                    type = "coal";
                }
                setItemInSlot(config.getInt(configID + ".slot"), buildItem(configID, player), player1 -> {
                    if (config.getBoolean(configID + ".exit-button.enabled")) {
                        new MainMenuGui(player1).open(player1);
                    }
                    if (config.getBoolean(configID + ".previous-page.enabled")) {
                        new TierMenuGui(player1, config.getInt(configID + ".previous-page.page")).open(player1);
                    }
                    if (config.getBoolean(configID + ".next-page.enabled")) {
                        new TierMenuGui(player1, config.getInt(configID + ".next-page.page")).open(player1);
                    }
                    if (config.getBoolean(configID + ".tier-item.enabled")) {
                        if (config.getBoolean(configID + ".tier-item.gold") && !PlayerDataManager.isGold(player)) {
                            return;
                        }
                        if (points < FileManager.get("tier_config").getInt(config.getInt(configID + ".tier") + ".points-required")) {
                            return;
                        }
                        String tType = config.getBoolean(configID + ".tier-item.gold") ? "gold" : "coal";
                        if (PlayerTierManager.hasClaimedReward(player, config.getInt(configID + ".tier"), tType)) {
                            return;
                        }
                        PlayerTierManager.setClaimed(player, config.getInt(configID + ".tier"), tType);
                        TierCommandUtil.execute("tier_gui_page_" + page, configID + ".tier-item.commands", player);
                        MessageUtil.message("tier_gui_page_" + page, configID + ".tier-item.message", player,
                                "{player}", player.getName());
                        new TierMenuGui(player, page).open(player);
                    }
                });
            } catch (Exception e) {
                //Do nothing, item just is not loaded for this slot
            }
        }
    }

    public ItemStack buildItem(int configID, Player player) {
        //Create the item
        ItemBuilderUtil ibu;
        if (config.getBoolean(configID + ".adaptable")) {
            if (PlayerTierManager.hasRewardPending(player, config.getInt(configID + ".tier"), "coal")) {
                ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                        FileManager.get("tier_config").getString("adaptable-glass.reward-pending"));
            } else if (PlayerTierManager.getTier(player) >= config.getInt(configID + ".tier")) {
                if (PlayerDataManager.isGold(player) && PlayerTierManager.hasRewardPending(player, config.getInt(configID + ".tier"), "gold")) {
                    ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                            FileManager.get("tier_config").getString("adaptable-glass.reward-pending"));
                } else {
                    ibu = new ItemBuilderUtil(config.getString(configID + ".material"),
                            FileManager.get("tier_config").getString("adaptable-glass.complete"));
                }
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
        if (config.getBoolean(configID + ".info-item.enabled")) {
            ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
            ibu.replaceLorePlaceholder("{points}", BattlePass.df.format(points));
            ibu.replaceLorePlaceholder("{challenges-completed}", String.valueOf(PlayerDataManager.getChallengesCompleted(player)));
        } else if (completed(config.getInt(configID + ".tier"), PlayerTierManager.getTier(player))) {
            ibu.replaceLorePlaceholder("{points}", "MAX");
        } else {
            ibu.replaceLorePlaceholder("{points}", BattlePass.df.format(points));
        }
        if (config.getBoolean(configID + ".progress-bar.enabled")) {
            try {
                ibu.replaceLorePlaceholder("{progress-bar}",
                        ProgressBarUtil.bar(points,
                                FileManager.get("tier_config").getInt(config.getInt(configID + ".tier") + ".points-required")));
            } catch (Exception e) {
                ibu.replaceLorePlaceholder("{progress-bar}", "debug");
            }
        }
        if (config.getBoolean(configID + ".status.enabled")) {
            try {
                ibu.replaceLorePlaceholder("{status}", getStatus(configID, player));
            } catch (Exception e) {
                ibu.replaceLorePlaceholder("{status}", "debug");
            }
        }
        //Add item enchantments
        ibu.addEnchantments(config.getStringList(configID + ".enchantments"));
        //Add item flags
        ibu.addItemFlags(config.getStringList(configID + ".item-flags"));
        return ibu.getItem();
    }

    public String getStatus(int configID, Player player) {
        if (PlayerTierManager.hasRewardPending(player, config.getInt(configID + ".tier"), type)) {
            if (type.equalsIgnoreCase("gold") && !PlayerDataManager.isGold(player)) {
                return ColorUtil.colorize(FileManager.get("tier_config").getString("status.locked"));
            }
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.reward-available"));
        } else if (PlayerTierManager.getTier(player) >= config.getInt(configID + ".tier")) {
            if (type.equalsIgnoreCase("gold") && !PlayerDataManager.isGold(player)) {
                return ColorUtil.colorize(FileManager.get("tier_config").getString("status.locked"));
            }
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.complete"));
        } else if (config.getBoolean(configID + ".tier-item.gold") && !PlayerDataManager.isGold(player)) {
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.locked"));
        } else {
            return ColorUtil.colorize(FileManager.get("tier_config").getString("status.in-progress"));
        }
    }

    public boolean completed(int i, int tier) {
        return i - 1 < tier;
    }
}
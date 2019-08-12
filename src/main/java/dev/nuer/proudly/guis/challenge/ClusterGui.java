package dev.nuer.proudly.guis.challenge;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.Challenge;
import dev.nuer.proudly.challenges.Status;
import dev.nuer.proudly.enable.ClusterManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.guis.AbstractGui;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClusterGui extends AbstractGui {
    private YamlConfiguration config;
    private Challenge challenge;

    /**
     * Constructor the create a new Gui
     */
    public ClusterGui(int clusterID, Player player, String type) {
        super(FileManager.get(type + "_cluster_" + clusterID).getInt("gui.size"),
                ColorUtil.colorize(FileManager.get(type + "_cluster_" + clusterID).getString("gui.name")));
        config = FileManager.get(type + "_cluster_" + clusterID);
        if (config.getBoolean("gui.exit-button.enabled")) {
            ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString("gui.exit-button.material"),
                    config.getString("gui.exit-button.data-value"));
            ibu.addName(ColorUtil.colorize(config.getString("gui.exit-button.name")));
            ibu.addLore(config.getStringList("gui.exit-button.lore"));
            ibu.addEnchantments(config.getStringList("gui.exit-button.enchantments"));
            ibu.addItemFlags(config.getStringList("gui.exit-button.item-flags"));
            setItemInSlot(config.getInt("gui.exit-button.slot"), ibu.getItem(), player1 -> {
                new ChallengeMenuGui(player, type).open(player);
            });
        }
        for (int i = 1; i < config.getInt("gui.size"); i++) {
            try {
                int configID = i;
                setItemInSlot(config.getInt("challenges." + configID + ".gui.slot"), buildItem(configID, player, config), player1 -> {
                    if (config.getBoolean("challenges." + configID + ".gui.rewards.enabled")) {
                        if (challenge.isComplete(player) && !challenge.hasClaimedReward(player)) {
                            challenge.setRewardClaimed(player);
                            TierCommandUtil.execute(type + "_cluster_" + clusterID, "challenges." + configID + ".gui.rewards.commands", player);
                            MessageUtil.message(type + "_cluster_" + clusterID, "challenges." + configID + ".gui.rewards.message", player,
                                    "{player}", player.getName());
                            new ClusterGui(clusterID, player, type).open(player);
                        }
                    }
                });
            } catch (Exception e) {
                if (i == 1) {
                    e.printStackTrace();
                }
                //Do nothing, Item just doesn't exist
            }
        }
    }

    public ItemStack buildItem(int configID, Player player, YamlConfiguration config) {
        ItemBuilderUtil ibu = new ItemBuilderUtil(config.getString("challenges." + configID + ".gui.item.material"),
                config.getString("challenges." + configID + ".gui.item.data-value"));
        ibu.addName(ColorUtil.colorize(config.getString("challenges." + configID + ".gui.name")));
        ibu.addLore(config.getStringList("challenges." + configID + ".gui.lore"));
        ibu.replaceLorePlaceholder("{player}", player.getName());
        ibu.replaceLorePlaceholder("{tier}", String.valueOf(PlayerTierManager.getTier(player)));
        challenge = ClusterManager.getChallenge(config.getString("challenges." + configID + ".challenge-id"));
        ibu.replaceLorePlaceholder("{status}", new Status(player, config, challenge).getString());
        try {
            ibu.replaceLorePlaceholder("{progress-bar}", ProgressBarUtil.bar(challenge.getProgress(player), challenge.getTotal()));
        } catch (Exception e) {
            ibu.replaceLorePlaceholder("{progress-bar}", "debug");
        }
        ibu.addEnchantments(config.getStringList("challenges." + configID + ".gui.enchantments"));
        ibu.addItemFlags(config.getStringList("challenges." + configID + ".gui.item-flags"));
        return ibu.getItem();
    }
}

package dev.nuer.proudly.guis.challenge;

import dev.nuer.proudly.challenges.Challenge;
import dev.nuer.proudly.challenges.Status;
import dev.nuer.proudly.enable.ClusterManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.guis.AbstractGui;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.*;
import org.bukkit.configuration.file.YamlConfiguration;
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
        for (int i = 1; i < config.getInt("gui.size"); i++) {
            try {
                int configID = i;
                setItemInSlot(config.getInt("challenges." + configID + ".gui.slot"), buildItem(configID, player, config), player1 -> {
                    if (config.getBoolean("challenges." + configID + ".gui.rewards.enabled")) {
                        if (challenge.isComplete(player) && !challenge.hasClaimedReward(player)) {
                            TierCommandUtil.execute(type + "_cluster_" + clusterID, "challenges." + configID + ".gui.rewards.commands", player);
                            MessageUtil.message(type + "_cluster_" + clusterID, "challenges." + configID + ".gui.rewards.message", player);
                        }
                    }
                });
            } catch (Exception e) {
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

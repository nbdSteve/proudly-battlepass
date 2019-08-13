package dev.nuer.proudly.challenges;

import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.utils.ColorUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Status {
    private Player player;
    private YamlConfiguration config;
    private Challenge challenge;

    public Status(Player player, YamlConfiguration config, Challenge challenge) {
        this.player = player;
        this.config = config;
        this.challenge = challenge;
    }

    public String getString() {
        if (challenge.getClusterType().equals(ClusterType.GOLD) && !PlayerDataManager.isGold(player)) {
            return ColorUtil.colorize(config.getString("status.locked"));
        } else if (challenge.hasClaimedReward(player)) {
            return ColorUtil.colorize(config.getString("status.complete"));
        } else if (challenge.isComplete(player)) {
            return ColorUtil.colorize(config.getString("status.reward-available"));
        } else {
            return ColorUtil.colorize(config.getString("status.in-progress"));
        }
    }
}
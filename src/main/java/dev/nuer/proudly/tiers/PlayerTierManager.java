package dev.nuer.proudly.tiers;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.data.utils.PlayerFileUtil;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.tiers.events.PlayerTierUpEvent;
import dev.nuer.proudly.tiers.exception.BelowMinimumPlayerTierException;
import dev.nuer.proudly.tiers.exception.ExceedMaxPlayerTierException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTierManager {

    public static void incrementTier(Player player, int numberOfTiersToIncrement) {
        try {
            setTier(player, getTier(player) + numberOfTiersToIncrement);
        } catch (ExceedMaxPlayerTierException | BelowMinimumPlayerTierException e) {
            //Do nothing, the exception handles debugging with a message
        }
    }

    public static void decrementTier(Player player, int numberOfTiersToDecrement) {
        try {
            setTier(player, getTier(player) - numberOfTiersToDecrement);
        } catch (ExceedMaxPlayerTierException | BelowMinimumPlayerTierException e) {
            //Do nothing, the exception handles debugging with a message
        }
    }

    public static void setTier(Player player, int tier) throws ExceedMaxPlayerTierException, BelowMinimumPlayerTierException {
        //Check to make sure the desired tier is not going to exceed the max tier
        if (tier > FileManager.get("tier_config").getInt("max-tier"))
            throw new ExceedMaxPlayerTierException();
        if (tier < 0) throw new BelowMinimumPlayerTierException();
        //Register the increment events if the tier is increasing
        if (tier > getTier(player)) {
            for (int i = 0; i < tier - getTier(player); i++) {
                Bukkit.getScheduler().runTask(BattlePass.instance, () -> {
                    Bukkit.getPluginManager().callEvent(new PlayerTierUpEvent(player, tier));
                });
            }
        }
        PlayerFileUtil pfu = PlayerDataManager.getPlayerFile(player);
        //If not, then set the players tier
        pfu.get().set("pass-info.tier", tier);
        pfu.save();
    }

    public static int getTier(Player player) {
        return PlayerDataManager.getPlayerFile(player).get().getInt("pass-info.tier");
    }
}
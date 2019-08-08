package dev.nuer.proudly.data;

import dev.nuer.proudly.data.utils.PlayerFileUtil;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerDataManager {

    public static PlayerFileUtil getPlayerFile(Player player) {
        return new PlayerFileUtil("player-data" + File.separator + player.getUniqueId().toString() + ".yml");
    }

    public static void makeGold(Player player) {
        PlayerFileUtil pfu = getPlayerFile(player);
        pfu.get().set("pass-info.gold-pass", true);
        pfu.save();
    }

    public static int getChallengesCompleted(Player player) {
        return getPlayerFile(player).get().getInt("pass-info.challenges-completed");
    }

    public static boolean hasCopy(Player player) {
        return getPlayerFile(player).get().getBoolean("pass-info.gold-pass");
    }
}

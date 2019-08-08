package dev.nuer.proudly.points;

import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.data.utils.PlayerFileUtil;
import org.bukkit.entity.Player;

public class PlayerPointManager {

    public static void incrementPoints(Player player, int pointsToAdd) {
        setPoints(player, getPoints(player) + pointsToAdd);
    }

    public static void decrementPoints(Player player, int pointsToRemove) {
        setPoints(player, getPoints(player) - pointsToRemove);
    }

    public static void setPoints(Player player, int points) {
        if (points > getPoints(player)) {

        }
        //Set the new points
        PlayerFileUtil pfu = PlayerDataManager.getPlayerFile(player);
        pfu.get().set("pass-info.points", points);
        pfu.save();
    }

    public static int getPoints(Player player) {
        return PlayerDataManager.getPlayerFile(player).get().getInt("pass-info.points");
    }
}

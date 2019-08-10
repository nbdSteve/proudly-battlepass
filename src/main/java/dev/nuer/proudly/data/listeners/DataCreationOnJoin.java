package dev.nuer.proudly.data.listeners;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.points.PlayerPointManager;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Class that handles generating and loading a players data file when they join the server
 */
public class DataCreationOnJoin implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        PlayerDataManager.getPlayerFile(event.getPlayer());
        if (!FileManager.get("config").getBoolean("enable-welcome-message")) return;
        Bukkit.getScheduler().runTaskLater(BattlePass.instance, () -> {
            if (PlayerDataManager.isGold(event.getPlayer())) {
                MessageUtil.message("messages", "player-welcome.gold-pass", event.getPlayer(),
                        "{player}", event.getPlayer().getName(),
                        "{tier}", String.valueOf(PlayerTierManager.getTier(event.getPlayer())),
                        "{points}", BattlePass.df.format(PlayerPointManager.getPoints(event.getPlayer())),
                        "{challenges-completed}", String.valueOf(PlayerDataManager.getChallengesCompleted(event.getPlayer())));
            } else {
                MessageUtil.message("messages", "player-welcome.coal-pass", event.getPlayer(),
                        "{player}", event.getPlayer().getName(),
                        "{tier}", String.valueOf(PlayerTierManager.getTier(event.getPlayer())),
                        "{points}", BattlePass.df.format(PlayerPointManager.getPoints(event.getPlayer())),
                        "{challenges-completed}", String.valueOf(PlayerDataManager.getChallengesCompleted(event.getPlayer())));
            }
        }, 10L);
    }
}

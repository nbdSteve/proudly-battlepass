package dev.nuer.proudly.points.listeners;

import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.points.events.PlayerPointIncreaseEvent;
import dev.nuer.proudly.tiers.PlayerTierManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PointTierListener implements Listener {

    @EventHandler
    public void pointGain(PlayerPointIncreaseEvent event) {
        if (event.getTotalPoints() >= FileManager.get("tier_config").getInt((PlayerTierManager.getTier(event.getPlayer()) + 1) + ".points-required") - 0.1) {
            PlayerTierManager.incrementTier(event.getPlayer(), 1);
        }
    }
}

package dev.nuer.proudly.tiers.listeners;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.tiers.events.PlayerTierUpEvent;
import dev.nuer.proudly.utils.MessageUtil;
import dev.nuer.proudly.utils.TierCommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTierListener implements Listener {

    @EventHandler
    public void tierUp(PlayerTierUpEvent event) {
        //Run the commands for the coal tier
        TierCommandUtil.execute("tier_config", event.getNewPlayerTier() + ".rewards.coal.commands", event.getPlayer());
        //If they have a copy of the battlepass run gold commands
        if (PlayerDataManager.isGold(event.getPlayer())) {
            TierCommandUtil.execute("tier_config", event.getNewPlayerTier() + ".rewards.gold.commands", event.getPlayer());
        }
        //Send the player a tier message
        MessageUtil.message("tier_config", event.getNewPlayerTier() + ".rewards.message", event.getPlayer());
        //Play a sound
        if (FileManager.get("tier_config").getBoolean("tier-up-notification.sound.enabled")) {
            event.getPlayer().playSound(event.getPlayer().getLocation(),
                    Sound.valueOf(FileManager.get("tier_config").getString("tier-up-notification.sound.type").toUpperCase()),
                    FileManager.get("tier_config").getInt("tier-up-notification.sound.volume"),
                    FileManager.get("tier_config").getInt("tier-up-notification.sound.pitch"));
        }
        //Shoot a firework
        if (FileManager.get("tier_config").getBoolean("tier-up-notification.firework.enabled")) {
            Bukkit.getScheduler().runTask(BattlePass.instance, () -> {
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
            });
        }
    }
}
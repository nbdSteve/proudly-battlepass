package dev.nuer.proudly.tiers.listeners;

import dev.nuer.pp.PassPlus;
import dev.nuer.pp.enable.FileManager;
import dev.nuer.pp.playerData.PlayerDataManager;
import dev.nuer.pp.tiers.events.PlayerTierUpEvent;
import dev.nuer.pp.utils.MessageUtil;
import dev.nuer.pp.utils.TierCommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTierListener implements Listener {

    @EventHandler
    public void tierUp(PlayerTierUpEvent event) {
        //Run the commands for the free tier
        TierCommandUtil.execute("tier_config", event.getNewPlayerTier() + ".rewards.free.commands", event.getPlayer());
        //If they have a copy of the battlepass run premium commands
        if (PlayerDataManager.hasCopy(event.getPlayer())) {
            TierCommandUtil.execute("tier_config", event.getNewPlayerTier() + ".rewards.premium.commands", event.getPlayer());
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
            Bukkit.getScheduler().runTask(PassPlus.instance, () -> {
                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
            });
        }
    }
}
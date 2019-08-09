package dev.nuer.proudly.challenges.listeners;

import dev.nuer.proudly.challenges.events.PlayerChallengeCompletionEvent;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.points.PlayerPointManager;
import dev.nuer.proudly.utils.MessageUtil;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChallengeCompletionListener implements Listener {

    @EventHandler
    public void complete(PlayerChallengeCompletionEvent event) {
        if (event.isCancelled()) return;
        //Set the challenge as complete
        event.getChallenge().setComplete(event.getPlayer());
        //Increase the number of points the player has
        PlayerPointManager.incrementPoints(event.getPlayer(), event.getChallenge().getPayout());
        String cType = event.getChallenge().getClusterTypeString();
        //Send the player the message
        if (FileManager.get("config").getBoolean(cType + "pass.completion-notification.message.enabled")) {
            MessageUtil.message("config", cType + "pass.completion-notification.message.text", event.getPlayer(),
                    "{challenge-id}", event.getChallenge().getID());
        }
        //Play a sound
        if (FileManager.get("config").getBoolean(cType + "pass.completion-notification.sound.enabled")) {
            event.getPlayer().playSound(event.getPlayer().getLocation(),
                    Sound.valueOf(FileManager.get("config").getString(cType + "pass.completion-notification.sound.type").toUpperCase()),
                    FileManager.get("config").getInt(cType + "pass.completion-notification.sound.volume"),
                    FileManager.get("config").getInt(cType + "pass.completion-notification.sound.pitch"));
        }
        //Shoot a firework
        if (FileManager.get("config").getBoolean(cType + "pass.completion-notification.firework.enabled")) {
            event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
        }
    }
}

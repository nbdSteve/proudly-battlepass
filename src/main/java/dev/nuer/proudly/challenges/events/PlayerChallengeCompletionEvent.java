package dev.nuer.proudly.challenges.events;

import dev.nuer.proudly.challenges.Challenge;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChallengeCompletionEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Challenge challenge;
    private boolean cancel;

    public PlayerChallengeCompletionEvent(Player player, Challenge challenge) {
        this.player = player;
        this.challenge = challenge;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}

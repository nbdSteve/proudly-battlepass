package dev.nuer.proudly.challenges.events;

import dev.nuer.proudly.challenges.Cluster;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChallengeClusterUnlockEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Cluster cluster;
    private boolean cancel;

    public ChallengeClusterUnlockEvent(Cluster cluster) {
        this.cluster = cluster;
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

    public Cluster getCluster() {
        return cluster;
    }
}

package dev.nuer.proudly.points.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerPointIncreaseEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();
    private Player player;
    private int pointsGained;
    private int totalPoints;
    private boolean cancel;

    public PlayerPointIncreaseEvent(Player player, int pointsGained, int totalPoints) {
        this.player = player;
        this.pointsGained = pointsGained;
        this.totalPoints = totalPoints;
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
        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPointsGained() {
        return pointsGained;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
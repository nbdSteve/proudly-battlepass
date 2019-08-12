package dev.nuer.proudly.challenges.listeners;

import dev.nuer.proudly.challenges.Challenge;
import dev.nuer.proudly.challenges.ChallengeType;
import dev.nuer.proudly.challenges.Cluster;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.ClusterManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ChallengeListener implements Listener {

    //Player block mine challenge
    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_MINE)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getBlock().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                        if (challenge.getData() != -1) {
                            if (event.getBlock().getData() != challenge.getData())
                                continue;
                        }
                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getPlayer())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_MINE)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getBlock().getType().toString().equalsIgnoreCase(challenge.getElement())) {
                            continue;
                        }
                        if (challenge.getData() != -1) {
                            if (event.getBlock().getData() != challenge.getData()) {
                                continue;
                            }
                        }
                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
    }

    //Player block place challenge
    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_PLACE)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getBlockPlaced().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                        if (challenge.getData() != -1) {
                            if (event.getBlockPlaced().getData() != challenge.getData())
                                continue;
                        }
                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getPlayer())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_PLACE)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getBlockPlaced().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                        if (challenge.getData() != -1) {
                            if (event.getBlockPlaced().getData() != challenge.getData())
                                continue;
                        }
                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
    }

    //Player kills challenge
    @EventHandler
    public void playerKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_KILLS)) continue;
                    if (challenge.getProgress(event.getEntity().getKiller()) == -1) continue;
                    challenge.progress(event.getEntity().getKiller());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getEntity().getKiller())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_KILLS)) continue;
                    if (challenge.getProgress(event.getEntity().getKiller()) == -1) continue;
                    challenge.progress(event.getEntity().getKiller());
                }
            }
        }
    }

    //Player deaths challenge
    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_DEATHS)) continue;
                    if (challenge.getProgress(event.getEntity()) == -1) continue;
                    challenge.progress(event.getEntity());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getEntity())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_DEATHS)) continue;
                    if (challenge.getProgress(event.getEntity()) == -1) continue;
                    challenge.progress(event.getEntity());
                }
            }
        }
    }

    //Player kill mob challenge
    @EventHandler
    public void mobDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_KILL_MOB)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getEntity().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                    }
                    if (challenge.getProgress(event.getEntity().getKiller()) == -1) continue;
                    challenge.progress(event.getEntity().getKiller());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getEntity().getKiller())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_KILL_MOB)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getEntity().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                    }
                    if (challenge.getProgress(event.getEntity().getKiller()) == -1) continue;
                    challenge.progress(event.getEntity().getKiller());
                }
            }
        }
    }

    //Player chat challenge
    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_CHAT)) continue;
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getPlayer())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_CHAT)) continue;
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
    }

    //Player fish challenge
    @EventHandler
    public void playerFish(PlayerFishEvent event) {
        if (event.getCaught() == null) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_FISH)) continue;
//                    if (!challenge.getElement().equalsIgnoreCase("")) {
//                        if (!event.getHook().getType().toString().equalsIgnoreCase(challenge.getElement()))
//                            continue;
//                        if (challenge.getData() != -1) {
//                            PassPlus.log.info("" + event.getCaught().getType().getTypeId());
//                            if (event.getCaught().getType().getTypeId() != challenge.getData()) continue;
//                        }
//                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getPlayer())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_FISH)) continue;
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
    }

    //Player consume food event
    @EventHandler
    public void playerEat(PlayerItemConsumeEvent event) {
        if (event.isCancelled()) return;
        for (Cluster cluster : ClusterManager.coalClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_CONSUME)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getItem().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                        if (challenge.getData() != -1) {
                            if (event.getItem().getDurability() != challenge.getData())
                                continue;
                        }
                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
        if (!PlayerDataManager.isGold(event.getPlayer())) return;
        for (Cluster cluster : ClusterManager.goldClusters.values()) {
            if (cluster.isUnlocked()) {
                for (Challenge challenge : cluster.getChallenges()) {
                    if (!challenge.getType().equals(ChallengeType.PLAYER_CONSUME)) continue;
                    if (!challenge.getElement().equalsIgnoreCase("")) {
                        if (!event.getItem().getType().toString().equalsIgnoreCase(challenge.getElement()))
                            continue;
                        if (challenge.getData() != -1) {
                            if (event.getItem().getDurability() != challenge.getData())
                                continue;
                        }
                    }
                    if (challenge.getProgress(event.getPlayer()) == -1) continue;
                    challenge.progress(event.getPlayer());
                }
            }
        }
    }
}
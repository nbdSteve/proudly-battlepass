package dev.nuer.proudly.challenges;

import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.ClusterManager;
import dev.nuer.proudly.points.PlayerPointManager;
import org.bukkit.entity.Player;

public class Challenge {
    private int cluster;
    private ChallengeType type;
    private String ID;
    private String element;
    private short data;
    private int total;
    private int payout;
    private ClusterType clusterType;

    public Challenge(int cluster, ChallengeType type, String ID, String element, short data, int total, int payout, ClusterType clusterType) {
        this.cluster = cluster;
        this.type = type;
        this.ID = ID;
        this.element = element;
        this.data = data;
        this.total = total;
        this.payout = payout;
        this.clusterType = clusterType;
    }

    public int getProgress(Player player) {
        if (PlayerDataManager.getPlayerFile(player).get().get())
    }

    public String getClusterType(ClusterType clusterType) {
        if (clusterType.equals(ClusterType.COAL)) return ""
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public ChallengeType getType() {
        return type;
    }

    public void setType(ChallengeType type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public short getData() {
        return data;
    }

    public void setData(short data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPayout() {
        return payout;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }
}
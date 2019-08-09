package dev.nuer.proudly.challenges;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.challenges.events.PlayerChallengeCompletionEvent;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.data.utils.PlayerFileUtil;
import dev.nuer.proudly.utils.CustomEventUtil;
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
        String cType = getClusterTypeString();
        if (PlayerDataManager.getPlayerFile(player).get().get(cType + "challenges.cluster-" + cluster + "." + getID()) == null)
            return 0;
        return PlayerDataManager.getPlayerFile(player).get().getInt(cType + "challenges.cluster-" + cluster + "." + getID());
    }

    public void progress(Player player) {
        PlayerFileUtil pfu = PlayerDataManager.getPlayerFile(player);
        String cType = getClusterTypeString();
        BattlePass.log.info(cType);
        if (getProgress(player) + 1 >= getTotal() - 0.1) {
            CustomEventUtil.fire(new PlayerChallengeCompletionEvent(player, this));
        } else {
            pfu.get().set(cType + "challenges.cluster-" + cluster + "." + getID(), getProgress(player) + 1);
            pfu.save();
        }
    }

    public void setComplete(Player player) {
        PlayerFileUtil pfu = PlayerDataManager.getPlayerFile(player);
        String cType = getClusterTypeString();
        if (pfu.get().get(cType + "challenges.cluster-" + cluster + "." + getID()) == null)
            return;
        pfu.get().set(cType + "challenges.cluster-" + cluster + "." + getID(), -1);
        pfu.get().set("pass-info.challenges-completed", pfu.get().getInt("pass-info.challenges-completed") + 1);
        pfu.save();
    }

    public String getClusterTypeString() {
        if (this.clusterType.equals(ClusterType.COAL)) return "coal-";
        return "gold-";
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

    public void setClusterType(ClusterType clusterType) {
        this.clusterType = clusterType;
    }

    public ClusterType getClusterType() {
        return clusterType;
    }
}
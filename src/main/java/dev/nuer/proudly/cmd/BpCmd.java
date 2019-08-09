package dev.nuer.proudly.cmd;

import dev.nuer.proudly.challenges.events.PlayerChallengeCompletionEvent;
import dev.nuer.proudly.enable.ClusterManager;
import dev.nuer.proudly.utils.CustomEventUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BpCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CustomEventUtil.fire(new PlayerChallengeCompletionEvent((Player) sender, ClusterManager.getCluster(1, true).getChallenges().get(0)));
        return true;
    }
}

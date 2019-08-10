package dev.nuer.proudly.cmd;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.data.PlayerDataManager;
import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.points.PlayerPointManager;
import dev.nuer.proudly.tiers.PlayerTierManager;
import dev.nuer.proudly.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
//            new MainMenuGui((Player) sender).open((Player) sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("tier")) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "tier-query", (Player) sender,
                            "{tier}", String.valueOf(PlayerTierManager.getTier((Player) sender)));
                } else {
                    BattlePass.log.info("Only players can view their current tier.");
                }
            }
            if (args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("point") || args[0].equalsIgnoreCase("points")) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "points-query", (Player) sender,
                            "{points}", String.valueOf(PlayerPointManager.getPoints((Player) sender)));
                } else {
                    BattlePass.log.info("Only players can view their current points.");
                }
            }
            if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("challenge") || args[0].equalsIgnoreCase("challenges")) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "challenge-query", (Player) sender,
                            "{challenges-completed}", String.valueOf(PlayerDataManager.getChallengesCompleted((Player) sender)));
                } else {
                    BattlePass.log.info("Only players can view their completed challenges.");
                }
            }
        }
        return true;
    }
}

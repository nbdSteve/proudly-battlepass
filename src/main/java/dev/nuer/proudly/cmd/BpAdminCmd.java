package dev.nuer.proudly.cmd;

import dev.nuer.proudly.BattlePass;
import dev.nuer.proudly.cmd.adminSub.GiveCmd;
import dev.nuer.proudly.cmd.adminSub.PointsCmd;
import dev.nuer.proudly.cmd.adminSub.ReloadCmd;
import dev.nuer.proudly.cmd.adminSub.TierCmd;
import dev.nuer.proudly.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BpAdminCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("battlepass.admin-cmd.help")) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "admin-cmd-help", (Player) sender);
                } else {
                    BattlePass.log.info("The admin help command message can only be view in game");
                }
            } else {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "permission-debug", (Player) sender, "{node}", "battlepass.admin-cmd.help");
                }
            }
        }else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("battlepass.admin-cmd.reload")) {
                    if (sender instanceof Player) {
                        MessageUtil.message("messages", "permission-debug", (Player) sender,
                                "{node}", "battlepass.admin-cmd.reload");
                    }
                    return true;
                }
                ReloadCmd.onCmd(sender);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give")) {
                if (!sender.hasPermission("battlepass.admin-cmd.give")) {
                    if (sender instanceof Player) {
                        MessageUtil.message("messages", "permission-debug", (Player) sender,
                                "{node}", "battlepass.admin-cmd.give");
                    }
                    return true;
                }
                GiveCmd.onCmd(sender, args);
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("tier")) {
                if (!sender.hasPermission("battlepass.admin-cmd.tier")) {
                    if (sender instanceof Player) {
                        MessageUtil.message("messages", "permission-debug", (Player) sender,
                                "{node}", "battlepass.admin-cmd.tier");
                    }
                    return true;
                }
                TierCmd.onCmd(sender, args);
            } else if (args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("point") || args[0].equalsIgnoreCase("points")) {
                if (!sender.hasPermission("battlepass.admin-cmd.point")) {
                    if (sender instanceof Player) {
                        MessageUtil.message("messages", "permission-debug", (Player) sender,
                                "{node}", "battlepass.admin-cmd.point");
                    }
                    return true;
                }
                PointsCmd.onCmd(sender, args);
            }
        } else {
            if (sender instanceof Player) {
                MessageUtil.message("messages", "command-debug", (Player) sender,
                        "{reason}", "The command arguments are not valid, please see the GitHub page for command help");
            } else {
                BattlePass.log.info("The command you entered is invalid.");
            }
        }
        return true;
    }
}

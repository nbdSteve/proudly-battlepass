package dev.nuer.proudly.cmd.adminSub;

import dev.nuer.proudly.enable.FileManager;
import dev.nuer.proudly.points.PlayerPointManager;
import dev.nuer.proudly.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointsCmd {

    public static void onCmd(CommandSender sender, String[] args) {
        if (args[1].equalsIgnoreCase("set")
                || args[1].equalsIgnoreCase("=")) {
            //Set the players points
            PlayerPointManager.setPoints(Bukkit.getPlayer(args[2]), Integer.parseInt(args[3]));
        } else if (args[1].equalsIgnoreCase("inc")
                || args[1].equalsIgnoreCase("increment")
                || args[1].equalsIgnoreCase("+")) {
            //Increment the players points
            PlayerPointManager.incrementPoints(Bukkit.getPlayer(args[2]), Integer.parseInt(args[3]));
        } else if (args[1].equalsIgnoreCase("dec")
                || args[1].equalsIgnoreCase("decrement")
                || args[1].equalsIgnoreCase("-")) {
            //Decrement the players points
            PlayerPointManager.decrementPoints(Bukkit.getPlayer(args[2]), Integer.parseInt(args[3]));
        }
        //Send the admin a message so that they know their command worked
        if (sender instanceof Player) {
            MessageUtil.message("messages", "admin-cmd-points-manipulation", (Player) sender,
                    "{player}", Bukkit.getPlayer(args[2]).getName(),
                    "{points}", String.valueOf(PlayerPointManager.getPoints(Bukkit.getPlayer(args[2]))));
        }
    }
}

package dev.nuer.proudly.utils;

import dev.nuer.proudly.enable.FileManager;
import org.bukkit.Bukkit;

/**
 * Class that handles broadcasting messages
 */
public class BroadcastUtil {

    /**
     * Broadcasts the specified message to the server
     *
     * @param filePath String, the internal path for the broadcast message
     */
    public static void broadcast(String filePath) {
        for (String line : FileManager.get("config").getStringList(filePath)) {
            Bukkit.broadcastMessage(ColorUtil.colorize(line));
        }
    }

    /**
     * Broadcasts the specified message to the server
     *
     * @param filePath String, the internal path for the broadcast message
     */
    public static void broadcast(String filePath, String placeholder_1, String replacement_1) {
        for (String line : FileManager.get("config").getStringList(filePath)) {
            Bukkit.broadcastMessage(ColorUtil.colorize(line)
            .replace(placeholder_1, replacement_1));
        }
    }
}
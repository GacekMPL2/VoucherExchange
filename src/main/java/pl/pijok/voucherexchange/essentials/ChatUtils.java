package pl.pijok.voucherexchange.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtils {
    private static String prefix;

    public static void setPrefix(String a) {
        prefix = a;
    }

    public static String fixColor(String message) {
        message = message.replace("&", "§");
        return message;
    }

    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers())
            sendMessage(player, message);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(fixColor(prefix + message));
    }

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(fixColor(prefix + message));
    }
}

package pl.pijok.voucherexchange.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Debug {
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static String prefix = "[SkyblockFix]";

    public static void setPrefix(String a) {
        prefix = a;
    }

    public static void log(String a) {
        a = prefix + a;
        console.sendMessage(a.replace("&", "§"));
    }

    public static void sendError(String a) {
        log("&c============");
        log("&c" + a);
        log("&c============");
    }

    public static void log(Object a) {
        a = prefix + a;
        console.sendMessage(String.valueOf(a).replace("&", "§"));
    }
}

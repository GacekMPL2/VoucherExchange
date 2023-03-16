package pl.pijok.voucherexchange.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.pijok.voucherexchange.essentials.NotNull;
import pl.pijok.voucherexchange.VoucherExchange;
import pl.pijok.voucherexchange.essentials.ChatUtils;

public class VoucherCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission("vouchers.admin")) {
                ChatUtils.sendMessage(player, "&cNie masz dostepu do tej komendy!");
                return true;
            }
        }
        if (args.length == 1 &&
                args[0].equalsIgnoreCase("reload")) {
            VoucherExchange.getInstance().loadStuff();
            ChatUtils.sendMessage(sender, "&aReloaded!");
            return true;
        }
        if (args.length == 3 &&
                args[0].equalsIgnoreCase("give")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                ChatUtils.sendMessage(sender, "&cTen gracz jest offline!");
                return true;
            }
            String ID = args[2];
            ItemStack voucher = new ItemStack(VoucherExchange.getVoucherController().getVoucherByID(ID));
            if (voucher == null) {
                ChatUtils.sendMessage(sender, "&cNie ma kuponu o takiej wartosci!");
                return true;
            }
            target.getInventory().addItem(new ItemStack[] { voucher });
            ChatUtils.sendMessage(target, "&a&lOtrzymales voucher o ID &e" + ID + " &a&l$!");
            return true;
        }
        ChatUtils.sendMessage(sender, "&7/" + label + " reload");
        ChatUtils.sendMessage(sender, "&7/" + label + " give <nickname> <ID>");
        return true;
    }
}

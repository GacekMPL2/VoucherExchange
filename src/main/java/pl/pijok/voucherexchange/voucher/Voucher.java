package pl.pijok.voucherexchange.voucher;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.pijok.voucherexchange.VoucherExchange;
import pl.pijok.voucherexchange.essentials.ChatUtils;
import pl.pijok.voucherexchange.essentials.ConfigUtils;
import pl.pijok.voucherexchange.essentials.Debug;

import java.util.HashMap;
import java.util.List;

public class Voucher {
    private String ID;

    private VoucherType voucherType;

    private double money;

    private List<String> commands;

    private ItemStack itemStack;

    public Voucher(String ID, VoucherType voucherType, double money, ItemStack itemStack) {
        this.ID = ID;
        this.voucherType = voucherType;
        this.money = money;
        this.itemStack = itemStack;
    }

    public Voucher(String ID, VoucherType voucherType, List<String> commands, ItemStack itemStack) {
        this.ID = ID;
        this.voucherType = voucherType;
        this.commands = commands;
        this.itemStack = itemStack;
    }

    public void exchange(Player player, int amount) {
        if (this.voucherType.equals(VoucherType.MONEY)) {
            double value = this.money * amount;
            VoucherExchange.getEcon().depositPlayer((OfflinePlayer)player, value);
        } else {
            String command = "";
            for (int i = 0; i < amount; i++) {
                for (String a : this.commands) {
                    command = a;
                    command = command.replace("%player%", player.getName());
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command);
                }
            }
        }
    }

    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
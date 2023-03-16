package pl.pijok.voucherexchange;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pijok.voucherexchange.commands.VoucherCommand;
import pl.pijok.voucherexchange.essentials.ChatUtils;
import pl.pijok.voucherexchange.essentials.ConfigUtils;
import pl.pijok.voucherexchange.essentials.Debug;
import pl.pijok.voucherexchange.listeners.InteractListener;
import pl.pijok.voucherexchange.voucher.VoucherController;

public class VoucherExchange extends JavaPlugin {
    private static VoucherExchange instance;

    private static Economy econ = null;

    private static VoucherController voucherController;

    public void onEnable() {
        instance = this;
        voucherController = new VoucherController();
        ChatUtils.setPrefix("&7&l[&a&lVoucher&7&l] ");
        Debug.setPrefix("[VoucherExchange]");
        ConfigUtils.setPlugin((Plugin)this);
        if (!setupEconomy()) {
            Debug.log(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
            getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        loadStuff();
        getCommand("voucher").setExecutor((CommandExecutor)new VoucherCommand());
        getServer().getPluginManager().registerEvents((Listener)new InteractListener(), (Plugin)this);
    }

    public void onDisable() {}

    public void loadStuff() {
        Debug.log("Ładowanie VoucherExchange");
        Debug.log("Ładowanie Voucherów");
        voucherController.load();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = (Economy)rsp.getProvider();
        return (econ != null);
    }

    public static VoucherExchange getInstance() {
        return instance;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static VoucherController getVoucherController() {
        return voucherController;
    }
}
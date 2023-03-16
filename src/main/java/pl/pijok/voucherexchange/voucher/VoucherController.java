package pl.pijok.voucherexchange.voucher;

import java.util.HashMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.pijok.voucherexchange.VoucherExchange;
import pl.pijok.voucherexchange.essentials.ChatUtils;
import pl.pijok.voucherexchange.essentials.ConfigUtils;

public class VoucherController {
    private HashMap<ItemStack, Voucher> vouchers;

    public void load() {
        this.vouchers = new HashMap<>();
        YamlConfiguration configuration = ConfigUtils.load("vouchers.yml", (Plugin)VoucherExchange.getInstance());
        for (String key : configuration.getConfigurationSection("vouchers").getKeys(false)) {
            ItemStack itemStack = ConfigUtils.getItemstack(configuration, "vouchers." + key + ".item");
            VoucherType voucherType = VoucherType.valueOf(configuration.getString("vouchers." + key + ".type"));
            if (voucherType.equals(VoucherType.MONEY)) {
                this.vouchers.put(itemStack, new Voucher(key, voucherType, configuration.getDouble("vouchers." + key + ".value"), itemStack));
                continue;
            }
            if (voucherType.equals(VoucherType.COMMAND))
                this.vouchers.put(itemStack, new Voucher(key, voucherType, configuration.getStringList("vouchers." + key + ".commands"), itemStack));
        }
    }

    public ItemStack getVoucherByValue(double value) {
        for (ItemStack itemStack : this.vouchers.keySet()) {
            if (((Voucher)this.vouchers.get(itemStack)).getVoucherType().equals(VoucherType.MONEY) && (
                    (Voucher)this.vouchers.get(itemStack)).getMoney() == value)
                return itemStack;
        }
        return null;
    }

    public ItemStack getVoucherByID(String ID) {
        for (ItemStack itemStack : this.vouchers.keySet()) {
            if (((Voucher)this.vouchers.get(itemStack)).getID().equalsIgnoreCase(ID))
                return itemStack;
        }
        return null;
    }

    public boolean isVoucher(ItemStack itemStack) {
        ItemStack toTest = new ItemStack(itemStack);
        toTest.setAmount(1);
        return this.vouchers.containsKey(toTest);
    }

    public void exchangeVoucher(Player player, ItemStack itemStack) {
        ItemStack voucher = new ItemStack(itemStack);
        voucher.setAmount(1);
        Voucher voucherObject = this.vouchers.get(itemStack);
        voucherObject.exchange(player, itemStack.getAmount());
        player.getInventory().removeItem(new ItemStack[] { itemStack });
        ChatUtils.sendMessage(player, "&aWymieniono voucher!");
    }
}


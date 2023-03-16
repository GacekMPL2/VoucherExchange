package pl.pijok.voucherexchange.listeners;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.pijok.voucherexchange.VoucherExchange;
import pl.pijok.voucherexchange.voucher.Voucher;

public class InteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack inHand = player.getInventory().getItemInMainHand();
        if (inHand == null || inHand.getType().equals(Material.AIR))
            return;
        if (VoucherExchange.getVoucherController().isVoucher(inHand))
            VoucherExchange.getVoucherController().exchangeVoucher(player, inHand);
    }
}


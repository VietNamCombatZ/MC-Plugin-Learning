package project01.learningProject01.lockItem;



import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import project01.learningProject01.common.Permissions;

import java.util.UUID;

public class LockItemListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (!LockItemUtil.isLocked(item)) return;

        UUID owner = LockItemUtil.getOwner(item);

        if (owner == null && !p.hasPermission(Permissions.ADMIN)) {
            LockItemUtil.setOwner(item, p.getUniqueId());
            item.getItemMeta().setLore(
                    java.util.List.of("§c[Đã khóa: " + p.getName() + "]")
            );
            return;
        }

        if (owner != null && !owner.equals(p.getUniqueId())) {
            e.setCancelled(true);
            p.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText("§cBạn không sở hữu vật phẩm này")
            );
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;

        ItemStack item = p.getInventory().getItemInMainHand();
        if (!LockItemUtil.isLocked(item)) return;

        UUID owner = LockItemUtil.getOwner(item);
        if (owner != null && !owner.equals(p.getUniqueId())) {
            e.setCancelled(true);
            p.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText("§cBạn không sở hữu vật phẩm này")
            );
        }
    }
}


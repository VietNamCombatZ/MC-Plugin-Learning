package project01.learningProject01.lockItem;



import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import project01.learningProject01.common.Keys;
import project01.learningProject01.common.Permissions;

import java.util.List;

public class LockItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission(Permissions.ADMIN)) return true;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return true;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(
                Keys.LOCKED,
                PersistentDataType.BYTE,
                (byte) 1
        );

        meta.getPersistentDataContainer().remove(Keys.OWNER);
        meta.setLore(List.of(ChatColor.RED + "[Đã khóa]"));
        item.setItemMeta(meta);

        player.sendMessage("§aĐã khóa vật phẩm");
        return true;
    }
}


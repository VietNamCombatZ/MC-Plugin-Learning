package project01.learningProject01.lockItem;


import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import project01.learningProject01.common.Keys;
import project01.learningProject01.common.Permissions;

public class UnlockItemCommand implements CommandExecutor {

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
        meta.getPersistentDataContainer().remove(Keys.LOCKED);
        meta.getPersistentDataContainer().remove(Keys.OWNER);
        meta.setLore(null);
        item.setItemMeta(meta);

        player.sendMessage("§aĐã mở khóa vật phẩm");
        return true;
    }
}


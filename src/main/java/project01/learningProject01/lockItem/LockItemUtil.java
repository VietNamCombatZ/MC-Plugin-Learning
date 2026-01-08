package project01.learningProject01.lockItem;


import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import project01.learningProject01.common.Keys;

import java.util.UUID;

public class LockItemUtil {

    public static boolean isLocked(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer()
                .has(Keys.LOCKED, PersistentDataType.BYTE);
    }

    public static UUID getOwner(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        String uuid = meta.getPersistentDataContainer()
                .get(Keys.OWNER, PersistentDataType.STRING);
        return uuid == null ? null : UUID.fromString(uuid);
    }

    public static void setOwner(ItemStack item, UUID uuid) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(
                Keys.OWNER,
                PersistentDataType.STRING,
                uuid.toString()
        );
        item.setItemMeta(meta);
    }
}


package project01.learningProject01.gravitygun;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class GravityGunItem {

    private static final String KEY = "gravity_gun";

    public static ItemStack create(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§6Súng Trọng Lực");
        meta.setLore(List.of(
                "§7Công cụ điều khiển trọng lực",
                "§eChuột phải: Nhấc / Thả",
                "§cChuột trái: Ném"
        ));

        meta.getPersistentDataContainer().set(
                new NamespacedKey(plugin, KEY),
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isGravityGun(JavaPlugin plugin, ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(
                new NamespacedKey(plugin, KEY),
                PersistentDataType.BYTE
        );
    }
}

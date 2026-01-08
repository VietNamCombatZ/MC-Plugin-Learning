package project01.learningProject01.common;


import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
    public static NamespacedKey LOCKED;
    public static NamespacedKey OWNER;

    public static void init(JavaPlugin plugin) {
        LOCKED = new NamespacedKey(plugin, "locked");
        OWNER  = new NamespacedKey(plugin, "owner");
    }
}


package project01.learningProject01;


import project01.learningProject01.listeners.*;
import org.bukkit.Bukkit;


import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;


public final class LearningProject01 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new CrawlListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

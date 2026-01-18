package project01.learningProject01;


import project01.learningProject01.common.*;
import project01.learningProject01.crawl.*;
import project01.learningProject01.gravitygun.command.*;
import project01.learningProject01.gravitygun.listener.*;
import project01.learningProject01.gravitygun.particle.*;
import project01.learningProject01.lockItem.*;
import project01.learningProject01.gravitygun.*;

import org.bukkit.Bukkit;


import org.bukkit.plugin.java.JavaPlugin;


public final class LearningProject01 extends JavaPlugin {

    @Override
    public void onEnable() {

        // Plugin startup logic

        Bukkit.getPluginManager().registerEvents(new CrawlListener(this), this);

        Keys.init(this);

        getCommand("lock-item").setExecutor(new LockItemCommand());
        getCommand("lock-item").setTabCompleter(new LockItemTab());
        getCommand("unlock-item").setExecutor(new UnlockItemCommand());

        Bukkit.getPluginManager().registerEvents(
                new LockItemListener(), this
        );

        saveDefaultConfig();

        GravityCommand gravityCommand = new GravityCommand(this);
        getCommand("gravity").setExecutor(gravityCommand);
        getCommand("gravity").setTabCompleter(gravityCommand);

        Bukkit.getPluginManager().registerEvents(
                new GravityInteractListener(this),
                this
        );
        Bukkit.getPluginManager().registerEvents(
                new GravityGlowListener(this), this
        );

        new GravityParticleTask(this).start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

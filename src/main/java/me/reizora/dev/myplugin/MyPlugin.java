package me.reizora.dev.myplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        PlayerListener playerListener = new PlayerListener();
        getServer().getPluginManager().registerEvents(playerListener   ,this);
        System.out.println("The plugin has started");
        Bukkit.getScheduler().runTaskTimer(this, PlayerMonitor::checkAfk,0,1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getScheduler().cancelTasks(this);
    }
}

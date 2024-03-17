package me.reizora.dev.myplugin;

import me.reizora.dev.myplugin.command.AFKTimerCommand;
import me.reizora.dev.myplugin.command.LavaFloorCommand;
import me.reizora.dev.myplugin.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        System.out.println("The plugin has started");
        Bukkit.getScheduler().runTaskTimer(this, PlayerMonitor::checkAfk, 0, 1);
        Objects.requireNonNull(getCommand("setAFKTimer")).setExecutor(new AFKTimerCommand());
        Objects.requireNonNull(getCommand("LavaFloor")).setExecutor(new LavaFloorCommand());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getScheduler().cancelTasks(this);
    }
}

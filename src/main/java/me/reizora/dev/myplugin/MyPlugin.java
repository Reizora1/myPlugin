package me.reizora.dev.myplugin;

import me.reizora.dev.myplugin.commands.eventCommands;
import me.reizora.dev.myplugin.commands.playerCommands;
import me.reizora.dev.myplugin.commands.serverCommands;
import me.reizora.dev.myplugin.listeners.entityEvents;
import me.reizora.dev.myplugin.listeners.objectEvents;
import me.reizora.dev.myplugin.listeners.playerEvents;
import org.bukkit.plugin.java.JavaPlugin;
public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        System.out.println("PLUGIN HAS STARTED!!!!!!!!!!!!!");
        objectEvents obj = new objectEvents();
        playerEvents player = new playerEvents();
        entityEvents entity = new entityEvents();
        afkMonitor afk = new afkMonitor(this);
        afk.startIdleCheckTask();

        //eventAPIs
        getServer().getPluginManager().registerEvents(obj, this);
        getServer().getPluginManager().registerEvents(player, this);
        getServer().getPluginManager().registerEvents(entity, this);

        //eventCommands
        getCommand("break").setExecutor(new eventCommands(obj));
        getCommand("place").setExecutor(new eventCommands(obj));
        //playerCommands
        getCommand("fly").setExecutor(new playerCommands());
        getCommand("craft").setExecutor(new playerCommands());
        getCommand("creative").setExecutor(new playerCommands());
        getCommand("survival").setExecutor(new playerCommands());
        getCommand("night").setExecutor(new playerCommands());
        getCommand("day").setExecutor(new playerCommands());
        getCommand("setspawn").setExecutor(new playerCommands());
        //serverCommands
        getCommand("setMOTD").setExecutor(new serverCommands());
        getCommand("setSize").setExecutor(new serverCommands());
        getCommand("setTimeout").setExecutor(new serverCommands());
        getCommand("setAFKTimer").setExecutor(new serverCommands());
        getCommand("getAFKTimer").setExecutor(new serverCommands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package me.reizora.dev.myplugin;

import me.reizora.dev.myplugin.commands.playerCommands;
import me.reizora.dev.myplugin.listeners.playerEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class afkMonitor {
    JavaPlugin plugin;
    World world;
    public afkMonitor(JavaPlugin plugin){
        this.plugin = plugin;
    }
    public static long defaultAfkTimer = 10000L;                                                                        // 1000L = 1sec.
    public static long newAfkTimer = 10L;
    private static final HashMap<Player, Long> lastRecordedMovement = new HashMap<>();                                  // HashMap to store player object as keys and the passed value; System.currentTimeMillis() as its value.

    public static void playerJoined(Player player){                                                                     // Immediately fetches the system time upon playerJoin and store it in the lastRecordedMovement hashmap.
        lastRecordedMovement.put(player, System.currentTimeMillis());
    }
    public static void playerLeft(Player player){ // Removes an instance of a players movement data from the hashmap.
        lastRecordedMovement.remove(player);
    }
    public static void playerMoved(Player player){                                                                      // Called in the onPlayerMove() in the playerEvents.java. It is the logic for determining whenever a player gets out of an AFK status.
        boolean wasAFK = System.currentTimeMillis() - lastRecordedMovement.get(player) >= defaultAfkTimer;              // When the player moves, this gets the currentTimeMillis from the system and compares it--
                                                                                                                        // with the data that is last recorded from the lastRecordedMovement hashmap.
        if (wasAFK){
            player.sendMessage(ChatColor.GREEN+ "You are no longer AFK!");
            teleportToLastLocation(player);                                                                             // When the player moves, this method is called to return the player to its last recorded position before going afk.
        }
        lastRecordedMovement.put(player, System.currentTimeMillis());                                                   // THE UPDATE LINE. This updates the lastRecordedMovement hashmap to store the new currentTimeMillis from the system when the player moves.
                                                                                                                        // The update is necessary for the wasAFK variable to evaluate to either true or false.
    }

    public void startIdleCheckTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                long lastMoveTime = lastRecordedMovement.getOrDefault(player, 0L);
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastMoveTime >= defaultAfkTimer) {
                    teleportToSpawn(player);
                    System.out.println("The following player teleported due to inactivity: " +player.getName());
                }
            }
        }, 0L, 20L * newAfkTimer);                                                                                // Check every 10 seconds (20 ticks * x seconds)
    }

    private void teleportToSpawn(Player player) {
        world = player.getWorld();
        if(playerCommands.getPlayerSpawn(player) != null){
            world.setSpawnLocation(playerCommands.getPlayerSpawn(player));
        }
        else {
            world.setSpawnLocation(playerEvents.playerJoinLocation.get(player));
        }
        Location spawnLocation = world.getSpawnLocation();
        if(spawnLocation != null){
            player.teleport(spawnLocation);
            player.sendMessage(ChatColor.YELLOW + "You have been automatically teleported to spawn due to inactivity.");
            player.sendMessage("You are now AFK!");
        }
    }
    private static void teleportToLastLocation(Player player){
        World world = player.getWorld();
        world.setSpawnLocation(playerEvents.playerLastLocation.get(player));
        Location spawnLocation = world.getSpawnLocation();

        if(spawnLocation != null){
            player.teleport(spawnLocation);
            player.sendMessage(ChatColor.YELLOW + "Returning to last recorded position...");
        }
    }
}

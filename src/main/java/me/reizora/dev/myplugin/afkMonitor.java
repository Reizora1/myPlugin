package me.reizora.dev.myplugin;

import me.reizora.dev.myplugin.commands.playerCommands;
import me.reizora.dev.myplugin.commands.serverCommands;
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
    private static final HashMap<Player, Long> lastRecordedMovement = new HashMap<>();                                  // HashMap to store player object as keys and the passed value; System.currentTimeMillis() as its value.
    private static final HashMap<Player, Boolean> isPlayerAfk = new HashMap<>();
    public static void playerJoined(Player player){                                                                     // Immediately fetches the system time upon playerJoin and store it in the lastRecordedMovement hashmap.
        lastRecordedMovement.put(player, System.currentTimeMillis());
    }
    public static void playerLeft(Player player){ // Removes an instance of a players movement data from the hashmap.
        lastRecordedMovement.remove(player);
        playerCommands.playerSpawnLocations.remove(player);
        playerEvents.playerLastLocation.remove(player);
        playerEvents.playerJoinLocation.remove(player);
    }
    public static void playerMoved(Player player){                                                                      // Called in the onPlayerMove() in the playerEvents.java. It is the logic for determining whenever a player gets out of an AFK status.
        boolean wasAFK = System.currentTimeMillis() - lastRecordedMovement.get(player) >= defaultAfkTimer;              // When the player moves, this gets the currentTimeMillis from the system and compares it--
                                                                                                                        // with the data that is last recorded from the lastRecordedMovement hashmap.
        if (wasAFK){
            player.sendMessage(ChatColor.GREEN+ "You are no longer AFK!");
            isPlayerAfk.remove(player);
            teleportToLastLocation(player);                                                                             // When the player moves, this method is called to return the player to its last recorded position before going afk.
        }
        lastRecordedMovement.put(player, System.currentTimeMillis());                                                   // THE UPDATE LINE. This updates the lastRecordedMovement hashmap to store the new currentTimeMillis from the system when the player moves.
                                                                                                                        // The update is necessary for the wasAFK variable to evaluate to either true or false.
    }

    public void startIdleCheckTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long currentTime = System.currentTimeMillis();

            for (Player player : Bukkit.getOnlinePlayers()) {
                long lastMoveTime = lastRecordedMovement.getOrDefault(player, currentTime);
                if (currentTime - lastMoveTime >= defaultAfkTimer) {
                    isPlayerAfk.put(player, true);
                    player.sendMessage("The player has been afk for " +(System.currentTimeMillis() - lastMoveTime) / 1000L +" seconds.");

                    System.out.println("The player has been afk for " +(System.currentTimeMillis() - lastMoveTime) / 1000L +" seconds.");
                    //System.out.println(defaultAfkTimer);
                }
                else {
                    isPlayerAfk.put(player, false);
                }
                teleportToSpawn(player, isPlayerAfk.get(player));
            }
        }, 0L, 20); // real-time checking of afk status in 1sec interval.
    }

    private void teleportToSpawn(Player player, Boolean isAFK) {
        world = player.getWorld();

        if(playerCommands.getPlayerSpawn(player) != null){
            world.setSpawnLocation(playerCommands.getPlayerSpawn(player));
        }
        else {
            world.setSpawnLocation(playerEvents.playerJoinLocation.get(player));
        }

        Location playerLocation = player.getLocation();
        Location spawnLocation = world.getSpawnLocation();
        System.out.println(isPlayerAfk.get(player)); // prints true or false.

        if (isAFK && !playerLocation.equals(spawnLocation)){
            player.teleport(spawnLocation);
            lastRecordedMovement.put(player, System.currentTimeMillis());
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

package me.reizora.dev.myplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerMonitor {
    public static final HashMap<Player, Long> playerTimeList = new HashMap<>();
    public static final HashMap<Player, Location> playerLastLocation = new HashMap<>();
    public static final HashMap<Player, Boolean> playerIsAFK = new HashMap<>();
    private static int ticksCounter = 0;
    private static int rewardInterval = 2; // in seconds
    public static Material reward = Material.DIAMOND;

    public static void checkAfk() {
        for (HashMap.Entry<Player, Long> entry : playerTimeList.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() > 10000) {
                if (!playerIsAFK.get(entry.getKey())) {
                    playerIsAFK.put(entry.getKey(), true);
                    entry.getKey().setInvulnerable(true);
                    playerLastLocation.put(entry.getKey(), entry.getKey().getLocation());
                    entry.getKey().teleport(new Location(entry.getKey().getWorld(), 0, 64, 0));
                    entry.getKey().sendMessage("You are AFK");
                } else {
                    if (ticksCounter > 20 * rewardInterval) {
                        entry.getKey().sendMessage("You receive: " + reward.toString());
                        entry.getKey().getInventory().addItem(new ItemStack(reward));
                        ticksCounter = 0;
                    } else {
                        ticksCounter++;
                    }
                }
            } else {
                if (playerIsAFK.get(entry.getKey())) {
                    entry.getKey().teleport(playerLastLocation.get(entry.getKey()));
                    entry.getKey().setInvulnerable(false);
                    playerIsAFK.put(entry.getKey(), false);
                    playerLastLocation.remove(entry.getKey());
                    entry.getKey().sendMessage("Leaving AFK");
                }
            }
        }
    }
}



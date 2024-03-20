package me.reizora.dev.myplugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerMonitor {

    static int ticksCounter = 0;

    public static void checkAfk() {
        for (HashMap.Entry<Player, HashMap<String, Object>> entry : Server.playersData.entrySet()) {
            HashMap<String, Object> playerDetail = entry.getValue();
            Player player = entry.getKey();
            if (System.currentTimeMillis() - ((Long) playerDetail.get("playerTime")) > Server.afkTimer * 1000) {
                if (!((Boolean) playerDetail.get("playerIsAFK")).booleanValue()) {
                    playerDetail.put("playerIsAFK", new Boolean(true));
                    player.setInvulnerable(true);
                    playerDetail.put("playerLastLocation", player.getLocation());
                    player.teleport((Location) playerDetail.get("playerSpawn"));
                    player.sendMessage("You are AFK");
                } else {

                    if (ticksCounter > 20 * Server.rewardInterval) {
                        entry.getKey().sendMessage("You receive: " + Server.reward.toString());
                        entry.getKey().getInventory().addItem(new ItemStack(Server.reward));
                        ticksCounter = 0;
                    } else {
                        ticksCounter++;
                    }
                }
            } else {
                player.sendMessage(String.valueOf(System.currentTimeMillis() - ((Long) playerDetail.get("playerTime"))));
                if (((Boolean) playerDetail.get("playerIsAFK")).booleanValue()) {
                    player.teleport(((Location) playerDetail.get("playerLastLocation")));
                    player.setInvulnerable(false);
                    playerDetail.put("playerIsAFK", new Boolean(false));
                    playerDetail.remove("playerLastLocation");
                    player.sendMessage("Leaving AFK");
                }
            }
        }
    }
}



package me.reizora.dev.myplugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerMonitor {

    static int ticksCounter = 0;
    public static void checkAfk() {
        for (HashMap.Entry<Player, Long> entry : Server.playerTimeList.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() > Server.afkTimer*1000){
                if (!Server.playerIsAFK.get(entry.getKey())) {
                    Server.playerIsAFK.put(entry.getKey(), true);
                    entry.getKey().setInvulnerable(true);
                    Server.playerLastLocation.put(entry.getKey(), entry.getKey().getLocation());
                    entry.getKey().teleport(new Location(entry.getKey().getWorld(), 0, 64, 0));
                    entry.getKey().sendMessage("You are AFK");
                } else {

                    if (ticksCounter > 20 * Server.rewardInterval) {
                        entry.getKey().sendMessage("You receive: " + Server.reward.toString());
                        entry.getKey().getInventory().addItem(new ItemStack(Server.reward));
                        ticksCounter = 0;
                    } else {
                        ticksCounter++;
                    }
                }
            } else{
                if (Server.playerIsAFK.get(entry.getKey())) {

                    entry.getKey().teleport(Server.playerLastLocation.get(entry.getKey()));
                    entry.getKey().setInvulnerable(false);
                    Server.playerIsAFK.put(entry.getKey(), false);
                    Server.playerLastLocation.remove(entry.getKey());
                    entry.getKey().sendMessage("Leaving AFK");
                }
            }
        }
    }
}



package me.reizora.dev.myplugin.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.reizora.dev.myplugin.Server;

import java.util.HashMap;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        HashMap<String, Object> playerDetail = new HashMap<>();
        playerDetail.put("playerTime", new Long(System.currentTimeMillis()));
        playerDetail.put("playerIsAFK", new Boolean(false));
        playerDetail.put("playerLastLocation", event.getPlayer().getLocation());
        playerDetail.put("playerSpawn", new Location(event.getPlayer().getWorld(), 0, 64, 0));
        Server.playersData.put(event.getPlayer(), playerDetail);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Server.playersData.remove(event.getPlayer());
        Server.playersData.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        HashMap<String, Object> playerDetail = Server.playersData.get(event.getPlayer());
        if (((Boolean) playerDetail.get("playerIsAFK"))) {
            long AFKTime = (System.currentTimeMillis() - (Long) playerDetail.get("playerTime")) / 1000L;
            event.getPlayer().sendMessage("You are AFK for " + AFKTime);
        }
        playerDetail.put("playerTime", new Long(System.currentTimeMillis()));
        if (Server.isLavaFloor) {
            Player player = event.getPlayer();
            Location playerLocation = player.getLocation();
            double xLocation = playerLocation.getX();
            double yLocation = playerLocation.getY();
            double zLocation = playerLocation.getZ();
            World world = event.getPlayer().getWorld();
            Block belowBlock = world.getBlockAt(new Location(world, xLocation, yLocation - 1, zLocation));
            player.sendMessage(belowBlock.getType().toString());
            if (belowBlock.getType() == Material.GRASS_BLOCK) {
                player.setFireTicks(5 * 20);
            }
        }

    }
}

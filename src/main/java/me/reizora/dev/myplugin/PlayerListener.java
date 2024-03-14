package me.reizora.dev.myplugin;

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

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerMonitor.playerTimeList.put(event.getPlayer(), System.currentTimeMillis());
        PlayerMonitor.playerIsAFK.put(event.getPlayer(), false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerMonitor.playerTimeList.remove(event.getPlayer());
        PlayerMonitor.playerIsAFK.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        PlayerMonitor.playerTimeList.put(event.getPlayer(), System.currentTimeMillis());
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        double xLocation = playerLocation.getX();
        double yLocation = playerLocation.getY();
        double zLocation = playerLocation.getZ();
        World world = event.getPlayer().getWorld();
        Block belowBlock = world.getBlockAt(new Location(world, xLocation, yLocation - 1, zLocation));
        player.sendMessage(belowBlock.getType().toString());
        if (belowBlock.getType() == Material.GRASS_BLOCK) {
            player.setFireTicks(100 * 20);
        }

    }
}

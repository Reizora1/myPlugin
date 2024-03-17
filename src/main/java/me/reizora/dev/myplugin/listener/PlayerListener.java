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

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Server.playerTimeList.put(event.getPlayer(), System.currentTimeMillis());
        Server.playerIsAFK.put(event.getPlayer(), false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Server.playerTimeList.remove(event.getPlayer());
        Server.playerIsAFK.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Server.playerIsAFK.get(event.getPlayer())) {
            long AFKTime = (System.currentTimeMillis() - Server.playerTimeList.get(event.getPlayer())) / 1000;
            event.getPlayer().sendMessage("You are AFK for " + AFKTime);
        }
        Server.playerTimeList.put(event.getPlayer(), System.currentTimeMillis());
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

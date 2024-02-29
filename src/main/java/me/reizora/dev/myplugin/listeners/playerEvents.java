package me.reizora.dev.myplugin.listeners;


import me.reizora.dev.myplugin.afkMonitor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class playerEvents implements Listener {
    public static final HashMap<Player, Location> playerJoinLocation = new HashMap<>();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player =  event.getPlayer();
        playerJoinLocation.put(player, player.getLocation());

        player.sendTitle(ChatColor.GREEN+ "WELCOME", ChatColor.GREEN+ "TO THE SERVER!", 50, 20, 10);
        if(player.hasPlayedBefore()){
            event.setJoinMessage(ChatColor.GREEN+ "Welcome back " +player.getName()+ "!");
            //Bukkit.broadcastMessage("WELCOME TO THE SERVER! " +player.getName());
        }
        else{
            System.out.println("A player has joined the server.");
            event.setJoinMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GREEN + " has joined the muthafukin server!");
        }
        afkMonitor.playerJoined(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        afkMonitor.playerLeft(event.getPlayer());
        System.out.println("Player Disconnected....");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Location getLastLocation = event.getPlayer().getLocation();
        afkMonitor.playerMoved(event.getPlayer(), getLastLocation);
        //afkMonitor.lastRecordedMovement.put(event.getPlayer(), System.currentTimeMillis()); // <<<<<<<<<< THIS PIECE OF SHIT RIGHT HERE IF PLACED ABOVE THE playerMoved() WILL UPDATE THE HASHMAP TABLE WITH RECENT VALUE FROM THE currentTimeMillis AND THEREFORE THE EVALUATION OF THE wasAFK VARIABLE IN THE playerMoved() IS ALWAYS FUCKING FALSE.
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player= event.getPlayer();
        player.sendTitle("YOU DIED!", "GIT GUD NOOB!", 10, 40, 10);
        player.sendMessage(ChatColor.GREEN +"YOU RESPAWNED WITH 10 LEVELS TO HELP YOUR NOOB ASS.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        event.setDeathMessage(ChatColor.YELLOW+ "Player " +player.getName()+ " is gay because he died!");
        //event.setNewLevel(10);

        //Bukkit.broadcastMessage(ChatColor.YELLOW+ "Player " +player.getName()+ " is gay because he died!");
        //player.sendMessage(player.getName()+ " has died...");
    }

    @EventHandler
    public void onPlayerLvlUp(PlayerLevelChangeEvent event){
        Player player = event.getPlayer();
        player.sendTitle(ChatColor.GREEN+ "You have leveled up!", "", 5, 10, 5);
        player.sendMessage(ChatColor.GREEN+ "You have leveled up from " +event.getOldLevel()+ " to " +event.getNewLevel());
    }

    @EventHandler
    public void chatDisplay(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        if(message.equalsIgnoreCase("fuck you")){
            event.setMessage("I love you!");
        }
    }
}
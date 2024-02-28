package me.reizora.dev.myplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class objectEvents implements Listener {
    private boolean isBlockBreakable = false;
    private boolean isBlockPlaceable = false;

    public void enableBlockBreak(){
        isBlockBreakable = !isBlockBreakable;
    }

    public void enableBlockPlace(){
        isBlockPlaceable = !isBlockPlaceable;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (isBlockBreakable){
            event.setCancelled(true);
            player.sendMessage("You cannot destroy a block at the moment.");
        }
        else{
            event.setCancelled(false);
            player.sendMessage(ChatColor.RED +"You destroyed a " +block.getType());
            System.out.println("Player " +player.getName()+ " destroyed a block.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();

        if (isBlockPlaceable){
            event.setCancelled(true);
            player.sendMessage("You cannot place a block at the moment.");
        }
        else{
            event.setCancelled(false);
            player.sendMessage(ChatColor.GREEN +"You placed a block " +item.getType());
            System.out.println("Player " +player.getName()+ " has placed the item: " +item.getType());
        }

        if(block.getType() == Material.TNT){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED+ "YOU CANNOT PLACE THAT BLOCK, IT IS TOO DANGEROUS");
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event){
        Player player = event.getPlayer();
        System.out.println("A player named " +event.getPlayer().getName()+ " has filled a bucket.");
        player.sendMessage(player.getName()+ " has filled a bucket");
    }
}

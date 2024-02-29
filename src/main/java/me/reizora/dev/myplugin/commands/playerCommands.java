package me.reizora.dev.myplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class playerCommands implements CommandExecutor {
    private boolean canFly = true;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player player = (Player) sender;
        World world = Bukkit.getWorld("world");

        if (command.getName().equalsIgnoreCase("fly")) { // enable or disable flight on creative mode.
            if (sender != null) {
                canFly = !canFly;
                if (canFly){
                    System.out.println("Flight enabled.");
                    player.sendMessage("Flight enabled.");
                    player.setAllowFlight(canFly);
                }
                else {
                    System.out.println("Flight disabled.");
                    player.sendMessage("Flight disabled.");
                    player.setAllowFlight(canFly);
                }
            }
        }
        else if (command.getName().equalsIgnoreCase("craft")) { // opens the crafting table GUI.
            player.openWorkbench(player.getLocation(), true);
        }
        else if (command.getName().equalsIgnoreCase("survival")) { // Sets gamemode to survival.
            if (player.getGameMode() == GameMode.SURVIVAL){
                player.sendMessage(ChatColor.GREEN+ "Current gamemode is already set to survival.");
            }
            else {
                player.sendMessage(ChatColor.RED+ "SET GAMEMODE TO SURVIVAL.");
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
        else if (command.getName().equalsIgnoreCase("creative")) { // Sets gamemode to creative.
            if (player.getGameMode() == GameMode.CREATIVE){
                player.sendMessage(ChatColor.GREEN+ "Current gamemode is already set to creative.");
            }
            else{
                player.sendMessage(ChatColor.GREEN+ "SET GAMEMODE TO CREATIVE.");
                player.setGameMode(GameMode.CREATIVE);
            }
        }
        else if (command.getName().equalsIgnoreCase("night")) { // Sets absolute time to night.
            world.setTime(13000);
            player.sendMessage("Time set to night.");
        }
        else if (command.getName().equalsIgnoreCase("day")) { // Sets absolute time to day.
            world.setFullTime(1000);
            player.sendMessage("Time set to day.");
        }

        return true;
    }
}
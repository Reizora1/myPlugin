package me.reizora.dev.myplugin.commands;

import me.reizora.dev.myplugin.afkMonitor;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class serverCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Server server = sender.getServer();

        if (command.getName().equalsIgnoreCase("setMOTD")) {
            if (sender.hasPermission("admin")){
                if (args.length == 0){
                    sender.sendMessage(ChatColor.RED+ "Missing command argument!");
                    sender.sendMessage(ChatColor.GRAY+ "/setmotd <motd message here>.");
                }
                else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < args.length; i++){
                        builder.append(args[i]);
                        builder.append(" ");
                    }
                    String finalTxt = builder.toString().stripTrailing();
                    server.setMotd(finalTxt);
                    sender.sendMessage(ChatColor.GREEN+ "Server MOTD updated!");
                }
            }
            else{
                sender.sendMessage(ChatColor.GRAY+ "You have no permission to execute that command!");
            }
        }
        else if (command.getName().equalsIgnoreCase("setSize")){
            if (sender.hasPermission("admin")){
                if(args.length != 1){
                    sender.sendMessage(ChatColor.RED+ "Invalid command argument!");
                    sender.sendMessage(ChatColor.GRAY+ "/setsize <size>.");
                }
                else {
                    int size = Integer.parseInt(args[0]);
                    server.setMaxPlayers(size);
                    sender.sendMessage(ChatColor.GREEN+ "Server capacity updated to " +size);
                }
            }
            else{
                sender.sendMessage(ChatColor.GRAY+ "You have no permission to execute that command!");
            }
        }
        else if (command.getName().equalsIgnoreCase("setTimeout")) {
            if (sender.hasPermission("admin")){
                if (args.length != 1){
                    sender.sendMessage(ChatColor.RED+ "Invalid command argument!");
                    sender.sendMessage(ChatColor.GRAY+ "/settimeout <minute>.");
                }
                else {
                    int timeout = Integer.parseInt(args[0]);
                    server.setIdleTimeout(timeout);
                    sender.sendMessage(ChatColor.GREEN+ "Updated idle timeout.");
                    if (timeout != 0) {
                        sender.sendMessage(ChatColor.GRAY + "To disable idle timeout, pass 0 as the argument.");
                    }
                }
            }
            else{
                sender.sendMessage(ChatColor.GRAY+ "You have no permission to execute that command!");
            }
        }
        else if (command.getName().equalsIgnoreCase("setAFKTimer")){
            if (sender.hasPermission("admin")){
                if (args.length != 1){
                    sender.sendMessage(ChatColor.RED+ "Invalid command argument!");
                    sender.sendMessage(ChatColor.GRAY+ "/setafktimer <seconds>.");
                }
                else {
                    long afkTime = Long.parseLong(args[0]);
                    afkMonitor.defaultAfkTimer = afkTime * 1000L;
                    afkMonitor.newAfkTimer = afkTime;
                    sender.sendMessage(ChatColor.GREEN+ "AFK timer updated!");
                }
            }
            else{
                sender.sendMessage(ChatColor.GRAY+ "You have no permission to execute that command!");
            }
        }
        else if (command.getName().equalsIgnoreCase("getAFKTimer")) {
            sender.sendMessage(ChatColor.GRAY+ "AFK Timer is currently set to " +(afkMonitor.defaultAfkTimer/1000L)+ " seconds.");
        }
        return true;
    }
}
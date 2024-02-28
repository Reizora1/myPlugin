package me.reizora.dev.myplugin.commands;


import me.reizora.dev.myplugin.listeners.objectEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class eventCommands implements CommandExecutor {
    private final objectEvents obj;
    public eventCommands(objectEvents obj){//constructor to accept the obj object

        this.obj = obj;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player p = (Player) sender;

        if (command.getName().equalsIgnoreCase("break")) { //enable or disable block breaking.
            if (sender != null) {
                obj.enableBlockBreak();
                p.sendMessage(ChatColor.GREEN + "You toggled the blockBreak plugin");
            }
        }
        else if (command.getName().equalsIgnoreCase("place")) { // enable or disable block placing.
            if (sender != null) {
                obj.enableBlockPlace();
                p.sendMessage(ChatColor.GREEN + "You toggled the blockPlace plugin");
            }
        }

        return true;
    }
}

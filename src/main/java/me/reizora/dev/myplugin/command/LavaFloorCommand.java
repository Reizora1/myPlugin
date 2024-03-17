package me.reizora.dev.myplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.reizora.dev.myplugin.Server;

public class LavaFloorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Server.isLavaFloor = !Server.isLavaFloor;
            commandSender.sendMessage(String.valueOf(Server.isLavaFloor));
        }
        return true;
    }
}

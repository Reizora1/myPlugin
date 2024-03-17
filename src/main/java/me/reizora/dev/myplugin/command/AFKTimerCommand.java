package me.reizora.dev.myplugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.reizora.dev.myplugin.Server;

public class AFKTimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && isNumeric(strings[0])) {
            Server.afkTimer = Integer.parseInt(strings[0]);
            commandSender.sendMessage("The AFK Timer is set to " + Server.afkTimer);
        }
        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

package me.reizora.dev.myplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Server {
    public static final HashMap<Player, Long> playerTimeList = new HashMap<>();
    public static final HashMap<Player, Location> playerLastLocation = new HashMap<>();
    public static final HashMap<Player, Boolean> playerIsAFK = new HashMap<>();
    public static boolean isLavaFloor = false;
    public static long afkTimer = 10;
    public static int rewardInterval = 2; // in seconds
    public static Material reward = Material.DIAMOND;
}

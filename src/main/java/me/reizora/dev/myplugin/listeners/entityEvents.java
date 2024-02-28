package me.reizora.dev.myplugin.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityResurrectEvent;

import java.text.DecimalFormat;

public class entityEvents implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity player = event.getDamager();
        Entity damagedEntity = event.getEntity();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double dmgVal = event.getDamage();
        String formatVal = decimalFormat.format(dmgVal);
        double formatDmgVal = Double.parseDouble(formatVal);

        player.sendMessage(player.getName()+ " has damaged " +damagedEntity.getName()+ " for " +formatDmgVal);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        LivingEntity deadEntity = event.getEntity();
        Player p = event.getEntity().getKiller();
        event.setDroppedExp(100);

        if(p != null){
            p.sendMessage(p.getName()+ " kills a " +deadEntity.getType());
            System.out.println(p.getName()+ " kills a " +deadEntity.getType());
        }
        else {
            System.out.println("A " +deadEntity.getType()+ " has died somewhere~~");
        }
    }

    @EventHandler
    public void onEntityRes(EntityResurrectEvent event){
        LivingEntity entity = event.getEntity();
        Entity player = event.getEntity();

        entity.setArrowsInBody(15);
        player.sendMessage("The Totem of Undying has been consumed!");
    }
}

package com.darkender.plugins.deathtesting;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class DeathTesting extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler(ignoreCancelled = false)
    public void onPlayerRevive(EntityResurrectEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            Player p = (Player) event.getEntity();
            
            if(event.isCancelled())
            {
                p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
                p.setFoodLevel(20);
                p.setSaturation(0.0F);
                p.setVelocity(new Vector(0, 0, 0));
                
                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(p, null, false);
                        getServer().getPluginManager().callEvent(respawnEvent);
                        p.teleport(respawnEvent.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    }
                }, 1L);
            }
        }
    }
}

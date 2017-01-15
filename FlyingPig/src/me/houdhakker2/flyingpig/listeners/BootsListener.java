package me.houdhakker2.flyingpig.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.houdhakker2.flyingpig.Test;

public class BootsListener implements Listener {
	
	public Test plugin;
	public BootsListener(Test plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PIG){
			Entity pig = e.getEntity();
			if(pig.getCustomName() != null){
				if(pig.getCustomName() == "Flying Pig"){
					e.setCancelled(true);
				}
			}
		}
	}
}

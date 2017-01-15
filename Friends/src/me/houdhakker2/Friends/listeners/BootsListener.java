package me.houdhakker2.Friends.listeners;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.houdhakker2.Friends.Test;

public class BootsListener implements Listener {
	
	public Test plugin;
	public BootsListener(Test plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		String name = e.getPlayer().getName();
		if(!plugin.friends.isSet("friends." + name)){
			plugin.friends.set("friends."+name+".friends" , new ArrayList<String>());
		}
	}
}

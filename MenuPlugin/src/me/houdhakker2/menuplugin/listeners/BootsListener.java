package me.houdhakker2.menuplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.houdhakker2.menuplugin.Test;

public class BootsListener implements Listener {
	
	public Test plugin;
	public BootsListener(Test plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
	}
}

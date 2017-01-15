package me.houdhakker2.Friends.schedulers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.houdhakker2.Friends.Test;

public class pigscanfly extends BukkitRunnable {

	public Test plugin;
	public Player p;
	public Entity pig;
	
	public pigscanfly(Test plugin,Player p ) {
		this.plugin = plugin;
		this.p = p;
	}

	@Override
	public void run() {
	}

}

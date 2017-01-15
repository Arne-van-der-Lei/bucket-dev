package me.houdhakker2.menuplugin.schedulers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.houdhakker2.menuplugin.Test;

public class pigscanfly extends BukkitRunnable {

	public Test plugin;
	public Player p;
	public Entity pig;
	
	public pigscanfly(Test plugin,Player p ,Entity pig) {
		this.plugin = plugin;
		this.p = p;
		this.pig = pig;
	}

	@Override
	public void run() {
		double yaw  = Math.abs(p.getLocation().getYaw())*Math.PI/180;
		double pitch = - p.getLocation().getPitch()*Math.PI/180;
		double x = Math.cos(yaw)*Math.cos(pitch);
		double y = Math.sin(yaw)*Math.cos(pitch);
		double z = Math.sin(pitch);
		pig.setVelocity(new Vector(y,z,x));
		if(pig.getPassenger() == null){
			pig.remove();
			this.cancel();
		}
	}

}

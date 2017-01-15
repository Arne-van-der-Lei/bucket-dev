package me.houdhakker2.detectordoor.schedulers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.houdhakker2.detectordoor.Test;

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
		if(p.getVehicle() == null){
			pig.remove();
			this.cancel();
			return;
		}
		
		double yaw  = -p.getLocation().getYaw()*Math.PI/180;
		double pitch = - p.getLocation().getPitch()*Math.PI/180;
		double x = Math.cos(yaw)*Math.cos(pitch);
		double y = Math.sin(yaw)*Math.cos(pitch);
		double z = Math.sin(pitch);
		pig = p.getVehicle();
		pig.setVelocity(new Vector(y,z,x));
		
	}

}

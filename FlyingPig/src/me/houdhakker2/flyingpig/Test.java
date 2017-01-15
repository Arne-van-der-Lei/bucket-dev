package me.houdhakker2.flyingpig;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.houdhakker2.flyingpig.listeners.BootsListener;
import me.houdhakker2.flyingpig.schedulers.pigscanfly;

// flyingPig
public class Test extends JavaPlugin {

	public Logger logger;
	public PluginDescriptionFile pdfFile;

	public void onEnable() {
		pdfFile = getDescription();
		logger = getLogger();
		
		getServer().getPluginManager().registerEvents(new BootsListener(this), this);
		
		logger.info(pdfFile.getFullName() + " is enabled");

	}

	public void onDisable() {

		logger.info(pdfFile.getFullName() + " is disabled");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("flyingPig")) {

				Entity pig = player.getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
				pig.setCustomName("Flying Pig");
				pig.setPassenger(player);
				
				/*BukkitTask task =*/ new pigscanfly(this,player).runTaskTimer(this, 0, 1);
				player.sendMessage("Your riding a flying pig");

			}
		} else {
			sender.sendMessage("You need to be a player!");
			return false;
		}
		return false;
	}

}

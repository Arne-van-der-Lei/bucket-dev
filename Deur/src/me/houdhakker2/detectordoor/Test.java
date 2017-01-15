package me.houdhakker2.detectordoor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import me.houdhakker2.detectordoor.listeners.BootsListener;

// flyingPig
public class Test extends JavaPlugin {

	public Logger logger;
	public PluginDescriptionFile pdfFile;
	public FileConfiguration config;

	public void onEnable() {
		pdfFile = getDescription();
		logger = getLogger();
		config = getConfig();
		
		Location loc = new Location(getServer().getWorlds().get(0),51,64,251);
		config.addDefault("door.door1.detectorRailLocation", loc);
		config.addDefault("door.door1.doorLocation", new Location(getServer().getWorlds().get(0),53,64,251));
		World world = getServer().getWorlds().get(0);
		List<List> blocks = new ArrayList<List>();
		List<Block> block = new ArrayList<Block>();
		Block blockk = loc.getBlock();
		blockk.setType(Material.WOOD);
		block.add(blockk);
		
		config.addDefault("door.door1.doorBlocks", block);
		config.options().copyDefaults(true);
		
		saveConfig();
		ArmorStand entity = (ArmorStand) world.spawnEntity(new Location(world,53,64,251), EntityType.ARMOR_STAND);
		entity.setVelocity(new Vector(0,0,0));
		entity.setGravity(false);
		entity.setVisible(false);
		entity.setHelmet(new ItemStack(Material.WOOD));
		
		getServer().getPluginManager().registerEvents(new BootsListener(this), this);
		
		logger.info(pdfFile.getFullName() + " is enabled");

	}

	public void onDisable() {

		logger.info(pdfFile.getFullName() + " is disabled");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			//Player player = (Player) sender;
			if (label.equalsIgnoreCase("flyingPig")) {
				
			}
		} else {
			sender.sendMessage("You need to be a player!");
			return false;
		}
		return false;
	}

}

package me.houdhakker2.test;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.houdhakker2.test.listeners.BootsListener;

// Gravity Boots
public class Test extends JavaPlugin {

	public Logger logger;
	public PluginDescriptionFile pdfFile;

	public void onEnable() {
		pdfFile = getDescription();
		logger = getLogger();

		getServer().getPluginManager().registerEvents(new BootsListener(this), this);
		logger.info("Gravity Boots is enabled");

	}

	public void onDisable() {

		logger.info("Gravity Boots is disabled");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("GravityBoots")) {

				ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("Gravity Boots");
				item.setItemMeta(meta);

				player.getInventory().setBoots(item);
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 5));
				player.sendMessage("you receved the Gravity Boots");

			}
		} else {
			sender.sendMessage("You need to be a player!");
			return false;
		}
		return false;
	}

}

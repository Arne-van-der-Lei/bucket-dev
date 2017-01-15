package me.houdhakker2.test.listeners;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.houdhakker2.test.Test;

public class BootsListener implements Listener {
	
	public Test plugin;
	public BootsListener(Test plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventClick(InventoryClickEvent e) {
		ItemStack i = e.getCursor();
		if (e.getSlotType() == SlotType.ARMOR) {
			HumanEntity p = e.getWhoClicked();
			p.removePotionEffect(PotionEffectType.JUMP);
			if (i == null)
                return;
			if(i.getItemMeta() == null)
				return;
			if (i.getType() == Material.DIAMOND_BOOTS && i.getItemMeta().getDisplayName().contains("Gravity Boots")) {
				if(p.hasPermission("Gravity Boots.boots")){
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 5));
				}else{
					p.sendMessage("You dont have premission for the boots to work");
				}
			}
		}
	}
}

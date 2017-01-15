package me.houdhakker2.menuplugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

public class Test extends JavaPlugin implements Listener {
	public static FileConfiguration config;
	public final Logger logger = Logger.getLogger("Minecraft");
	public PluginDescriptionFile pdfFile = getDescription();
	public Yaml yml = new Yaml();
	public Map<String, Inventory> invent;
	public Map<String, String> command;
	public Map<String, List<List<String>>> ani;
	public Map<ItemStack, String> open;
	public Map<Integer, ItemStack> open2;
	public Map<ItemStack, Integer> open3;
	public Map<Location, String> sign;
	public String debug = "";
	public int ln;

	@Override
	public void onEnable() {
		this.logger.info("*--==[ HeavenMC Lobby Menu ]==--*");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();

		this.invent = new HashMap();
		this.command = new HashMap();
		this.ani = new HashMap();
		this.open = new HashMap();
		this.open2 = new HashMap();
		this.open3 = new HashMap();
		this.sign = new HashMap();
		try {
			getMenus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getMenus() throws Exception {
		File path = new File("plugins/HeavenMCLobbyMenu/menu");
		path.mkdirs();

		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				this.debug = files[i].getName();
				InputStream inpu = new FileInputStream(files[i]);
				InputStreamReader isr = new InputStreamReader(inpu);
				BufferedReader input = new BufferedReader(isr);
				Object yamlObj = this.yml.load(input);
				if (yamlObj.getClass() == LinkedHashMap.class) {
					Map obj = (Map) yamlObj;

					Inventory inv = Bukkit.createInventory(null, Integer.parseInt(obj.get("rows").toString()) * 9,
							ChatColor.translateAlternateColorCodes('&',
									ReplaceVariables.replaceVariables(obj.get("name").toString())));
					this.ln = 0;
					for (int i1 = 0; i1 <= inv.getSize(); i1++) {
						this.ln += 1;
						if (obj.get("invent") != null) {
							Map obj2 = (Map) obj.get("invent");

							ItemStack item = new ItemStack(Material.AIR);

							if (obj2.get(Integer.valueOf(i1)) != null) {
								Map obj3 = (Map) obj2.get(Integer.valueOf(i1));

								item.setType(Material.getMaterial(obj3.get("material").toString()));
								item.setAmount(Integer.parseInt(obj3.get("amount").toString()));
								ItemMeta meta = item.getItemMeta();

								if ((item.getType() == Material.LEATHER_BOOTS)
										|| (item.getType() == Material.LEATHER_CHESTPLATE)
										|| (item.getType() == Material.LEATHER_HELMET)
										|| (item.getType() == Material.LEATHER_LEGGINGS)) {
									LeatherArmorMeta metaLether = (LeatherArmorMeta) meta;
									List l = (List) obj3.get("color");
									metaLether.setDisplayName(ChatColor.translateAlternateColorCodes('&',
											ReplaceVariables.replaceVariables(obj3.get("name").toString())));
									List loreo = (List) obj3.get("lore");
									List lore = new ArrayList();
									for (int j = 0; j < loreo.size(); j++) {
										lore.add(ChatColor.translateAlternateColorCodes('&',
												ReplaceVariables.replaceVariables(loreo.get(j).toString())));
									}
									metaLether.setColor(Color.fromRGB(Integer.parseInt(l.get(0).toString()),
											Integer.parseInt(l.get(1).toString()),
											Integer.parseInt(l.get(2).toString())));

									if (obj3.get("enchantment") != null) {
										item.addUnsafeEnchantment(
												Enchantment.getByName(obj3.get("enchantment").toString()), 1);
									}
									metaLether.setLore(lore);
									if (((String) lore.get(0)).endsWith(".yml")) {
										Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
												new runable(files[i].getName(), i1 - 1, (String) lore.get(0)), 1L, 10L);
									}
									item.setDurability(Short.parseShort(obj3.get("durability").toString()));
									item.setItemMeta(metaLether);
								} else {
									meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
											ReplaceVariables.replaceVariables(obj3.get("name").toString())));
									List loreo = (List) obj3.get("lore");
									List lore = new ArrayList();
									for (int j = 0; j < loreo.size(); j++) {
										lore.add(ChatColor.translateAlternateColorCodes('&',
												ReplaceVariables.replaceVariables(loreo.get(j).toString())));
									}
									meta.setLore(lore);
									if (((String) lore.get(0)).endsWith(".yml")) {
										Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
												new runable(files[i].getName(), i1 - 1, (String) lore.get(0)), 10L,
												Long.parseLong(obj3.get("tiks").toString()));
									}
									if (obj3.get("enchantment") != null) {
										item.addUnsafeEnchantment(
												Enchantment.getByName(obj3.get("enchantment").toString()), 1);
									}
									item.setDurability(Short.parseShort(obj3.get("durability").toString()));
									item.setItemMeta(meta);
								}

								String GUIName = ChatColor.translateAlternateColorCodes('&',
										ReplaceVariables.replaceVariables(obj.get("name").toString()));

								this.command.put(GUIName + (i1 - 1), obj3.get("command").toString());
								inv.setItem(i1 - 1, item);
							}
						}
					}
					this.invent.put(files[i].getName(), inv);
				}
			}
		}

		path = new File("plugins/HeavenMCLobbyMenu/ani");
		path.mkdirs();

		files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				InputStream inpu = new FileInputStream(files[i]);
				InputStreamReader isr = new InputStreamReader(inpu);
				BufferedReader input = new BufferedReader(isr);
				Object yamlObj = this.yml.load(input);
				if (yamlObj.getClass() == LinkedHashMap.class) {
					Map obj = (Map) yamlObj;

					List l = new ArrayList();

					for (int k = 0; k < 100; k++) {
						if (obj.get(Integer.valueOf(k)) != null) {
							Map obj2 = (Map) obj.get(Integer.valueOf(k));
							List li = new ArrayList();

							for (int j = 0; j < 100; j++) {
								if (obj2.get(Integer.valueOf(j)) != null) {
									li.add(ChatColor.translateAlternateColorCodes('&', ReplaceVariables
											.replaceVariables(obj2.get(Integer.valueOf(j)).toString())));
								}
							}
							l.add(li);
						}
					}

					this.ani.put(files[i].getName(), l);
				}
			}
		}

		for (int i = 0; i < 200; i++) {
			if (config.get("hotbar." + i) != null) {
				ItemStack item = new ItemStack(
						Material.getMaterial(config.get("hotbar." + i + ".material").toString()));
				ItemMeta meta = item.getItemMeta();

				List list = new ArrayList();
				for (int j = 0; j < config.getStringList("hotbar." + i + ".lore").size(); j++) {
					list.add(ChatColor.translateAlternateColorCodes('&',
							ReplaceVariables.replaceVariables(config.getStringList("hotbar." + i + ".lore").get(j))));
				}
				meta.setLore(list);
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						ReplaceVariables.replaceVariables(config.getString("hotbar." + i + ".name"))));
				item.setDurability((short) config.getInt("hotbar." + i + ".damage"));
				item.setItemMeta(meta);
				if ((config.get("hotbar." + i + ".enchantment").toString() != null)
						&& (!config.get("hotbar." + i + ".enchantment").toString().equals(""))) {
					item.addUnsafeEnchantment(
							Enchantment.getByName(config.get("hotbar." + i + ".enchantment").toString()), 1);
				}
				this.open.put(item, config.getString("hotbar." + i + ".command"));
				this.open2.put(Integer.valueOf(i), item);
				this.open3.put(item, Integer.valueOf(i));
			}
		}

		for (int i = 0; i < 200; i++) {
			if (config.get("Sign." + i) != null) {
				this.sign.put(
						new Location(
								getServer().getWorld(config
										.getStringList(new StringBuilder("Sign.").append(i).append(".loc").toString())
										.get(3)),
								Integer.parseInt(config.getStringList("Sign." + i + ".loc").get(0)),
								Integer.parseInt(config.getStringList("Sign." + i + ".loc").get(1)),
								Integer.parseInt(config.getStringList("Sign." + i + ".loc").get(2))),
						config.getString("Sign." + i + ".command"));
			}
		}
	}

	@Override
	public void onDisable() {
		this.logger.info("*--==[ HeavenMC Lobby Menu ]==--*");
		this.logger.info("[" + this.pdfFile.getName() + "] v" + this.pdfFile.getVersion() + " is now disabled!");
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (this.open3.containsKey(e.getItemDrop().getItemStack())) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't drop this item!"));
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (((e.getAction() == Action.RIGHT_CLICK_BLOCK)
				|| (e.getAction() == Action.RIGHT_CLICK_AIR || (e.getAction() == Action.LEFT_CLICK_AIR))
						&& (this.open.containsKey(e.getItem())))) {
			String commands = this.open.get(e.getItem());
			p.closeInventory();

			String[] s = commands.split("µ");
			for (int i = 0; i < s.length; i++) {
				if (s[i].startsWith("/")) {
					p.performCommand(s[i].replaceFirst("/", "").replace("%PLAYER%", p.getName()));
				} else if (s[i].startsWith(":")) {
					p.openInventory(this.invent.get(s[i].replaceFirst(":", "")));
				} else if (s[i].startsWith("message:")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							ReplaceVariables.replaceVariables(s[i].replaceFirst("message:", ""))));
				} else if (s[i].startsWith("noncommand:")) {
					getServer().dispatchCommand(getServer().getConsoleSender(),
							s[i].replaceFirst("noncommand:", "").replace("%Player%", p.getDisplayName()));
				}
			}
			e.setCancelled(true);
		}

		if (((e.getAction() == Action.LEFT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (this.sign.containsKey(e.getClickedBlock().getLocation()))) {
			String commands = this.sign.get(e.getClickedBlock().getLocation());
			p.closeInventory();

			String[] s = commands.split("µ");
			for (int i = 0; i < s.length; i++) {
				if (s[i].startsWith("/")) {
					p.performCommand(s[i].replaceFirst("/", "").replace("%PLAYER%", p.getName()));
				} else if (s[i].startsWith(":")) {
					p.openInventory(this.invent.get(s[i].replaceFirst(":", "")));
				} else if (s[i].startsWith("message:")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							ReplaceVariables.replaceVariables(s[i].replaceFirst("message:", ""))));
				} else if (s[i].startsWith("switch:")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', s[i].replaceFirst("switch:", "")));
				} else if (s[i].startsWith("noncommand:")) {
					getServer().dispatchCommand(getServer().getConsoleSender(),
							s[i].replaceFirst("noncommand:", "").replace("%Player%", p.getDisplayName()));
				}
			}
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onRightClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String GUIName = ChatColor.translateAlternateColorCodes('&', e.getInventory().getName());

		if ((e.getCurrentItem() != null) && (this.command.containsKey(GUIName + e.getSlot()))) {
			String commands = this.command.get(e.getInventory().getName() + e.getSlot());
			p.closeInventory();

			String[] s = commands.split("µ");
			for (int i = 0; i < s.length; i++) {
				if (s[i].startsWith("/")) {
					p.performCommand(s[i].replaceFirst("/", "").replace("%PLAYER%", p.getName()));
				} else if (s[i].startsWith(":")) {
					p.openInventory(this.invent.get(s[i].replaceFirst(":", "")));
				} else if (s[i].startsWith("message:")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							ReplaceVariables.replaceVariables(s[i].replaceFirst("message:", ""))));
				} else if (s[i].startsWith("switch:")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', s[i].replaceFirst("message:", "")));
				} else if (s[i].startsWith("noncommand:")) {
					getServer().dispatchCommand(getServer().getConsoleSender(),
							s[i].replaceFirst("noncommand:", "").replace("%Player%", p.getDisplayName()));
				}
			}
			e.setCancelled(true);
		} else if (this.open.containsKey(e.getCurrentItem())) {
			String commands = this.open.get(e.getCurrentItem());
			p.closeInventory();

			String[] s = commands.split("µ");
			for (int i = 0; i < s.length; i++) {
				if (s[i].startsWith("/")) {
					p.performCommand(s[i].replaceFirst("/", "").replace("%PLAYER%", p.getName()));
				} else if (s[i].startsWith(":")) {
					p.openInventory(this.invent.get(s[i].replaceFirst(":", "")));
				} else if (s[i].startsWith("message:")) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							ReplaceVariables.replaceVariables(s[i].replaceFirst("message:", ""))));
				} else if (s[i].startsWith("noncommand:")) {
					getServer().dispatchCommand(getServer().getConsoleSender(),
							s[i].replaceFirst("noncommand:", "").replace("%Player%", p.getDisplayName()));
				}
			}
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.getInventory().clear();
		Object[] item = this.open2.values().toArray();
		for (int i = 0; i < item.length; i++) {
			if (item[i].getClass() == ItemStack.class) {
				p.getInventory().setItem(this.open3.get(item[i]).intValue() - 1, (ItemStack) item[i]);
			}
		}
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		Player p = e.getPlayer();
		p.sendMessage(e.getLines());
		if (p.hasPermission("base.one.two")) {
			p.sendMessage(e.getBlock().getLocation().toString());
			if (e.getLine(0).contains("[MENU]")) {
				List list = new ArrayList();
				list.add(e.getBlock().getLocation().getBlockX());
				list.add(e.getBlock().getLocation().getBlockY());
				list.add(e.getBlock().getLocation().getBlockZ());
				list.add(e.getBlock().getLocation().getWorld().getName());
				if (config.get("amountofsigns") == null) {
					config.set("amountofsigns", "1");
				} else {
					config.set("amountofsigns", Integer.valueOf(config.getInt("amountofsigns") + 1));
				}
				config.set("Sign." + config.get("amountofsigns") + ".loc", list);
				config.set("Sign." + config.get("amountofsigns") + ".command", "");
				saveConfig();
				this.sign.put(e.getBlock().getLocation(), "");
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (label.equalsIgnoreCase("menu") || label.equalsIgnoreCase("heavenmclobbymenu")
					|| label.equalsIgnoreCase("lobbymenu")) {
				Player p = (Player) sender;
				if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						if (p.isOp()) {
							onEnable();
							try {
								getMenus();
							} catch (Exception e) {
								sender.sendMessage(e.toString());
							}

							for (String i : this.invent.keySet()) {
								sender.sendMessage(i);
							}

							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lReload complete!"));
						}
					} else if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("open")) {
						p.closeInventory();
						if (args[1].equalsIgnoreCase("joininventory")) {
							onJoinEvent(new PlayerJoinEvent(p, "joininventory"));
						} else {
							if (this.invent.containsKey(args[1] + ".yml")) {
								p.openInventory(this.invent.get(args[1] + ".yml"));
							} else {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&',
										"&c&lError&f: &7This is not a menu!"));
							}
						}
					} else if (args[0].equalsIgnoreCase("openbyplayer")) {
						p.closeInventory();
						if (!args[2].equalsIgnoreCase("") && !args[1].equalsIgnoreCase("")) {
							if (Bukkit.getServer().getPlayer(args[2]) != null
									&& Bukkit.getServer().getPlayer(args[2]).isOnline()) {
								Player targetPlayer = Bukkit.getServer().getPlayer(args[2]);
								if (args[1].equalsIgnoreCase("joininventory")) {
									onJoinEvent(new PlayerJoinEvent(targetPlayer, "joininventory"));
								} else {
									if (this.invent.containsKey(args[1] + ".yml")) {
										targetPlayer.openInventory(this.invent.get(args[1] + ".yml"));
									} else {
										p.sendMessage(ChatColor.translateAlternateColorCodes('&',
												"&c&lError&f: &7This is not a menu!"));
									}
								}
							} else {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&',
										"&c&lError&f: &7This is not a player or the player is not online!"));
							}
						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',
									"&c&lError&f: &7use /HeavenMCmenu &copenbyplayer &6<filename | joininventory> <playername>"));
						}
					} else if (args[0].equalsIgnoreCase("playerhat")) {
						p.closeInventory();
						ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
						SkullMeta meta = (SkullMeta) skull.getItemMeta();
						meta.setOwner(p.getName());
						meta.setDisplayName(ChatColor.GREEN + p.getName() + "'s Head!");
						skull.setItemMeta(meta);

						p.getInventory().addItem(skull);
					}
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &6> &7/HeavenMCmenu &creload"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"  &6> &7/HeavenMCmenu &c<get | open> &6<filename | joininventory>"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"  &6> &7/HeavenMCmenu &copenbyplayer &6<filename | joininventory> <playername>"));
				}
			}
		}
		return false;
	}

	class runable implements Runnable {
		int j;
		String i;
		int k;
		String lore;

		public runable(String i, int j, String lore) {
			this.i = i;
			this.j = j;
			this.lore = lore;
		}

		@Override
		public void run() {
			this.k += 1;
			if (this.k == ((List) Test.this.ani.get(this.lore)).size()) {
				this.k = 0;
			}

			Inventory inv = Test.this.invent.get(this.i);
			ItemStack item = inv.getItem(this.j);
			ItemMeta meta = item.getItemMeta();
			meta.setLore((List) ((List) Test.this.ani.get(this.lore)).get(this.k));
			item.setItemMeta(meta);
			inv.setItem(this.j, item);
			Test.this.invent.put(this.i, inv);
		}
	}
}

class ReplaceVariables {
	public static String replaceVariables(String string) {
		String newString = string;
		
		// -----------------------------------------------------------------------------------------------------------
		while (newString.contains("<ucode")) {
			String code = newString.split("<ucode")[1].split(">")[0];
			newString = newString.replaceAll("<ucode" + code + ">", String.valueOf((char) Integer.parseInt(code, 16)));
		}
		return newString;
	}
}

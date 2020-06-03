package at.haha007.teamcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CE_ItemTool implements CommandExecutor, TabCompleter {

	public CE_ItemTool(JavaPlugin plugin) {
		plugin.getCommand("edititem").setExecutor(this);
		plugin.getCommand("edititem").setTabCompleter(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Dieser Command kann nur von Spielern ausgeführt werden!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length < 2) {
			sendInfo(player);
			return true;
		}

		switch (args[0].toLowerCase()) {
		case "rename": {

			ItemStack item = player.getInventory().getItemInMainHand();
			if (item == null || item.getType() == Material.AIR) {
				player.sendMessage(ChatColor.GOLD + "Du benötigst ein Item in der Hand!");
				return true;
			}

			String name = combineStrings(1, args.length, args);
			name = ChatColor.translateAlternateColorCodes('&', name);

			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			item.setItemMeta(meta);

			player.getInventory().setItemInMainHand(item);
		}
			break;
		case "repaircost": {

			ItemStack item = player.getInventory().getItemInMainHand();
			if (item == null || item.getType() == Material.AIR) {
				player.sendMessage(ChatColor.GOLD + "Du benötigst ein Item in der Hand!");
				return true;
			}
			int lvl;
			try {
				lvl = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.GOLD + args[1] + " ist keine Zahl!");
				return true;
			}

			net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
			nmsItem.getOrCreateTag().setInt("RepairCost", lvl);
			item = CraftItemStack.asCraftMirror(nmsItem);

			player.getInventory().setItemInMainHand(item);
		}
			break;
		case "addlore": {

			ItemStack item = player.getInventory().getItemInMainHand();
			if (item == null || item.getType() == Material.AIR) {
				sender.sendMessage(ChatColor.GOLD + "Du benötigst ein Item in der Hand!");
				return true;
			}

			String string = combineStrings(1, args.length, args);
			string = ChatColor.translateAlternateColorCodes('&', string);

			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null)
				lore = new ArrayList<>();
			lore.add(string);
			meta.setLore(lore);
			item.setItemMeta(meta);

			player.getInventory().setItemInMainHand(item);
		}
			break;
		case "setlore": {

			int index;
			try {
				index = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.GOLD + args[1] + " ist keine Zahl!");
				return true;
			}

			ItemStack item = player.getInventory().getItemInMainHand();
			if (item == null || item.getType() == Material.AIR) {
				sender.sendMessage(ChatColor.GOLD + "Du benötigst ein Item in der Hand!");
				return true;
			}

			String string = combineStrings(2, args.length, args);
			string = ChatColor.translateAlternateColorCodes('&', string);

			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null)
				lore = new ArrayList<>();
			try {
				lore.set(index, string);
			} catch (IndexOutOfBoundsException e) {
				player.sendMessage(ChatColor.GOLD + "Das Item hat keine lore mit dem Index " + index);
				return true;
			}
			meta.setLore(lore);
			item.setItemMeta(meta);

			player.getInventory().setItemInMainHand(item);
		}
			break;
		case "removelore": {

			ItemStack item = player.getInventory().getItemInMainHand();
			if (item == null || item.getType() == Material.AIR) {
				sender.sendMessage(ChatColor.GOLD + "Du benötigst ein Item in der Hand!");
				return true;
			}

			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			if (lore == null || lore.isEmpty()) {
				player.sendMessage(ChatColor.GOLD + "Das Item hat keine Lore.");
				return true;
			}

			int index;
			if (args.length == 1) {
				index = lore.size() - 1;
			} else {
				try {
					index = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.GOLD + args[1] + " ist keine Zahl!");
					return true;
				}
			}

			try {
				lore.remove(index);
			} catch (IndexOutOfBoundsException e) {
				player.sendMessage(ChatColor.GOLD + "Das Item hat keine lore mit dem Index " + index);
				return true;
			}
			meta.setLore(lore);
			item.setItemMeta(meta);

			player.getInventory().setItemInMainHand(item);
		}
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			String arg0 = args[0].toLowerCase();
			if ("rename".startsWith(arg0))
				list.add("rename");
			if ("addlore".startsWith(arg0))
				list.add("addlore");
			if ("setlore".startsWith(arg0))
				list.add("setlore");
			if ("removelore".startsWith(arg0))
				list.add("removelore");
			if ("repaircost".startsWith(arg0))
				list.add("repaircost");
		}
		return list;
	}

	private void sendInfo(Player p) {
		p.sendMessage(ChatColor.GOLD + "/itemtool rename <name>");
		p.sendMessage(ChatColor.GOLD + "/itemtool repaircost <lvl>");
		p.sendMessage(ChatColor.GOLD + "/itemtool addlore <name>");
		p.sendMessage(ChatColor.GOLD + "/itemtool setlore <line> <name>");
		p.sendMessage(ChatColor.GOLD + "/itemtool removelore <line>");
	}

	private String combineStrings(int startIndex, int endIndex, String... strings) {
		String string = "";
		try {
			for (int i = startIndex; i <= endIndex; i++) {
				string += " " + strings[i];
			}
		} catch (IndexOutOfBoundsException e) {
		}

		return string.replaceFirst(" ", "");
	}
}
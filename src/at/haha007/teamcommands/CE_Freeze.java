package at.haha007.teamcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CE_Freeze implements CommandExecutor, Listener {
	private boolean active = false;

	public CE_Freeze(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getCommand("freeze").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		active = !active;

		if (active) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Alle Blockänderungen sind nun blockiert.");
		} else {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Blockänderungen sind nun nicht mehr blockiert.");
		}
		return true;
	}

	@EventHandler
	void onBlockBreak(BlockBreakEvent event) {
		if (!active)
			return;
		if (event.getPlayer().hasPermission("teamcommands.freeze.bypass"))
			return;
		event.setCancelled(true);
	}

	@EventHandler
	void onBlockPlace(BlockPlaceEvent event) {
		if (!active)
			return;
		if (event.getPlayer().hasPermission("eden.edenbugfixes.freeze.bypass"))
			return;
		event.setCancelled(true);
	}
}

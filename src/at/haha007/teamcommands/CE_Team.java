package at.haha007.teamcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CE_Team implements CommandExecutor {
	public CE_Team(JavaPlugin plugin) {
		plugin.getCommand("teamchat").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.GOLD + "/team message");
			return true;
		}

		String name = ChatColor.DARK_RED + sender.getName() + ChatColor.WHITE;
		if (sender instanceof Player)
			name = ((Player) sender).getDisplayName();

		String message = combineStrings(0, args.length - 1, args);
		Bukkit.broadcast(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "TeamChat" + ChatColor.DARK_PURPLE
				+ "] " + name + " " + message, "teamcommands.teamchat");
		return true;
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

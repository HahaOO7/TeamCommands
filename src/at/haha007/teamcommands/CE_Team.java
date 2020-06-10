package at.haha007.teamcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CE_Team implements CommandExecutor {
	public CE_Team(JavaPlugin plugin) {
		plugin.getCommand("teamchat").setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.GOLD + "/team message");
			return true;
		}

		String name = ChatColor.DARK_RED + sender.getName() + ChatColor.WHITE;
		if (sender instanceof Player)
			name = ((Player) sender).getDisplayName();

		String message = combineStrings(args);
		Bukkit.broadcast(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "TeamChat" + ChatColor.DARK_PURPLE
			+ "] " + name + " " + message, "teamcommands.teamchat");
		return true;
	}

	private String combineStrings(String... strings) {
		StringBuilder string = new StringBuilder();
		try {
			for (int i = 0; i <= string.length() - 1; i++) {
				string.append(" ").append(strings[i]);
			}
		} catch (IndexOutOfBoundsException ignored) {
		}

		return string.toString().replaceFirst(" ", "");
	}
}

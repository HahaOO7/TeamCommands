package at.haha007.teamcommands;

import org.bukkit.plugin.java.JavaPlugin;

public class TeamCommands extends JavaPlugin {
	int eid = 0;

	@Override
	public void onEnable() {
		new CE_Freeze(this);
		new CE_ItemTool(this);
		new CE_Team(this);
	}
}

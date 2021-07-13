package us.mcfriendly.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import us.mcfriendly.core.events.JoinEvent;
import us.mcfriendly.core.features.chatcolor.ChatColorEvent;
import us.mcfriendly.core.features.chatcolor.ChatColorPlaceholder;
import us.mcfriendly.core.features.checkup.CheckupCommand;
import us.mcfriendly.core.features.customtags.CustomTagCommand;
import us.mcfriendly.core.features.playerwarps.PlayerWarpCommand;
import us.mcfriendly.core.features.playerwarps.PlayerWarpEvent;
import us.mcfriendly.core.features.rounds.RoundsCommand;
import us.mcfriendly.core.features.rounds.RoundsEvent;
import us.mcfriendly.core.features.staffchat.StaffChatCommand;
import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.SQLManager;

public class Main extends JavaPlugin {

	private static Economy econ = null;

	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getLogger().severe(
					String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		DataManager.setup(this);

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new ChatColorPlaceholder().register();
		}
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			SQLManager.initializeNewPlayer(all);
		}

		register();
		System.out.println("§eINFO: §aPlugin Enabled.");
	}

	private void register() {
		PluginManager pm = Bukkit.getPluginManager();

		// Main Events
		pm.registerEvents(new JoinEvent(), this);

		// Feature Events
		pm.registerEvents(new RoundsEvent(), this);
		pm.registerEvents(new PlayerWarpEvent(), this);
		pm.registerEvents(new ChatColorEvent(), this);

		// Commands
		getCommand("rounds").setExecutor(new RoundsCommand());
		getCommand("playerwarps").setExecutor(new PlayerWarpCommand());
		getCommand("sc").setExecutor(new StaffChatCommand());
		getCommand("createtag").setExecutor(new CustomTagCommand());
		getCommand("checkup").setExecutor(new CheckupCommand());
//		getCommand("chatcolor").setExecutor(new ChatColorCommand());
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Economy getEconomy() {
		return econ;
	}
}
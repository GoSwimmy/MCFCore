package us.mcfriendly.core.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Util {

	public static String getMessage(String path) {
		FileConfiguration c = DataManager.config;
		return prefix() + color(c.getString(path));
	}

	public static String prefix() {
		FileConfiguration c = DataManager.config;
		return color(c.getString("options.prefix"));
	}

	public static String color(String raw) {
		return ChatColor.translateAlternateColorCodes('&', raw);
	}

	public static String buildString(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i] + " ");
		}
		return sb.toString().trim();
	}

}

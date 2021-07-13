package us.mcfriendly.core.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class DataManager {

	public static FileConfiguration config;
	public static File configFile;

	public static FileConfiguration portals;
	public static File portalsFile;

	public static void setup(Plugin pl) {
		configFile = new File(pl.getDataFolder(), "config.yml");
		config = pl.getConfig();
		config.options().copyDefaults(false);
		if (!pl.getDataFolder().exists()) {
			pl.getDataFolder().mkdir();
		}
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pl.saveResource("config.yml", true);
		}
		config = YamlConfiguration.loadConfiguration(configFile);
		
		portalsFile = new File(pl.getDataFolder(), "portals.yml");
		portals = pl.getConfig();
		portals.options().copyDefaults(false);
		if (!portalsFile.exists()) {
			try {
				portalsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		portals = YamlConfiguration.loadConfiguration(portalsFile);
	}
	
	public static void save() {
		try {
			portals.save(portalsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

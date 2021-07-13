package us.mcfriendly.core.features.staffchat;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.Util;

public class StaffChatManager {

	public static HashMap<UUID, Boolean> enabled = new HashMap<UUID, Boolean>();

	public static void sendStaffMessage(Player p, String rawstring) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.hasPermission(DataManager.config.getString("options.staffchat.permission"))) {
				all.sendMessage(Util.color(DataManager.config.getString("options.staffchat.format")
						.replace("%username%", p.getName()).replace("%message%", rawstring)));
			}
		}
	}
}

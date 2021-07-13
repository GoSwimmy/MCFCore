package us.mcfriendly.core.features.chatcolor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import us.mcfriendly.core.managers.SQLManager;

public class ChatColorPlaceholder extends PlaceholderExpansion {

	public String getIdentifier() {
		return "mcfchat";
	}

	public String getPlugin() {
		return null;
	}

	public String getAuthor() {
		return "GoSwimmy";
	}

	public String getVersion() {
		return "1.0";
	}

	public String onPlaceholderRequest(Player player, String identifier) {
		if (player == null) {
			return "";
		}
		if (identifier.startsWith("test_")) {

		}
		if (identifier.equalsIgnoreCase("color")) {
			if (SQLManager.connect()) {
				if (SQLManager.isConnected()) {
					Connection connection = SQLManager.getConnection();
					try {
						String sql = "SELECT * FROM `chat_color` WHERE uuid = ?";
						PreparedStatement stmt = connection.prepareStatement(sql);
						stmt.setString(1, player.getUniqueId().toString());
						ResultSet res = stmt.executeQuery();
						if (res.next()) {
							if (res.getString("color") == null) {
								return "&f";
							} else {
								return res.getString("color");
							}
						} else {
							return "";
						}
					} catch (Exception e) {
						e.printStackTrace();
						return "";
					}
				} else {
					return "";
				}
			} else {
				return "";
			}
		}
		return null;
	}
}
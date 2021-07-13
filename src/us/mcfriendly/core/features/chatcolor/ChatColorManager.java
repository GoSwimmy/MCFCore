package us.mcfriendly.core.features.chatcolor;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.SQLManager;
import us.mcfriendly.core.managers.Util;

public class ChatColorManager {

	public static void setColor(Player p, HeadColors color) {
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "UPDATE `chat_color` SET color = ? WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, color.getColor());
					stmt.setString(2, p.getUniqueId().toString());
					stmt.executeUpdate();
					p.sendMessage(Util.color(Util.prefix() + "&aChatColor changed to "+color.getColor()+color.getName()+"&a!"));
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		p.sendMessage(Util.color(Util.prefix() + "&cAn error occurred"));
	}

}

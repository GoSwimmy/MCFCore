package us.mcfriendly.core.features.rounds;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.SQLManager;

public class RoundsManager {

	// Hour in milliseconds
	public static long roundchecktimes = 36000000;

	public static void resetPlayer(String puuid, Player mod) {
		long newtime = roundchecktimes + System.currentTimeMillis();
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "UPDATE rounds_data SET next_check = ?, last_checker = ? WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, newtime + "");
					stmt.setString(2, mod.getUniqueId().toString());
					stmt.setString(3, puuid);
					stmt.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

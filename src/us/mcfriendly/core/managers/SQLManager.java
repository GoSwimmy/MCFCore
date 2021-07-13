package us.mcfriendly.core.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SQLManager {

	public static Connection con;

	// connect
	public static boolean connect() {
		FileConfiguration c = DataManager.config;
		String host = c.getString("mysql.host");
		String port = c.getString("mysql.port");
		String database = c.getString("mysql.database");
		String username = c.getString("mysql.username");
		String password = c.getString("mysql.password");
		String ssl = c.getString("mysql.ssl");
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=" + ssl,
						username, password);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	// disconnect
	public static void disconnect() {
		if (isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// isConnected
	public static boolean isConnected() {
		return (con == null ? false : true);
	}

	// getConnection
	public static Connection getConnection() {
		return con;
	}

	public static void initialize() {
		System.out.println("§eINFO: §eConnecting to Main Database...");
		if (connect()) {
			System.out.println("§eINFO: §aConnected to Main Database.");
		} else {
			System.out.println("§cERROR: §cCannot Connect to Main Database!");
		}
		if (isConnected()) {
			Connection connection = getConnection();
			try {
				String createreporttable = "CREATE TABLE IF NOT EXISTS `reports` (`id` INT(255) NOT NULL AUTO_INCREMENT, `reason` VARCHAR(255), `uid` VARCHAR(255), `reporter` VARCHAR(255), `reported` VARCHAR(255), `staff` VARCHAR(255), `status` VARCHAR(255), `created_at` BIGINT(25) unsigned, PRIMARY KEY (`id`));";
				PreparedStatement createreporttablestmt = connection.prepareStatement(createreporttable);
				createreporttablestmt.executeUpdate();
				System.out.println("§eINFO: §aRan CREATE TABLE IF NOT EXISTS reports.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("§cERROR: §cCannot Create reports Table.");
			}
			try {
				String createreport_commentstable = "CREATE TABLE IF NOT EXISTS `report_comments` (`id` INT(255) NOT NULL AUTO_INCREMENT,  `report_uid` VARCHAR(255), `commentor` VARCHAR(255), `created_at` bigint(25) unsigned, `content` VARCHAR(255), PRIMARY KEY (`id`));";
				PreparedStatement createreport_commentstablestmt = connection
						.prepareStatement(createreport_commentstable);
				createreport_commentstablestmt.executeUpdate();
				System.out.println("§eINFO: §aRan CREATE TABLE IF NOT EXISTS report_comments.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("§cERROR: §cCannot Create report_comments Table.");
			}
			try {
				String roundstable = "CREATE TABLE IF NOT EXISTS `rounds_data` (`id` INT(255) NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(255), `next_check` VARCHAR(255), `last_checker` VARCHAR(255), PRIMARY KEY (`id`));";
				PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
				roundstablestmt.executeUpdate();
				System.out.println("§eINFO: §aRan CREATE TABLE IF NOT EXISTS rounds_data.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("§cERROR: §cCannot Create rounds_data Table.");
			}
			try {
				String createreport_commentstable = "CREATE TABLE IF NOT EXISTS `player_warps` (`id` INT(255) NOT NULL AUTO_INCREMENT, `icon` VARCHAR(255), `owner_uuid` VARCHAR(255), `uuid` VARCHAR(255), `location` VARCHAR(255), `name` VARCHAR(255), `lore` VARCHAR(255), PRIMARY KEY (`id`));";
				PreparedStatement createreport_commentstablestmt = connection
						.prepareStatement(createreport_commentstable);
				createreport_commentstablestmt.executeUpdate();
				System.out.println("§eINFO: §aRan CREATE TABLE IF NOT EXISTS player_warps.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("§cERROR: §cCannot Create player_warps Table.");
			}
			for (Player all : Bukkit.getOnlinePlayers()) {
				initializeNewPlayer(all);
			}
		}
	}

	public static void initializeNewPlayer(Player p) {
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM `chat_color` WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (!res.next()) {
						try {
							String roundstable = "INSERT INTO `chat_color` (uuid) VALUES (?)";
							PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
							roundstablestmt.setString(1, p.getUniqueId().toString());
							roundstablestmt.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
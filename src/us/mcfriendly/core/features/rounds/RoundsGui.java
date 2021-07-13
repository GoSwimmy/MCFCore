package us.mcfriendly.core.features.rounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import us.mcfriendly.core.managers.SQLManager;
import us.mcfriendly.core.managers.Util;

@SuppressWarnings("deprecation")
public class RoundsGui {

	public static void roundsGui(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, "§6§lRounds");
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM rounds_data LIMIT 54";
					PreparedStatement stmt = connection.prepareStatement(sql);
					ResultSet res = stmt.executeQuery();
					while (res.next()) {
						Player t = Bukkit.getPlayer(UUID.fromString(res.getString("uuid")));
						if (t != null) {
							long next_check = Long.parseLong(res.getString("next_check"));
							if (next_check <= System.currentTimeMillis()) {
								String last_checker = "N/A";
								if (res.getString("last_checker").length() >= 8) {
									OfflinePlayer ot = Bukkit
											.getOfflinePlayer(UUID.fromString(res.getString("last_checker")));
									last_checker = ot.getName();
								}
								ItemStack i = new ItemStack(Material.PLAYER_HEAD, 1,
										(short) SkullType.PLAYER.ordinal());
								SkullMeta m = (SkullMeta) i.getItemMeta();
								m.setOwningPlayer(t);
								m.setDisplayName("§6§l" + t.getName());
								m.setLore(Arrays.asList("§6UUID: §f" + t.getUniqueId(), " ",
										"§6Last Checker: §f" + last_checker));
								i.setItemMeta(m);
								inv.addItem(i);
							}
						}
					}
					p.openInventory(inv);
				} catch (Exception e) {
					e.printStackTrace();
					p.sendMessage(Util.prefix() + "§cDatabase error occurred, please try again later.");
				}
			} else {
				p.sendMessage(Util.prefix() + "§cDatabase error occurred, please try again later.");
			}
		} else {
			p.sendMessage(Util.prefix() + "§cDatabase error occurred, please try again later.");
		}
	}
	
}

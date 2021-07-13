package us.mcfriendly.core.features.playerwarps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import us.mcfriendly.core.managers.SQLManager;
import us.mcfriendly.core.managers.Util;

public class PlayerWarpGui {

	@SuppressWarnings("deprecation")
	public static void openGui(Player p, int page) {
		Inventory inv = Bukkit.createInventory(null, 54, "§6§lPlayer Warps | Page " + page);

		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					int currentpos = 0;
					
					ItemStack buyprem = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta buypremm = (SkullMeta) buyprem.getItemMeta();
					buypremm.setOwner("MHF_QUESTION");
					buypremm.setDisplayName(Util.color("&6&lPremium Player Warp Spot"));
					buypremm.setLore(Arrays.asList("", Util.color("&e&oTo purchase this spot,"), Util.color("&e&otype /pwarp premium!")));
					buyprem.setItemMeta(buypremm);
					for (int i = 0; i < 9; i++) {
						inv.setItem(i, buyprem);
					}
					
					String premiumsql = "SELECT * FROM player_warps WHERE active = ?";
					PreparedStatement premiumstmt = connection.prepareStatement(premiumsql);
					premiumstmt.setInt(1, 1);
					ResultSet premiumrs = premiumstmt.executeQuery();
					while (premiumrs.next()) {
						if (premiumrs.getFloat("endtimestamp") < System.currentTimeMillis()) {
							String roundstable = "UPDATE `player_warps` SET active = ? WHERE uuid = ?";
							PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
							roundstablestmt.setInt(1, 0);
							roundstablestmt.setString(2, premiumrs.getString("uuid"));
							roundstablestmt.executeUpdate();
						} else {
							ItemStack i = new ItemStack(Material.valueOf(premiumrs.getString("icon")));
							ItemMeta m = i.getItemMeta();
							m.setDisplayName(Util.color(premiumrs.getString("name")));
							List<String> lore = new ArrayList<String>();
							lore.add(Util.color("&6Owner: &f" + Bukkit
									.getOfflinePlayer(UUID.fromString(premiumrs.getString("owner_uuid"))).getName()));
							lore.add(Util.color("&6UUID: &f" + premiumrs.getString("uuid")));
							lore.add(Util.color("&f—————————————"));
							for (String rawlore : premiumrs.getString("lore").split("/n")) {
								lore.add(Util.color("&f" + rawlore));
							}
							m.setLore(lore);
							i.setItemMeta(m);
							if(currentpos < 9) {
								inv.setItem(currentpos, i);
							}
							currentpos++;
						}
					}

					ItemStack spacer = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
					ItemMeta spacerm = spacer.getItemMeta();
					spacerm.setDisplayName(Util.color("&l"));
					spacer.setItemMeta(spacerm);
					for (int i = 9; i < 18; i++) {
						inv.setItem(i, spacer);
					}

					String sql = "SELECT * FROM player_warps WHERE active = ? LIMIT 27";
					if (page > 1) {
						sql += " OFFSET " + ((page - 1) * 27);
					}
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setInt(1, 0);
					ResultSet res = stmt.executeQuery();
					while (res.next()) {
						ItemStack i = new ItemStack(Material.valueOf(res.getString("icon")));
						ItemMeta m = i.getItemMeta();
						m.setDisplayName(Util.color(res.getString("name")));
						List<String> lore = new ArrayList<String>();
						lore.add(Util.color("&6Owner: &f"
								+ Bukkit.getOfflinePlayer(UUID.fromString(res.getString("owner_uuid"))).getName()));
						lore.add(Util.color("&6UUID: &f" + res.getString("uuid")));
						lore.add(Util.color("&f—————————————"));
						for (String rawlore : res.getString("lore").split("/n")) {
							lore.add(Util.color("&f" + rawlore));
						}
						m.setLore(lore);
						i.setItemMeta(m);
						inv.addItem(i);
					}

					ItemStack next = new ItemStack(Material.BLACK_BANNER);
					ItemMeta nextm = next.getItemMeta();
					nextm.setDisplayName("§6§lNext Page");
					next.setItemMeta(nextm);
					if (inv.getItem(44) != null) {
						inv.setItem(53, next);
					}

					ItemStack refresh = new ItemStack(Material.SUNFLOWER);
					ItemMeta refreshm = refresh.getItemMeta();
					refreshm.setDisplayName("§6§lRefresh");
					refresh.setItemMeta(refreshm);
					inv.setItem(49, refresh);

					ItemStack previous = new ItemStack(Material.WHITE_BANNER);
					ItemMeta previousm = previous.getItemMeta();
					previousm.setDisplayName("§6§lPrevious Page");
					previous.setItemMeta(previousm);
					if (page >= 2) {
						inv.setItem(45, previous);
					}

					p.openInventory(inv);
				} catch (Exception e) {
					e.printStackTrace();
					p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
				}
			} else {
				p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
			}
		} else {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
		}
	}
	
	public static void openPremiumGui(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "§6§lPremium Confirmation");
		
		ItemStack spacer = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		ItemMeta spacerm = spacer.getItemMeta();
		spacerm.setDisplayName(Util.color("&l"));
		spacer.setItemMeta(spacerm);
		for(int i = 0; i < 27; i++) {
			inv.setItem(i, spacer);
		}
		
		ItemStack info = new ItemStack(Material.ACACIA_SIGN);
		ItemMeta infom = info.getItemMeta();
		infom.setDisplayName(Util.color("§6§lPremium Player Warp"));
		infom.setLore(Arrays.asList("", Util.color("&e&oTo purchase a Premium Player Warp spot,"), Util.color("&e&oClick the &a&lGREEN &e&obutton."), "", Util.color("&c&lWarning: Cost $50,000 (NO REFUND)")));
		info.setItemMeta(infom);
		inv.setItem(13, info);
		
		ItemStack confirm = new ItemStack(Material.GREEN_CONCRETE);
		ItemMeta confirmm = confirm.getItemMeta();
		confirmm.setDisplayName(Util.color("&a&lCONFIRM"));
		confirm.setItemMeta(confirmm);
		inv.setItem(11, confirm);
		
		ItemStack decline = new ItemStack(Material.RED_CONCRETE);
		ItemMeta declinem = decline.getItemMeta();
		declinem.setDisplayName(Util.color("&c&lDECLINE"));
		decline.setItemMeta(declinem);
		inv.setItem(15, decline);
		
		p.openInventory(inv);
	}

}

package us.mcfriendly.core.features.playerwarps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import us.mcfriendly.core.Main;
import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.SQLManager;
import us.mcfriendly.core.managers.Util;

public class PlayerWarpManager {

	public static void delete(Player p) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						try {
							String roundstable = "DELETE FROM `player_warps` WHERE uuid = ?";
							PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
							roundstablestmt.setString(1, res.getString("uuid"));
							roundstablestmt.executeUpdate();
							p.sendMessage(Util.getMessage("options.playerwarps.messages.deletewarp")
									.replace("%pwarpname%", Util.color("&f" + res.getString("name"))));
						} catch (Exception e) {
							e.printStackTrace();
							p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
						}
					}
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

	public static void getPremium(Player p) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						try {
							if(res.getInt("active") == 1) {
								p.sendMessage(Util.color(Util.prefix() + "&cYou already have Premium!"));
								return;
							}
							if (Main.getEconomy().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) < 50000) {
								p.sendMessage(Util.color(Util.prefix() + "&cYou do not have enough money for Premium! &e&o($50,000)"));
								return;
							}
							Main.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), 50000);
							String roundstable = "UPDATE `player_warps` SET active = ?, endtimestamp = ? WHERE uuid = ?";
							PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
							roundstablestmt.setInt(1, 1);
							roundstablestmt.setFloat(2, System.currentTimeMillis() + 86400000);
							roundstablestmt.setString(3, res.getString("uuid"));
							roundstablestmt.executeUpdate();
							p.sendMessage(Util.color(Util.prefix() + "&aYou have purchased Player Warp Premium for 1 Day!"));
						} catch (Exception e) {
							e.printStackTrace();
							p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
						}
					}
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

	@SuppressWarnings("deprecation")
	public static void setIcon(Player p) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						try {
							if (p.getItemInHand().getType() != Material.AIR && p.getItemInHand().getType() != null) {
								String icon = p.getItemInHand().getType().name();
								String roundstable = "UPDATE `player_warps` SET icon = ? WHERE uuid = ?";
								PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
								roundstablestmt.setString(1, icon);
								roundstablestmt.setString(2, res.getString("uuid"));
								roundstablestmt.executeUpdate();
								p.sendMessage(Util.getMessage("options.playerwarps.messages.iconchange")
										.replace("%pwarpname%", Util.color("&f" + res.getString("name")))
										.replace("%newiconname%", Util.color("&f" + icon)));
							} else {
								p.sendMessage(Util.getMessage("options.playerwarps.messages.noiteminhandforicon"));
							}
						} catch (Exception e) {
							e.printStackTrace();
							p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
						}
					}
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

	public static void relocate(Player p) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						try {
							if (Main.getEconomy().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= 5000) {
								Location loc = p.getLocation();
								String location = loc.getWorld().getName() + ";" + loc.getBlockX() + ";"
										+ loc.getBlockY() + ";" + loc.getBlockZ() + ";" + loc.getYaw() + ";"
										+ loc.getPitch();
								String roundstable = "UPDATE `player_warps` SET location = ? WHERE uuid = ?";
								PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
								roundstablestmt.setString(1, location);
								roundstablestmt.setString(2, res.getString("uuid"));
								roundstablestmt.executeUpdate();
								Main.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), 5000);
								p.sendMessage(Util.getMessage("options.playerwarps.messages.relocated")
										.replace("%pwarpname%", Util.color("&f" + res.getString("name"))));
							} else {
								p.sendMessage(Util.getMessage("options.playerwarps.messages.relocatednomoney"));
							}
						} catch (Exception e) {
							e.printStackTrace();
							p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
						}
					} else {
						p.sendMessage(Util.getMessage("options.playerwarps.messages.nowarp"));
					}
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

	@SuppressWarnings("deprecation")
	public static void create(Player p, String rawname) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (Main.getEconomy().getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) < Integer
				.parseInt(DataManager.config.getString("options.playerwarps.create-price"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nobalance"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						p.sendMessage(Util.getMessage("options.playerwarps.messages.alreadyhavepwarp"));
					} else {
						if (ChatColor.translateAlternateColorCodes('&', rawname).length() > 24) {
							p.sendMessage(Util.getMessage("options.playerwarps.messages.stringtoolong"));
						} else {
							if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', rawname))
									.length() == 0) {
								p.sendMessage(Util.getMessage("options.playerwarps.messages.stringtoolong"));
								return;
							}
							try {
								String icon = "STONE";
								if (p.getItemInHand().getType() != Material.AIR
										&& p.getItemInHand().getType() != null) {
									icon = p.getItemInHand().getType().name();
								}
								Location loc = p.getLocation();
								String location = loc.getWorld().getName() + ";" + loc.getBlockX() + ";"
										+ loc.getBlockY() + ";" + loc.getBlockZ() + ";" + loc.getYaw() + ";"
										+ loc.getPitch();
								String roundstable = "INSERT IGNORE INTO `player_warps` (owner_uuid, uuid, location, name, lore, icon) VALUES (?, ?, ?, ?, ?, ?)";
								PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
								roundstablestmt.setString(1, p.getUniqueId().toString());
								roundstablestmt.setString(2, UUID.randomUUID().toString().split("-")[0]);
								roundstablestmt.setString(3, location);
								roundstablestmt.setString(4, rawname);
								roundstablestmt.setString(5, "&fLine 1/n&fLine 2/n&fLine 3");
								roundstablestmt.setString(6, icon);
								roundstablestmt.executeUpdate();
								p.sendMessage(Util.getMessage("options.playerwarps.messages.createwarp")
										.replace("%pwarpname%", Util.color("&f" + rawname)));
								Main.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), Integer
										.parseInt(DataManager.config.getString("options.playerwarps.create-price")));
							} catch (Exception e) {
								e.printStackTrace();
								p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
							}
						}
					}
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

	public static void rename(Player p, String rawname) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						if (ChatColor.translateAlternateColorCodes('&', rawname).length() > 24) {
							p.sendMessage(Util.getMessage("options.playerwarps.messages.stringtoolong"));
						} else {
							if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', rawname))
									.length() == 0) {
								p.sendMessage(Util.getMessage("options.playerwarps.messages.stringtoolong"));
								return;
							}
							try {
								String roundstable = "UPDATE `player_warps` SET name = ? WHERE uuid = ?";
								PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
								roundstablestmt.setString(1, rawname);
								roundstablestmt.setString(2, res.getString("uuid"));
								roundstablestmt.executeUpdate();
								p.sendMessage(Util.getMessage("options.playerwarps.messages.renamewarp")
										.replace("%pwarpname%", Util.color("&f" + res.getString("name")))
										.replace("%newwarpname%", Util.color("&f" + rawname)));
							} catch (Exception e) {
								e.printStackTrace();
								p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
							}
						}
					} else {
						p.sendMessage(Util.getMessage("options.playerwarps.messages.warpdoesnotexist"));
					}
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

	public static void adminDelete(Player p, String rawquery) {

	}

	public static void setLore(Player p, String rawline, String rawtext) {
		if (!p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
			p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			return;
		}
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE owner_uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						try {
							int line = Integer.parseInt(rawline);
							if (line < 1 || line > 3) {
								p.sendMessage(Util.getMessage("options.playerwarps.messages.outofbounds"));
							} else {
								if (ChatColor.translateAlternateColorCodes('&', rawtext).length() > 24) {
									p.sendMessage(Util.getMessage("options.playerwarps.messages.stringtoolong"));
								} else {
									String[] lore = res.getString("lore").split("/n");
									String yesdaddy = "";
									for (int i = 0; i < 3; i++) {
										if (i + 1 == line) {
											if (i == 2) {
												yesdaddy += rawtext;
											} else {
												yesdaddy += rawtext + "/n";
											}
										} else {
											if (i == 2) {
												yesdaddy += lore[i];
											} else {
												yesdaddy += lore[i] + "/n";
											}
										}
									}
									try {
										String roundstable = "UPDATE `player_warps` SET lore = ? WHERE uuid = ?";
										PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
										roundstablestmt.setString(1, yesdaddy);
										roundstablestmt.setString(2, res.getString("uuid"));
										roundstablestmt.executeUpdate();
										p.sendMessage(Util.getMessage("options.playerwarps.messages.lorechange")
												.replace("%pwarpname%", Util.color("&f" + res.getString("name")))
												.replace("%loreline%", Util.color("&f" + line))
												.replace("%newlore%", Util.color("&f" + rawtext)));
									} catch (Exception e) {
										e.printStackTrace();
										p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
									}
								}
							}
						} catch (Exception e) {
							p.sendMessage(Util.getMessage("options.playerwarps.messages.databaseerr"));
						}
					} else {
						p.sendMessage(Util.getMessage("options.playerwarps.messages.warpdoesnotexist"));
					}
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

	public static void warp(Player p, String rawuuid) {
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM player_warps WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, rawuuid);
					ResultSet res = stmt.executeQuery();
					if (res.next()) {
						String[] rawloc = res.getString("location").split(";");
						Location loc = new Location(Bukkit.getWorld(rawloc[0]), Double.parseDouble(rawloc[1]),
								Double.parseDouble(rawloc[2]), Double.parseDouble(rawloc[3]),
								Float.parseFloat(rawloc[4]), Float.parseFloat(rawloc[5]));
						p.teleport(loc);
						p.sendMessage(Util.getMessage("options.playerwarps.messages.teleported").replace("%pwarpname%",
								Util.color("&f" + res.getString("name"))));
					} else {
						p.sendMessage(Util.getMessage("options.playerwarps.messages.warpdoesnotexist"));
					}
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

}

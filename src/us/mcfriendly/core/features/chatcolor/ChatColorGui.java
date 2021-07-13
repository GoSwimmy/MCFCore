package us.mcfriendly.core.features.chatcolor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import us.mcfriendly.core.managers.SQLManager;
import us.mcfriendly.core.managers.Util;

public class ChatColorGui {

	public static void openGui(Player p) {

		String currentcolor = "";

		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM `chat_color` WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (!res.next()) {
						currentcolor = "&f";
					} else {
						if (res.getString("color") == null) {
							currentcolor = "&f";
						} else {
							currentcolor = res.getString("color");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					currentcolor = "&f";
				}
			} else {
				currentcolor = "&f";
			}
		} else {
			currentcolor = "&f";
		}

		Inventory inv = Bukkit.createInventory(null, 54,
				"§6§lChatColor | §f" + Util.color(currentcolor + "MCFriendly"));

		inv.setItem(10, getItem(p, HeadColors.DARK_RED, currentcolor));
		inv.setItem(11, getItem(p, HeadColors.GREEN, currentcolor));
		inv.setItem(12, getItem(p, HeadColors.BLUE, currentcolor));
		inv.setItem(13, getItem(p, HeadColors.CYAN, currentcolor));
		inv.setItem(14, getItem(p, HeadColors.PURPLE, currentcolor));
		inv.setItem(15, getItem(p, HeadColors.GOLD, currentcolor));
		inv.setItem(16, getItem(p, HeadColors.DARK_GRAY, currentcolor));
		inv.setItem(19, getItem(p, HeadColors.RED, currentcolor));
		inv.setItem(20, getItem(p, HeadColors.LIME, currentcolor));
		inv.setItem(21, getItem(p, HeadColors.LIGHT_BLUE, currentcolor));
//		inv.setItem(22, getItem(p, HeadColors.BLACK, currentcolor));
		inv.setItem(23, getItem(p, HeadColors.PINK, currentcolor));
		inv.setItem(24, getItem(p, HeadColors.YELLOW, currentcolor));
		inv.setItem(25, getItem(p, HeadColors.GRAY, currentcolor));
		inv.setItem(30, getItem(p, HeadColors.AQUA, currentcolor));
		inv.setItem(32, getItem(p, HeadColors.WHITE, currentcolor));
		inv.setItem(40, getItem(p, HeadColors.RAINBOW, currentcolor));

		p.openInventory(inv);
	}

	public static ItemStack getItem(Player p, HeadColors color, String currentcolor) {
		ItemStack i = getSkull(color.getUrl());
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(Util.color(color.getColor() + color.getName()));
		if (p.hasPermission("mcf.chatcolor." + color.name())) {
			if (currentcolor.contains(color.getColor().replace("&", ""))) {
				m.setLore(Arrays.asList("", Util.color("&6Permission: &a&l✓"), "",
						Util.color("&e> Currently Equipped <")));
			} else {
				m.setLore(Arrays.asList("", Util.color("&6Permission: &a&l✓"), "", Util.color("&6> Click to Equip <")));
			}
		} else {
			m.setLore(Arrays.asList("", Util.color("&6Permission: &c&l✗")));
		}
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack getSkull(String url) {
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		if (url.isEmpty())
			return item;

		SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder()
				.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = itemMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(itemMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		item.setItemMeta(itemMeta);
		return item;
	}
}

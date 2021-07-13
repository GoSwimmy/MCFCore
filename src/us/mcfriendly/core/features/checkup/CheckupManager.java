package us.mcfriendly.core.features.checkup;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.Util;

public class CheckupManager {

	@SuppressWarnings("deprecation")
	public static void checkup(Player p, String target) {
		OfflinePlayer t = Bukkit.getOfflinePlayer(target);
		String ip = t.getPlayer().getAddress().getAddress().toString().replace("/", "");
		String apikey = DataManager.config.getString("options.checkup.api-key");
		ProxyCheck pc = new ProxyCheck(apikey);
		try {
			pc.parseResults(ip);
		} catch (ParseException | IOException ex) {
			p.sendMessage(Util.color(Util.prefix() + "&cAn error occurred, please try again later"));
			return;
		}
 		p.sendMessage(Util.color("&f&l&m---=[&6&l " + t.getName() + "'s Checkup &f&l&m]=---"));
		p.sendMessage(Util.color(" "));
		p.sendMessage(Util.color("&6Username History:"));
		p.sendMessage(Util.color("&e- GoSwimmy (02/05/2005)"));
		p.sendMessage(Util.color(" "));
		p.sendMessage(Util.color("&6VPN Information:"));
		if (pc.status.equalsIgnoreCase("ok")) {
			p.sendMessage(Util.color("&e- Status (" + pc.status.toUpperCase() + ")"));
			p.sendMessage(Util.color("&e- VPN (" + pc.proxy.toUpperCase() + ")"));
			p.sendMessage(Util.color("&e- Type (" + pc.type.toUpperCase() + ")"));
			if (pc.provider == null) {
				p.sendMessage(Util.color("&e- Provider (N/A)"));
			} else {
				p.sendMessage(Util.color("&e- Provider (" + pc.provider.toUpperCase() + ")"));
			}
		} else if (pc.status.equalsIgnoreCase("warning") || pc.status.equalsIgnoreCase("denied")
				|| pc.status.equalsIgnoreCase("error")) {
			p.sendMessage(Util.color("&e- Status (" + pc.status.toUpperCase() + ")"));
			p.sendMessage(Util.color("&e- Message (" + pc.message + ")"));
			p.sendMessage(Util.color("&e- VPN (" + pc.proxy.toUpperCase() + ")"));
			p.sendMessage(Util.color("&e- Type (" + pc.type.toUpperCase() + ")"));
			if (pc.provider == null) {
				p.sendMessage(Util.color("&e- Provider (N/A)"));
			} else {
				p.sendMessage(Util.color("&e- Provider (" + pc.provider.toUpperCase() + ")"));
			}
		}

	}

	public static JSONObject getName(UUID uuid) {
		String url = "https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names";
		try {
			@SuppressWarnings("deprecation")
			String nameJson = IOUtils.toString(new URL(url));
			JSONArray nameValue;
			JSONObject nameObject = null;
			try {
				nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
				String playerSlot = nameValue.get(nameValue.size() - 1).toString();
				nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
			return nameObject;
		} catch (IOException e) {
			return null;
		}
	}
}

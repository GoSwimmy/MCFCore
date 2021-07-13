package us.mcfriendly.core.features.customtags;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.Util;

public class CustomTagManager {

	public static void createCustomTag(Player p, String[] rawargs) {
		boolean checkTag = true;
		String[] args = rawargs;
		String newstring = Util.buildString(rawargs);
		if (newstring.contains("-f ")) {
			checkTag = false;
			args = newstring.replaceFirst("-f ", "").split(" ");
		}
		Player t = Bukkit.getPlayer(args[0]);
		if (t == null) {
			p.sendMessage(Util.getMessage("options.createtag.messages.player-offline"));
		} else {
			if (checkTag) {
				boolean hasTag = false;
				for (int i = 0; i < 35; i++) {
					if (t.getInventory().getItem(i) == null)
						continue;
					if (t.getInventory().getItem(i).getItemMeta().getDisplayName().contains("§6§lCustom Tag")) {
						t.getInventory().getItem(i).setAmount(t.getInventory().getItem(i).getAmount() - 1);
						hasTag = true;
						break;
					}
				}
				if (!hasTag) {
					p.sendMessage(Util.getMessage("options.createtag.messages.doesnt-have-tag"));
					return;
				}
			}
			String id = UUID.randomUUID().toString().split("-")[0];
			String tag = "";
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i] + " ");
			}
			tag = sb.toString().trim();

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mcftags create " + t.getName() + "-" + id + " " + tag);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"lp user " + t.getName() + " permission set mcftags.tag." + t.getName() + "-" + id + " true");
			p.sendMessage(Util.getMessage("options.createtag.messages.created-tag"));
			t.sendMessage(Util.getMessage("options.createtag.messages.player-created-tag"));
		}
	}
}

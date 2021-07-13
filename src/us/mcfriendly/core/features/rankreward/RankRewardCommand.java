package us.mcfriendly.core.features.rankreward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.Util;

public class RankRewardCommand {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(DataManager.config.getString("options.rankreward.donorpermission"))) {
				if (p.hasPermission(DataManager.config.getString("options.rankreward.donorpermission"))) {
					if (args.length == 0) {
						RankRewardManager.claimRankReward(p);
						return false;
					}
					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("force")) {
							RankRewardManager.forcePlayer(p, args[1]);
							return false;
						}
						if (args[0].equalsIgnoreCase("reset")) {
							RankRewardManager.resetPlayer(p, args[1]);
							return false;
						}
					}
					p.sendMessage(Util.color("&f&l&m------------=[&6&l Rank Rewards &f&l&m]=------------"));
					p.sendMessage(Util.color(" "));
					p.sendMessage(Util.color("&f/rankreward"));
					p.sendMessage(Util.color("&f/rankreward &6reset &f<player>"));
					p.sendMessage(Util.color("&f/rankreward &6force &f<player>"));
				} else {
					RankRewardManager.claimRankReward(p);
				}
			} else {
				p.sendMessage(Util.getMessage("options.rankreward.messages.notdonor"));
			}
		} else {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("force")) {
					RankRewardManager.forcePlayer(sender, args[1]);
					return false;
				}
				if (args[0].equalsIgnoreCase("reset")) {
					RankRewardManager.resetPlayer(sender, args[1]);
					return false;
				}
			}
		}
		return false;
	}
}

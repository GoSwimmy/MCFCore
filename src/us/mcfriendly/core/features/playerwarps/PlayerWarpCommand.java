package us.mcfriendly.core.features.playerwarps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.Util;

public class PlayerWarpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				PlayerWarpGui.openGui(p, 1);
				return false;
			}
			if (p.hasPermission(DataManager.config.getString("options.playerwarps.create-permission"))) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("delete")) {
						PlayerWarpManager.delete(p);
						return false;
					}
					if (args[0].equalsIgnoreCase("seticon")) {
						PlayerWarpManager.setIcon(p);
						return false;
					}
					if (args[0].equalsIgnoreCase("relocate")) {
						PlayerWarpManager.relocate(p);
						return false;
					}
					if (args[0].equalsIgnoreCase("premium")) {
						if(p.hasPermission("mcf.adminpwarp")) {
							PlayerWarpGui.openPremiumGui(p);
						} else {
							p.sendMessage(Util.color(Util.prefix() + "SubCommand Current Disabled!"));
						}
						return false;
					}
				}
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("delete")) {
						PlayerWarpManager.adminDelete(p, args[1]);
						return false;
					}
				}
				if (args.length >= 2) {
					if (args[0].equalsIgnoreCase("create")) {
						StringBuilder rawname = new StringBuilder();
						for (int i = 1; i < args.length - 1; i++) {
							rawname.append(args[i] + " ");
						}
						rawname.append(args[args.length - 1]);
						PlayerWarpManager.create(p, rawname.toString());
						return false;
					}
					if (args[0].equalsIgnoreCase("rename")) {
						StringBuilder rawname = new StringBuilder();
						for (int i = 1; i < args.length - 1; i++) {
							rawname.append(args[i] + " ");
						}
						rawname.append(args[args.length - 1]);
						PlayerWarpManager.rename(p, rawname.toString());
						return false;
					}
				}
				if (args.length >= 3) {
					if (args[0].equalsIgnoreCase("setlore")) {
						StringBuilder rawlore = new StringBuilder();
						for (int i = 2; i < args.length - 1; i++) {
							rawlore.append(args[i] + " ");
						}
						rawlore.append(args[args.length - 1]);
						PlayerWarpManager.setLore(p, args[1], rawlore.toString());
						return false;
					}
				}
				p.sendMessage(Util.color("&f&l&m------------=[&6&l Player Warps &f&l&m]=------------"));
				p.sendMessage(Util.color(" "));
				p.sendMessage(Util.color("&f/playerwarp &6help"));
				p.sendMessage(Util.color("&f/playerwarp &6delete"));
				p.sendMessage(Util.color("&f/playerwarp &6relocate"));
				p.sendMessage(Util.color("&f/playerwarp &6seticon"));
				p.sendMessage(Util.color("&f/playerwarp &6premium"));
				p.sendMessage(Util.color("&f/playerwarp &6create &f<text>"));
				p.sendMessage(Util.color("&f/playerwarp &6rename &f<text>"));
				p.sendMessage(Util.color("&f/playerwarp &6setlore &f<1-3> &6<text>"));
			} else {
				p.sendMessage(Util.getMessage("options.playerwarps.messages.nopermission"));
			}
		}
		return false;
	}

}

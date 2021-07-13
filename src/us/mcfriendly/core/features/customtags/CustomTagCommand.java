package us.mcfriendly.core.features.customtags;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.Util;

public class CustomTagCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("mcftags.createtag")) {
				if(args.length >= 2) {
					CustomTagManager.createCustomTag(p, args);
					return false;
				}	
				p.sendMessage(Util.color("&f&l&m------------=[&6&l Custom Tags &f&l&m]=------------"));
				p.sendMessage(Util.color("&7&o<> = required | () = options"));
				p.sendMessage(Util.color("&7&o-f = Force tag without checking if they have the item"));
				p.sendMessage(Util.color(" "));
				p.sendMessage(Util.color("&f/createtag &6help"));
				p.sendMessage(Util.color("&f/createtag &6<user> &f<tag>"));
				p.sendMessage(Util.color("&f/createtag &6-f &f<user> &6<tag>"));
			}
		}
		return false;
	}
	
}

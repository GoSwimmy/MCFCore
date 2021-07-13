package us.mcfriendly.core.features.checkup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.Util;

public class CheckupCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("mcf.admin")) {
				if (args.length == 1) {
					String target = args[0];
					CheckupManager.checkup(p, target);
				} else {
					p.sendMessage(Util.color(Util.prefix() + "&cInvalid usage, try /checkup <player>"));
				}
			}
		}
		return false;
	}
}

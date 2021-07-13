package us.mcfriendly.core.features.rounds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.Util;

public class RoundsCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("mcf.rounds")) {
				p.sendMessage(Util.prefix() + "§cCommand Under Development...");
				return false;
//				RoundsManager.roundsGui(p);
			} else {
				p.sendMessage(Util.prefix() + "§cYou do not have permission to use this command!");
			}
		}
		return false;
	}
}

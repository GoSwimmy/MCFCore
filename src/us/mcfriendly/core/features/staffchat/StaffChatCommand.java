package us.mcfriendly.core.features.staffchat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.Util;

public class StaffChatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(DataManager.config.getString("options.staffchat.permission"))) {
				if (args.length == 0) {
					p.sendMessage(Util.getMessage("options.staffchat.messages.invalidusage"));
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length - 1; i++) {
						sb.append(args[i] + " ");
					}
					sb.append(args[args.length - 1]);
					StaffChatManager.sendStaffMessage(p, sb.toString());
				}
			} else {
				p.sendMessage(Util.getMessage("options.staffchat.messages.nopermission"));
			}
		}
		return false;
	}
}

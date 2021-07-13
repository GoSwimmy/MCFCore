package us.mcfriendly.core.features.chatcolor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import us.mcfriendly.core.managers.Util;

public class ChatColorEvent implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().contains("§6§lChatColor")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			ItemStack i = e.getCurrentItem();
			for(String lore : i.getItemMeta().getLore()) {
				if(ChatColor.stripColor(lore).contains("Currently")) {
					p.sendMessage(Util.color(Util.prefix() + "&cYou already have that Chat Color equipped!"));
					return;
				}
			}
			HeadColors hc = HeadColors
					.valueOf(ChatColor.stripColor(i.getItemMeta().getDisplayName()).replace(" ", "_").toUpperCase());
			if(p.hasPermission("mcf.chatcolor."+hc.name())) {
				ChatColorManager.setColor(p, hc);
				ChatColorGui.openGui(p);
			} else {
				p.sendMessage(Util.color(Util.prefix() + "&cYou do not have permission to use this Chat Color!"));
			}
		}
	}
}

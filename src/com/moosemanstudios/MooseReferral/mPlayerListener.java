package com.moosemanstudios.MooseReferral;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class mPlayerListener extends PlayerListener {
	MooseReferral plugin;
	
	mPlayerListener(MooseReferral instance) {
		plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		// see if the player hasn't logged in before
		if (!plugin.playerHash.containsKey(player.getName())) {
			plugin.playerHash.put(player.getName(), false);	
		}
		
		// see if the player has made a referral already
		if (!plugin.playerHash.get(player.getName())) {
			player.sendMessage("Welcome " + player.getName() + ", if you were referred to this server by");
			player.sendMessage("another player, let them know you appreciate them referring");
			player.sendMessage("you by typing the command " + ChatColor.YELLOW + "/mRefer player [referee]");
		}
		
		// see if the player has any unclaimed bonuses
		if (plugin.playerBonus.containsKey(player.getName())) {
			int bonus = plugin.playerBonus.get(player.getName());
			
			if (bonus > 0) {
				player.sendMessage("You have an unclaimed bonus for referring a player!");
				player.sendMessage("Use the command " + ChatColor.YELLOW + "/mRefer claim" + ChatColor.WHITE + " to claim");
			}
		}
	}
}	

package com.moosemanstudios.MooseReferral;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
//import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MooseReferral extends JavaPlugin {
	Logger log = Logger.getLogger("minecraft");
	PluginDescriptionFile pdfFile = null;
	PluginManager pm = null;
	private final mPlayerListener playerlistener = new mPlayerListener(this);
	
	// TODO: public HashSet<mPlayer> playerHash = new HashSet<mPlayer>();
	public HashMap<String, Boolean> playerHash = new HashMap<String, Boolean>();
	public HashMap<String, Integer> playerBonus = new HashMap<String, Integer>();
	public HashMap<String, Integer> playerRefers = new HashMap<String, Integer>();
	
	public void onEnable() {
		// load up the config file
		
		// register events
		pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_JOIN, playerlistener, Priority.Normal, this);
		
		pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "] Version " + pdfFile.getVersion() + " is now enabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String[] split = args;
		String commandName = cmd.getName().toLowerCase();
		
		if (commandName.equalsIgnoreCase("mRefer")) {
			if (split.length == 0) {
				sender.sendMessage(ChatColor.RED + "Type " + ChatColor.WHITE + "/mRefer help" + ChatColor.RED +" for help");
				return true;
			}
			
			if (split[0].equalsIgnoreCase("help")) {
				// display help menu
				
				sender.sendMessage("Moose Referral Help");
				sender.sendMessage("-----------------------------");
				sender.sendMessage(ChatColor.RED + "/mRefer help" + ChatColor.WHITE + ": Displays this screen");
				sender.sendMessage(ChatColor.RED + "/mRefer player [player]" + ChatColor.WHITE + ": Who referred you");
				sender.sendMessage(ChatColor.RED + "/mRefer claim" + ChatColor.WHITE + ": Claims any bonuses you have");
				sender.sendMessage(ChatColor.RED + "/mRefer list" + ChatColor.WHITE + ": Display top 5 Referrers");

				if (sender.hasPermission("mRefer.admin")) {
					sender.sendMessage(ChatColor.RED + "/mRefer reload" + ChatColor.WHITE + ": reload files");
					sender.sendMessage(ChatColor.RED + "/mRefer pending" + ChatColor.WHITE + ": view pending bonuses");
				}
			}
			
			if (split[0].equalsIgnoreCase("claim")) {
				// TODO: allow player to claim bonus if they have any
				
			}
			
			if (split[0].equalsIgnoreCase("list")) {
				// list top 5 referral people
				
				TreeMap<String, Integer> sortedHash = new TreeMap<String, Integer>(playerRefers);
				
				// display first 5 from the map
				sender.sendMessage("mRefer Top Referrers");
				sender.sendMessage("--------------------------");
				
				int i = 0;
				for (Map.Entry<String, Integer> entry : sortedHash.entrySet()) {
					i++;
					if (i<=5) {
						sender.sendMessage(i + ")" + entry.getKey() + "-" + entry.getValue());
					}
				}
			}
			
			if (split[0].equalsIgnoreCase("player")) {
				// make sure its a player first of all
				if (sender instanceof Player) {
					// see if they haven't already referred anybody
					if (playerHash.containsKey(sender.getName())) {
						
						log.info("hashmap contains player " + sender.getName() + playerHash.get(sender.getName()));
						
						if (playerHash.get(sender.getName())) {
							// player already referred, tell them
							sender.sendMessage(ChatColor.RED + "You have already specified who referred you!");
						} else {
							playerHash.put(sender.getName(), true);
							if (split.length != 2) {
								sender.sendMessage(ChatColor.RED + "Must specify player");
							} else {
								// at this point get the player who referred the player
								String player = split[1];
								
								log.info("selected player " + player);
								
								//add to players unclaimed bonuses
								playerBonus.put(player, playerBonus.get(player) + 1);
								playerRefers.put(player, playerRefers.get(player) + 1);
								
								// alert both players that operation was successful
								sender.sendMessage("You have thanked " +  player + " for referring you.");
								getServer().getPlayer(player).sendMessage(sender.getName() + " has thanked you for referring them");
								getServer().getPlayer(player).sendMessage(sender.getName() + "Use the command " + ChatColor.YELLOW + "/mRefer claim" + " to claim");
							}							
						}
					} else {
						playerHash.put(sender.getName(),true);
						if (split.length != 2) {
							sender.sendMessage(ChatColor.RED + "Must specify player");
						} else {
							// at this point get the player who referred the player
							String player = split[1];
							
							log.info("selected player " + player);
							
							//add to players unclaimed bonuses
							playerBonus.put(player, playerBonus.get(player) + 1);
							playerRefers.put(player, playerRefers.get(player) + 1);
							
							// alert both players that operation was successful
							sender.sendMessage("You have thanked " +  player + " for referring you.");
							getServer().getPlayer(player).sendMessage(sender.getName() + " has thanked you for referring them");
							getServer().getPlayer(player).sendMessage(sender.getName() + "Use the command " + ChatColor.YELLOW + "/mRefer claim" + " to claim");
						}
					}
				} else {
					sender.sendMessage("Only players can do that!");
				}
			}
			
			if (split[0].equalsIgnoreCase("reload")) {
				// TODO: admin, reload files
				
				if (sender.hasPermission("mRefer.admin")) {
					
				} else {
					sender.sendMessage(ChatColor.RED + "You don't have permissions to do that!");
				}
			}
				
			if (split[0].equalsIgnoreCase("pending")) {
				// admin, check people who have unclaimed bonus's
				
				if (sender.hasPermission("mRefer.admin")) {
					// TODO: display people with unclaimed stuff
				} else {
					sender.sendMessage(ChatColor.RED + "You don't have permissions to do that!");
				}
			}
		}
		
		return false;
	}
	
	public void onDisable() {
		log.info("[MooseReferral] is now disabled");
	}
}

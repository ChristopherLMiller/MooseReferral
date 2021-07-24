package com.moosemanstudios.MooseReferral;

public class mPlayer {
	private int numReferrals; // how many times players were referred by player
	private int unclaimedBonus; // how many bonuses player has yet to claim
	private boolean madeReferral; // if the player has told who referred them yet
	private String playerName;	// players name
	
	public int getNumReferrals() {
		return numReferrals;
	}
	
	public void addReferral() {
		numReferrals++;
	}
	
	public int getNumUnclaimedBonus() {
		return unclaimedBonus;
	}
	
	public boolean madeReferral() {
		return madeReferral;
	}
	
	public void setReferralMade() {
		madeReferral = true;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String name) {
		playerName = name;
	}
}

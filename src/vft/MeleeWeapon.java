package vft;

import vft.window.GamePanel;

public enum MeleeWeapon {
	
	Dagger(1, -1, 0),
	Rapier(1, 0, 9),
	Club(1,0, 9),
	Hammer(1, 1, 10),
	Shortsword(2, -1, 11),
	Broadsword(2, 0, 12),
	Claymore(3, -1, 14),
	Battleaxe(3, 0, 15);
	
	public final int dice, mod, st;
	MeleeWeapon(int dice, int modifier, int st) {
		this.dice = dice;
		mod = modifier;
		this.st=st;
	}
	public int damage() {
		int roll = roll(dice);
		GamePanel.message(this.toString() + " damage = " + roll+" + " + mod + ".");
		return Math.max(roll + mod, 1);
	}

	public int roll(int dice) {
		int roll = 0;
		for(int i = 0; i < dice; i++)
		{
			roll += (int)(Math.random() * 6 + 1);
		}
		return roll;
	}
	
}
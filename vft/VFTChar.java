package vft;

import java.util.ArrayList;

public class VFTChar {
	
	private String name;
	private int st, dx, adjDx, iq, points;
	private MeleeWeapon weapon;
	private Armor armor;
	private int exp;
	boolean isUnique;
	ArrayList<String> kills;
	
	public VFTChar(String n, int s, int d, int i, int p, MeleeWeapon w, Armor a, boolean isUnique) {
		name = n;
		st = s;
		dx = d;
		iq = i;
		points = p;
		armor = a;
		weapon = w;
		adjDx = dx - armor.mod;
		exp = 0;
		this.isUnique = isUnique;
		kills = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}

	public int getSt() {
		return st;
	}

	public int getDx() {
		return dx;
	}

	public int getIq() {
		return iq;
	}

	public int getPoints() {
		return points;
	}

	public MeleeWeapon getWeapon() {
		return weapon;
	}

	public Armor getArmor() {
		return armor;
	}
	
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public boolean equals(Object o) {
		VFTChar c = (VFTChar)o;
		return name.equals(c.name);
	}
	public boolean isUnique() {
		return isUnique;
	}
	
	public int getMoves() {
		return 10-armor.mod;
	}
	
	
}
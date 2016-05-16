package vft;



import java.awt.Color;
import java.util.Random;

import vft.map.Hex;
import vft.map.Layout;
import vft.window.GamePanel;

public class GameChar  {

	VFTChar internalChar;
	Hex position;
	int currHP;
	Layout layout;
	int initiative;
	int moves;
	int gainedXP;
	Color color;
	boolean dead = false;
	GameChar killer = null;
	//int orientation;
	public GameChar(VFTChar internalChar, Layout l){
		this.internalChar = internalChar;
		layout = l;
		currHP = internalChar.getSt();
		moves = internalChar.getMoves();
	}
	public GameChar(VFTChar internalChar){
		this(internalChar, null);
	}
	
	public String toString() {
		return getChar().getName();
	}

	public VFTChar getChar() {
		return internalChar;
	}
	public Hex getPosition() {
		return position;
	}
	public void setPosition(Hex position) {
		this.position = position;
	}
	public int getCurrHP() {
		return currHP;
	}
	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}
	public void changeHP(int change) {
		currHP += change;
	}
	public Layout getLayout() {
		return layout;
	}
	public int getInitiative() {
		return initiative;
	}
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	public int getMoves() {
		return moves;
	}
	public void setMoves(int moves) {
		this.moves = moves;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void attack(GameChar opp) {

		GamePanel.message(internalChar.getName() + " is attacking " + opp.getChar().getName());
		Random random = new Random();
		int roll = random.nextInt(6)+1+random.nextInt(6)+1+random.nextInt(6)+1;
		if((roll > getChar().getAdjDx() || roll > 15) && roll > 5) {
			GamePanel.message(getChar().getName()+" missed with a roll of " + roll+".");
		}
		else {
			int damage = getChar().getWeapon().damage(); //CRITICALS
			if(roll == 3) damage *= 3;
			if(roll == 4) damage *= 2;

			damage = Math.max(damage - opp.getChar().getArmor().hits, 1);

			GamePanel.message(String.format(this + " did %d damage to %s!%n", damage, ""+opp));
			opp.damage(damage, this);

		}
	}
	public void refresh() {
		setMoves(getChar().getMoves());
	}
	public void damage(int damage, GameChar opp) {
		currHP -= damage;
		
		if(currHP <= 0) {
			dead = true;
			killer = opp;
			die();
		}
	}
	public void die() {
		if(killer.getChar().isUnique()) {
			if(getChar().isUnique()) {
				killer.gainedXP += 60;
			}
			else killer.gainedXP += 30;
		}
		GamePanel.message(getChar().getName() + " was killed by " + killer.getChar().getName());
	}
	public boolean isDead() {
		return dead;
	}



}

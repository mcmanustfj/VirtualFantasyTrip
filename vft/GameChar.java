package vft;

import java.awt.Color;

import vft.map.Hex;
import vft.map.Layout;

public class GameChar  {

	VFTChar internalChar;
	Hex position;
	int currHP;
	Layout layout;
	int initiative;
	int moves;
	Color color;
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
	
	
	

}

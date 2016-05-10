package vft.map;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vft.GameChar;
import vft.VFTChar;
public class Hex {
	final int q, r, s; //x, y, z
	final int[] array;
	boolean selected, focused, highlighted;
	GameChar character;
	final static Hex[] hex_directions = new Hex[]{ //add these to a hex to get the neighbor in that direction, starting from up+right and going clockwise (probably)
			new Hex(1, 0, -1),
			new Hex(1, -1, 0),
			new Hex(0, -1, 1),
			new Hex(-1, 0, 1),
			new Hex(-1, 1, 0),
			new Hex(0, 1, -1)
	};
	public Hex(int q, int r, int s) {
		this.q = q;
		this.r = r;
		this.s = s;
		array = new int[]{q, r, s};
		assert(q + r + s == 0);
		selected = false;
	}
	public Hex(int q, int r) {
		this(q, r, -q-r);
	}
	public Hex(int[] v) {
		this(v[0], v[1], v[2]);
	} 
	public int getQ() {return q;}
	public int getR() {return r;}
	public int getS() {return s;}
	public int[] getArray() {return new int[]{q, r, s};}

	public boolean equals(Object o) {
		Hex h = (Hex)o;
		return q == h.q && r == h.r && s == h.s;
	}
	public static Hex hex_add(Hex a, Hex b) {
		return new Hex(a.q + b.q, a.r+b.r, a.s+b.s);
	}
	public static Hex hex_subtract(Hex a, Hex b) {
		return new Hex(a.q - b.q, a.r-b.r, a.s-b.s);
	}
	public static Hex hex_multiply(Hex a, int k) {
		return new Hex(a.q * k, a.r*k, a.s*k);
	}
	public static int hex_length(Hex hex) {
		return (Math.abs(hex.q) + Math.abs(hex.r) + Math.abs(hex.s) / 2);
	}
	public static int hex_distance(Hex a, Hex b) {
		return hex_length(hex_subtract(a, b));
	}
	public Hex hex_direction(int d) {
		return hex_directions[d % 6];
	}
	public Hex hex_neighbor(Hex hex, int d) {
		return hex_add(hex, hex_direction(d));
	}
	public Set<Hex> range(int range, Layout l) {
		Set<Hex> hexSet = new HashSet<Hex>();
		for(Hex h : l.map) {
			if(hex_distance(h, this) <= range)
				hexSet.add(h);
		}

		return hexSet;
	}
	public Offset asQOffset() {
		return Offset.qHexToOffset(1, this);
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean selected() {
		return selected;
	}

	public boolean isFocused() {
		return focused;
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	public boolean isOccupied() {
		return character != null;
	}
	public String toString() {
		return String.format("q: %3d r: %3d s: %3d", q, r, s);
	}
	public VFTChar getChar() {
		return character.getChar();
	}
	public void setGameChar(GameChar c) {
		character = c;
		c.setPosition(this);
	}
	public GameChar getGameChar() {
		return character;
	}
	public Path2D toPoly(Layout layout) {
		return layout.toPoly(this);
	}
	public Point2D toPixel(Layout l) {
		return l.hexToPixel(this);
	}
	public void paint(Graphics2D g, Layout l) {
		if(focused){
			//g.setStroke(new BasicStroke(2));
			Color cColor = getColor();
			g.setColor(new Color(cColor.getRed(), cColor.getGreen(), cColor.getBlue(), 150));
			g.fill(toPoly(l));
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(5));
		}
		else if(highlighted){
			//g.setStroke(new BasicStroke(2));
			g.setColor(Color.white);
			g.fill(toPoly(l));
			Color cColor = getColor();
			g.setColor(new Color(cColor.getRed(), cColor.getGreen(), cColor.getBlue(), 100));
			g.fill(toPoly(l));
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(2));
		}

		else if(selected){
			//g.setStroke(new BasicStroke(2));
			g.setColor(Color.white);
			g.fill(toPoly(l));
			Color cColor = getColor();
			g.setColor(new Color(cColor.getRed(), cColor.getGreen(), cColor.getBlue(), 200));
			g.fill(toPoly(l));
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(3));
		}
		else {
			g.setColor(Color.white);
			g.fill(toPoly(l));
			Color cColor = getColor();
			g.setColor(new Color(cColor.getRed(), cColor.getGreen(), cColor.getBlue(), 100));
			g.fill(toPoly(l));
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.black);
		}

		for(int i = 0; i < 6; i++) {
			Point2D[] points = polygonCorners(l);
			g.draw(new Line2D.Double(points[i], points[(i+1)%6]));
		}
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.black);
		if(isOccupied()) {
			String toDraw = getChar().getName().substring(0, 1);
			g.setFont(new Font("Times New Roman", Font.BOLD, 24));
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(toDraw, g);

			g.drawString(toDraw, (int)(toPixel(l).getX() - rect.getWidth()/2), (int)(toPixel(l).getY() + rect.getHeight()/4));
		}
		else {
			//g.drawString("q:"+q+" r:" + r+" s:"+s, (int)l.hexToPixel(this).getX() - 25, (int)l.hexToPixel(this).getY());
		}
	}
	private Point2D[] polygonCorners(Layout l) {
		return l.polygonCorners(this);
	}
	public List<Hex> getNeighbors() {
		List<Hex> list = new ArrayList<>();
		for(Hex d : hex_directions) {
			list.add(hex_add(this, d));
		}
		return list;
	}
	public Color getColor() {
		if(character == null) return Color.lightGray;
		else if(character.getColor() == null) return Color.green;
		else return character.getColor();
	}
	public void setHighlighted(boolean bool) {
		highlighted = bool;
	}
	public boolean getHighlighted() {
		return highlighted;
	}


}

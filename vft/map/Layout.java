package vft.map;

import vft.map.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.HashSet;
import java.util.Set;

public class Layout {
	final Orientation orientation;
	Point2D size;
	Point2D origin;
	Set<Hex> map;
	Hex[] startingPositions;
	
	final static Point2D defaultSize = new Point2D.Double(30, 30);
	final static Point2D defaultOrigin = new Point2D.Double(0, 0);
	
	public Layout(Orientation o, Point2D s, Point2D or) {
		orientation = o;
		size = s;
		origin = or;
	}
	
	public  Point2D hexToPixel(Hex h) {
		Orientation o = orientation;
		double x = (o.fM[0] * h.q + o.fM[1] * h.r) * size.getX();
		double y = (o.fM[2] * h.q + o.fM[3] * h.r) * size.getY();
		return new Point2D.Double(x+origin.getX(), y + origin.getY());
	}
	
	public FractionalHex pixelToHexRaw(Point2D p) {
		Orientation o = orientation;
		Point2D pt = new Point2D.Double((p.getX()-origin.getX()) / size.getX(), 
										(p.getY()-origin.getY()) / size.getY());
		double q = o.bM[0] * pt.getX() + o.bM[1] * pt.getY();
		double r = o.bM[2] * pt.getX() + o.bM[3] * pt.getY();
		return new FractionalHex(q, r, -q-r);
	}
	public Hex pixelToHex(Point2D p) {
		return (pixelToHexRaw(p).round());
	}
	
	public Point2D hexCornerOffset(int corner) {
		Point2D s = size;
		double angle = 2 * Math.PI * (corner + orientation.startAngle) / 6;
		return new Point2D.Double(s.getX() * Math.cos(angle), s.getY() * Math.sin(angle));
	}
	
	public Point2D[] polygonCorners(Hex h) {
		Point2D[] corners = new Point2D[6];
		Point2D center = hexToPixel(h);
		for(int i = 0; i < 6; i++) {
			Point2D offset = hexCornerOffset(i);
			corners[i] = new Point2D.Double(center.getX() + offset.getX(), center.getY() + offset.getY());
		}
		return corners;
	}
	
	
	public void setSet(Set<Hex> set) {
		map = set;
	}
	
	public Set<Hex> getMap() {
		return map;
	}
	
	public void setOrigin(Point2D origin) {
		this.origin = origin;
	}
	public Point2D getOrigin() {
		return origin;
	}
	public void setSize(Point2D size) {
		this.size = size;
	}
	
	public Hex getHex(Hex h)
	{
		if(h == null) return null;
		for(Hex hex : map) {
			if(h.equals(hex))
				return hex;
		}
		return null;
	}
	
	public void focus(Hex h) {
		for(Hex hex : map) {
			hex.setFocused(false);
		}
		if(h != null)
			h.setFocused(true);
	}

	public void setStartingPositions(Hex[] startingPositions) {
		this.startingPositions = startingPositions;
	}

	public Hex[] getStartingPositions() {
		return startingPositions;
	}
	
	public void highlightHexes(Set<Hex> hset, boolean bool) {
		for(Hex h : hset) {
			getHex(h).setHighlighted(bool);
		}
	}
	
	public Path2D toPoly(Hex h) {
		Path2D polygon = new Path2D.Double();
		Point2D[] points = polygonCorners(h);
		polygon.moveTo(points[0].getX(), points[0].getY());
		for(Point2D p : points) {
			polygon.lineTo(p.getX(), p.getY());
		}
		return polygon;
	}
	public void paint(Graphics2D g) {
		
		for(Hex hex : map) {
			if(!(hex.focused || hex.highlighted || hex.selected))
			hex.paint(g, this);
		}
		for(Hex hex : map) {
			if(hex.highlighted && !(hex.focused || hex.selected))
			hex.paint(g, this);
		}
		for(Hex hex : map) {
			if(hex.selected && !hex.focused)
			hex.paint(g, this);
		}
		for(Hex hex : map) {
			if(hex.focused) {
				hex.paint(g, this);
			}
		}
	}
	public static Layout genPgram(int size) {
		Set<Hex> map = new HashSet<Hex>();
		for(int q = -size/2; q < (size-size/2); q++) {
			for(int s = -size/2; s < size-size/2; s++) {
				map.add(new Hex(q, -q-s, s));
			}
		}
		
		Layout layout = new Layout(Orientation.pointyLayout, defaultSize, defaultOrigin);
		Hex top;
		Hex bot;
		if(size % 2 == 0) {
			top = new Hex((size-2)/2, -(size-2), (size-2)/2);
			bot = new Hex(-size/2, size, -size/2);
		}
		else {
			top = new Hex((size-1)/2, -(size-1), (size-1)/2);
			bot = new Hex(-(size-1)/2, size-1, -(size-1)/2);
		}
		layout.setStartingPositions(new Hex[]{top, bot});
		layout.setSet(map);
		return layout;
	}
	
	
}
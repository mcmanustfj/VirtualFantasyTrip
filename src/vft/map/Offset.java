package vft.map;

public class Offset {
	final int col, row;
	final int EVEN = 1;
	final int ODD = -1;
	public Offset(int c, int r) {
		col = c;
		row = r;
	}
	
	public static Offset qHexToOffset(int offset, Hex h){
		int col = h.q;
		int row = h.r + ((h.q + offset * (h.q & 1)) / 2);
		return new Offset(col, row);
	}
	
	public Hex qOffsetToCube(int offset) {
		int q = col;
		int r = row - ((col + offset *(col&1)) / 2);
		return new Hex(q, r, -q-r);
		
	}
	
	public static Offset rHexToOffset(int offset, Hex h) {
		int col = h.q + ((h.r + offset * (h.q&1))/2);
		int row = h.r;
		return new Offset(col, row);
	}
	
	public Hex rOffsetToCube(int offset) {
		int q = col - ((row + offset * (row&1))/2);
		int r = row;
		return new Hex(q, r, -q-r);
	}
	
}

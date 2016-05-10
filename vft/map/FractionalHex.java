package vft.map;
import vft.map.Hex;

public class FractionalHex {
	final double[] array;
	final double q, r, s;
	
	public FractionalHex(double[] array_) {
		array = array_;
		this.q = array[0];
		this.r = array[1];
		this.s = array[2];
	}
	public FractionalHex(double q, double r, double s) {
		this(new double[]{q, r, s});
	}
	
	public Hex round()
	{
		int rq = (int)Math.round(array[0]);
		int rr = (int)Math.round(array[1]);
		int rs = (int)Math.round(array[2]);

		double q_diff = Math.abs(rq - q);
		double r_diff = Math.abs(rr - r);
		double s_diff = Math.abs(rs - s);
		
		if(q_diff > r_diff && q_diff>s_diff)
			rq = -rr-rs;
		else if(r_diff > s_diff)
			rr = -rq-rs;
		else
			rs = -rq-rr;
		
		return new Hex(rq, rr, rs);
		
	}
	
	public String toString() {
		return String.format("q: %3f r: %3f s: %3f", q, r, s);
	}
	
}

package vft.map;

enum Orientation {
	pointyLayout(new double[]{Math.sqrt(3.0), Math.sqrt(3) / 2.0, 0, 3/2.0}, 
				 new double[]{Math.sqrt(3)/3, -1/3.0, 0, 2/3.0}, 0.5),
	
	flatLayout(new double[]{3.0/2, 0, Math.sqrt(3)/2, Math.sqrt(3)}, 
			   new double[]{2.0/3, 0, -1.0/3, Math.sqrt(3) / 3}, 0);
	
	final double[] fM, bM;
	final double startAngle;
	Orientation(double[] forward, double[] backward, double start_angle_) {
		fM = forward;
		bM = backward;
		startAngle = start_angle_;
	}
}
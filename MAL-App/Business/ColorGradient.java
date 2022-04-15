package Business;

import java.awt.Color;

public class ColorGradient {
	
	private static Color c;
	private static double ratio;
	//public ColorGradient (double p, double min, double max) {
		
	//}
	public static Color getColorGradient(double p, double plus, double minus, int nbrColor) {
	//HSB 
		if (minus==0) minus=0.01;
		if (plus==0) plus=0.01;
		
		if (p<=0) {
			ratio = Math.abs((Math.log10(Math.abs(p))+2) / (Math.log10(Math.abs(minus))+2));
			c = Color.getHSBColor((float) (((double)(((int)((0.30-0.30*ratio)*nbrColor))))/nbrColor), 1, 1);
		}
		else {
			ratio = Math.abs((Math.log10(Math.abs(p))+2) / (Math.log10(Math.abs(plus))+2));
			c = Color.getHSBColor((float) (((double)(((int)((0.30+0.30*ratio+0.08)*nbrColor))))/nbrColor), 1, 1);
		}
		if (p==0.0) c = Color.getHSBColor((float) 0.30, 1, 1);
		return c;
	}
}

package Business;

import java.awt.Color;

public class ColorGradient {
	
	private static Color c;
	private static double ratio;
	//public ColorGradient (double p, double min, double max) {
		
	//}
	public static Color getColorGradient(double p, double plus, double minus, int nbrColor) {
	//HSB 
		ratio = Math.abs((Math.log10(Math.abs(p))+2) / (Math.log10(Math.abs(minus))+2));
		if (p<=0) c = Color.getHSBColor((float) (((double)(((int)((0.335-0.335*ratio)*nbrColor))))/nbrColor), 1, 1);
		else c = Color.getHSBColor((float) (((double)(((int)((0.335+0.335*ratio)*nbrColor))))/nbrColor), 1, 1);
		if (p==0.0) c = Color.getHSBColor((float) 0.35, 1, 1);
		return c;
	}
}

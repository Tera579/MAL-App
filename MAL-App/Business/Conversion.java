package Business;

import GUI.Drawing;

public class Conversion {
	private static double ech, xMax;
	
	public static int doublepixelX(double valdouble) {
		ech = (Drawing.getWidthDrawing()-40)/(2*xMax);
    	int valint=(int)((valdouble*ech + (double)Drawing.getWidthDrawing()/2)) ;
		return valint;
    }
    public static int doublepixelY(double valdouble) {
    	ech = (Drawing.getWidthDrawing()-40)/(2*xMax);
    	int valint=-(int)((valdouble*ech - (double)Drawing.getHeightDrawing()/2)) ;
		return valint;
    }
    public static double pixeldoubleX(int valint) {
    	ech = (Drawing.getWidthDrawing()-40)/(2*xMax);
    	double valdouble=(((double)valint - (double)Drawing.getWidthDrawing()/2)/ech) ;
		return valdouble;
    }
    public static double pixeldoubleY(int valint) {
    	ech = (Drawing.getWidthDrawing()-40)/(2*xMax);
    	double valdouble=-(((double)valint - (double)Drawing.getHeightDrawing()/2)/ech) ;
		return valdouble;
    }
    public static void setxMax(double xmax) {
    	xMax = xmax;
    }
}

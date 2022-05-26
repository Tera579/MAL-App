package Business;

import GUI.Drawing;

public class Conversion {
	private static double ech;
	
	public static int doublepixelX(double valdouble) {
    	int valint=(int)((valdouble*ech + (double)Drawing.getWidthDrawing()/2)) ;
		return valint;
    }
    public static int doublepixelY(double valdouble) {
    	int valint=-(int)((valdouble*ech - (double)Drawing.getHeightDrawing()/2)) ;
		return valint;
    }
    public static double pixeldoubleX(int valint) {
    	double valdouble=(((double)valint - (double)Drawing.getWidthDrawing()/2)/ech) ;
		return valdouble;
    }
    public static double pixeldoubleY(int valint) {
    	double valdouble=-(((double)valint - (double)Drawing.getHeightDrawing()/2)/ech) ;
		return valdouble;
    }
    public static void setEch(double echdraw) {
    	ech = echdraw;
    }
}

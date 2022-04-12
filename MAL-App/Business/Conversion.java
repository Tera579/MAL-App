package Business;

import GUI.Drawing;

public class Conversion {
	private static int Width = Drawing.getWidthDrawing();
	private static int Height = Drawing.getHeightDrawing();
	private static int ech;
	
	public static int doublepixelX(double valdouble) {
    	int valint=(int)((valdouble*ech + (double)Width/2)) ;
		return valint;
    }
    public static int doublepixelY(double valdouble) {
    	int valint=-(int)((valdouble*ech - (double)Height/2)) ;
		return valint;
    }
    public static double pixeldoubleX(int valint) {
    	double valdouble=(((double)valint - (double)Width/2)/ech) ;
		return valdouble;
    }
    public static double pixeldoubleY(int valint) {
    	double valdouble=-(((double)valint - (double)Height/2)/ech) ;
		return valdouble;
    }
    public static void setEch(int echdraw) {
    	ech = echdraw;
    }
}

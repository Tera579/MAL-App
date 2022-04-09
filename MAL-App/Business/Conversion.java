package Business;

import GUI.Drawing;

public class Conversion {
	public static int Width = Drawing.getWidthDrawing();
	private static int Height = Drawing.getHeightDrawing();
	
	public static int doublepixelX(double valdouble) {
    	int valint=(int)((valdouble + (double)Width/80)*40) ;
		return valint;
    }
    public static int doublepixelY(double valdouble) {
    	int valint=-(int)((valdouble - (double)Height/80)*40) ;
		return valint;
    }
    public static double pixeldoubleX(int valint) {
    	double valdouble=(((double)valint - (double)Width/2)/40) ;
		return valdouble;
    }
    public static double pixeldoubleY(int valint) {
    	double valdouble=-(((double)valint - (double)Height/2)/40) ;
		return valdouble;
    }
}

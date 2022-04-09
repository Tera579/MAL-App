package Business;

import GUI.Drawing;
import Business.Conversion;
import Business.Potentiel;

/**
 *
 * @author Elève
 */
public class ElectricField {
	
	int larg = Drawing.getWidthDrawing();
    int haut = Drawing.getHeightDrawing();
    double pot[][] = new double[haut][larg];  // tableau où vont être stockées les valeurs de potentiel
    int i, j; // variable de tableau 
    

    public ElectricField(Potentiel p) {
        for (i = 0; i < haut; i++) {
        	for (j = 0; j < larg; j++) {
        		double my = Conversion.pixeldoubleY(i);
        		double mx = Conversion.pixeldoubleX(j);
        		Point m = new Point (mx, my, "Field");   
        		Particule a = p.getA();
        		Particule b = p.getB();
        		p.calculPotentiel(a, b, m);
        		pot[i][j] = p.getV();
        	}
        }
    }
    
public double[][] getElectricField() {
    return pot;
}

}
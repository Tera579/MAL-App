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
    int i, j, pixel=0; // variable de tableau 
    double plus, minus, pij;
    

    public ElectricField(Potentiel p) {
    	Particule a = p.getA();
		Particule b = p.getB();
		Point m = new Point (0, 0, "Field"); 
    	p.calculPotentiel(a, b, m);
    	plus = p.getV();
    	minus = plus;
        for (i = 0; i < haut; i++) {
        	for (j = 0; j < larg; j++) {
        		double my = Conversion.pixeldoubleY(i);
        		double mx = Conversion.pixeldoubleX(j);
        		m = new Point (mx, my, "Field");  
        		p.calculPotentiel(a, b, m);
        		pij = p.getV();
        		if (pij>plus) plus=pij;
        		if (pij<minus) minus=pij;
        		pot[i][j] = pij;
        		pixel++;
        	}
        }
    }
    
public double[][] getElectricField() {
    return pot;
}
public double getElectricFieldPlus() {
	System.out.println("P plus="+plus);
    return plus;
}
public double getElectricFieldMinus() {
	System.out.println("P moins="+minus);
    return minus;
}

}
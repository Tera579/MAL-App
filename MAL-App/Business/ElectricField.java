package Business;

import GUI.Drawing;

public class ElectricField {
	
	int larg = Drawing.getWidthDrawing();
    int haut = Drawing.getHeightDrawing();
    double pot[][] = new double[haut+1][larg+1];  // tableau où vont être stockées les valeurs de potentiel
    int i, j; // variable de tableau 
    double plus, minus, pij;
    int maillage=2;
    

    public ElectricField(Potentiel p) {
    	Particule a = p.getA();
		Particule b = p.getB();
		Point m = new Point (0, 0, "Field"); 
    	p.calculPotentiel(a, b, m);
    	plus = p.getV();
    	minus = plus;
    	// Pixel
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
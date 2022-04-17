package Business;

import GUI.Drawing;

public class ElectricField {
	
	int larg = Drawing.getWidthDrawing();
    int haut = Drawing.getHeightDrawing();
    double[][] ele = new double[haut+1][larg+1];  // tableau où vont être stockées les valeurs de potentiel
    int i, j; // variable de tableau 
    double pij, plus, minus;
    Electric electric = new Electric();

    public ElectricField(Potential p) {
    	Particule a = p.getA();
		Particule b = p.getB();
		Point m = new Point (0, 0, "Field");
		plus = electric.geti();
    	minus = plus;
		
    	// Pixel
        for (i = 0; i < haut; i++) {
        	for (j = 0; j < larg; j++) {
        		double my = Conversion.pixeldoubleY(i);
        		double mx = Conversion.pixeldoubleX(j);
        		m = new Point (mx, my, "Field");  
        		electric.calculElectric(a, b, m);
        		pij = electric.geti();
        		if (pij>plus) plus=pij;
        		if (pij<minus) minus=pij;
        		ele[i][j] = pij;
        	}
        }
    }
    
public double[][] getElectricField() {
    return ele;
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
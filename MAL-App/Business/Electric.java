/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

public class Electric {

    protected Particule a,b;
    protected double i, j;
   
    public void Electric() {
    	
    }

    public void calculElectric(Particule A, Particule B, Point calculM) {
    	this.a = A;
    	this.b = B;
        double da = Math.sqrt(Math.pow(a.getPoint().getX()-calculM.getX(),2)+Math.pow(a.getPoint().getY()-calculM.getY(),2)); // attention par 0
        double db = Math.sqrt(Math.pow(b.getPoint().getX()-calculM.getX(),2)+Math.pow(b.getPoint().getY()-calculM.getY(),2)); // attention par 0
        double qa = this.a.q*1e-9;
        double qb = this.b.q*1e-9;
        double c = (double) 1 / (4 * Math.PI * 8.85419782e-12);
        double eA =  (c * qa / Math.pow(da, 2));
        double eB =  (c * qb / Math.pow(db, 2));
        i = eA*(calculM.getX()-a.getPoint().getX())/da + eB*(calculM.getX()-b.getPoint().getX())/db;
        j = eA*(calculM.getY()-a.getPoint().getY())/da + eB*(calculM.getY()-b.getPoint().getY())/db;
    }
    
    public double geti() {
        return this.i;
    }
    public double getj() {
        return this.j;
    }
}
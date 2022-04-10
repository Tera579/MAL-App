/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author Elève
 */
public class Potentiel {

    protected Particule a,b;
    protected Point m; 
    protected double v;

    public Potentiel(String name, Particule A, Particule B, Point M) {
    	this.a = A;
    	this.b = B;
    	this.m = M;
    }
    

    public void calculPotentiel(Particule A, Particule B, Point calculM) {
    	this.a = A;
    	this.b = B;
        double da = Math.sqrt(Math.pow(a.getPoint().getX()-calculM.getX(),2)+Math.pow(a.getPoint().getY()-calculM.getY(),2)); // attention par 0
        double db = Math.sqrt(Math.pow(b.getPoint().getX()-calculM.getX(),2)+Math.pow(b.getPoint().getY()-calculM.getY(),2)); // attention par 0
        double qa = this.a.q;
        double qb = this.b.q;
        double c = (double) 1 / (4 * Math.PI * 8.85419782e-12);
        v =  (c * (qa / 10*da + qb / db));
    }
    
    public double getV() {
        return this.v;
    }
    
    public Particule getA() {
        return this.a;
    }

    public Particule getB() {
        return this.b;
    }

    public Point getM() {
        return this.m;
    }
    
    public void setV(double v) {
    	this.v = v;
    }
    
    public void setA(Particule a) {
    	this.a = a;
    }
    
    public void setB(Particule b) {
    	this.b = b;
    }
    
    public void setM(Point m) {
    	this.m = m;
    }
}
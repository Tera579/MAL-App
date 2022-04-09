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
public class Particule {

    protected double q; // charge 
    private Point point; 

    public Particule(double x, double y, String name, double q) {
        this.q = q;
        point = new Point(x,y,name);
    }
    public Particule(String name) {
        point = new Point(0,0,name);
    }
    

    public double getQ() {
        return (this.q);
    }

    public void setQ(double q) {
        this.q = q;
    }  
        
    public Point getPoint() {
        return this.point;    
    }
    public void setPoint(Point point) {
    	this.point = point;
    }
    
}
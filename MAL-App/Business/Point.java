package Business;

/**
 *
 * @author Elève
 */
public class Point {

    private double x;
    private double y;
    private String name;

    public Point(String name) {
    	this.x = 0;
    	this.y = 0;
        this.name = name;
    }
    public Point(double x, double y, String name) {
    	this.x = x;
    	this.y = y;
    	this.name = name;
    }
    
    public double getX() {
        return (this.x);
    }

    public double getY() {
        return (this.y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
        return (this.name);
    }
}
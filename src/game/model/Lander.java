package game.model;

import loader.PropertiesLoader;

import java.util.Properties;

public class Lander {
    private Point position;
    private double fuel;
    private Vector velocityVector;
    private boolean crashed;
    private boolean landed;


    public Lander(String configurationFileName){
        Properties properties = PropertiesLoader.loadFromLocalFile("/maps" + configurationFileName);

        this.fuel = Double.parseDouble(properties.getProperty("FUEL"));
        this.position = new Point(properties.getProperty("START_POS"));
        this.velocityVector = new Vector(0,0);

        this.crashed = false;
        this.landed = false;
    }

    public Point[] returnCornersOfLander() {
        Point[] points = new Point[3];
        double x = this.position.getX();
        double y = this.position.getY();

        int xCenter = (int)x;
        int yCenter = (int)(y);

        // lewa noga
        points[0] = new Point(x - 10,y - 15);
        // srodkowa noga
        points[1] = new Point(x,y - 15);
        // prawa noga
        points[2] = new Point(x + 10,y - 15);



        return points;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public Vector getVelocityVector() {
        return velocityVector;
    }

    public void setVelocityVector(Vector velocityVector) {
        this.velocityVector = velocityVector;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }
}

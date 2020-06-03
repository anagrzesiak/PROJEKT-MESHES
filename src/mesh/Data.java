package mesh;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    public ArrayList<Point> nodes;
    public ArrayList<Point> points;
    public ArrayList<Edge> edges;
    public ArrayList<Element> elements;
    public ArrayList<Particle> particles;
    public int height=500;
    public int width=500;
    public int x0=0;
    public int y0=0;
    public int tolerance=1;
    public int particleSize = 10;
    public BufferedImage pic;
    public Node rootNode;
    public Data(){
        points=new ArrayList<>();
        nodes=new ArrayList<>();
        edges=new ArrayList<>();
        elements=new ArrayList<>();
        particles=new ArrayList<>();
    }
}

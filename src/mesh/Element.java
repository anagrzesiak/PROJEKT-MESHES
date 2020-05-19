package mesh;

import java.io.Serializable;

public class Element implements Serializable {
    public Point p1, p2;
    public Element(Point p1, Point p2){
        this.p1=p1;
        this.p2=p2;
    }
}

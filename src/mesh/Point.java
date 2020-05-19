package mesh;

import java.io.Serializable;

public class Point implements Serializable {
    public int x, y, id;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
        this.id=0;
    }
}

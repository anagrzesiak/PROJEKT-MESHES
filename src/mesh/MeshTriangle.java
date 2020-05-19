package mesh;

import java.io.Serializable;

public class MeshTriangle implements Serializable {
    Data d;
    public MeshTriangle(Data d){
        this.d=d;
    }

    public void generateMesh() {
        d.elements.clear();
        d.nodes.clear();
        Point start = d.points.get(0);
        int lengthX = d.width / d.x0;
        int lengthY = d.height / d.y0;

        for(int i = 0; i <= d.x0; i++) {
            for (int j = 0; j <= d.y0; j++) {
                d.nodes.add(new Point(start.x + i * lengthX, start.y + j * lengthY));
            }
        }

        for (int i = 0; i <=d.x0; i++) {
            for (int j = 0; j <= d.y0; j++) {
                if(i<d.x0 && j<d.y0) {
                    d.elements.add(new Element(new Point((start.x + (i * lengthX)), (start.y + (j * lengthY))), new Point(start.x + ((i + 1) * lengthX), (start.y + (j * lengthY)))));
                    d.elements.add(new Element(new Point((start.x + (i * lengthX)), (start.y + (j * lengthY))), new Point(start.x + (i * lengthX), (start.y + ((j + 1) * lengthY)))));
                    d.elements.add(new Element(new Point((start.x + (i * lengthX)), (start.y + (j * lengthY))), new Point(start.x + ((i + 1) * lengthX), (start.y + ((j + 1) * lengthY)))));
                }
            }
        }
        d.elements.add(new Element(new Point(start.x+d.width, start.y), new Point(start.x + d.width, (start.y+d.height))));
        d.elements.add(new Element(new Point(start.x, start.y+d.height), new Point(start.x+d.width, (start.y+d.height))));
    }
}

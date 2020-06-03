package mesh;

import java.util.ArrayList;
import java.util.List;

public class PixelObject {
   //private static final AtomicInteger count = new AtomicInteger(0);
    int color;
    public int x, y;
    public int id;
    public static List<PixelObject> one = new ArrayList<PixelObject>();
    public static List<PixelObject> all = new ArrayList<PixelObject>();

    public PixelObject(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        id=-(color)/2;
    }

    public static void output(PixelObject[][] p, int length, int width) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                PixelObject po = p[i][j];
                System.out.println("PIXEL! IDENTIFIER: " + po.id);
            }
        }
    }

}


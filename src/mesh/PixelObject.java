package mesh;

import sample.Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PixelObject {
    int color;
    public int x, y, identifier;
    public static List<PixelObject> one = new ArrayList<PixelObject>();


    public PixelObject(int x, int y, int color){
        this.x=x;
        this.y=y;
        this.color=color;
        identifier=-(color)/2;
    }

    public static ArrayList<PixelObject> output(PixelObject[][] p, int length, int width){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                PixelObject po = p[i][j];
                if (po.identifier == 6779600) {
                    one.add(po);
                    //System.out.println("added");
                }
                System.out.println("PIXEL! IDENTIFIER: " +po.identifier);
            }
        }
        return (ArrayList<PixelObject>) one;
    }

    public ArrayList<PixelObject> getArr(){
        return (ArrayList<PixelObject>) one;
    }

    public void pack(PixelObject uh){
        if (uh.identifier == 6779600) {
            one.add(uh);
            //System.out.println("added");
        }
    }

}

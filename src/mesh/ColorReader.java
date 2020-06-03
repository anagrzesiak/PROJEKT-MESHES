
package mesh;


import java.awt.image.BufferedImage;


public class ColorReader {
    public BufferedImage bf;
    public ColorReader(){

    }
    public static PixelObject[][] colors(BufferedImage bf){
        //Color[][] colors = new Color[bf.getWidth()][bf.getHeight()];
        PixelObject[][] arr = new PixelObject[bf.getWidth()][bf.getHeight()];

        for (int i = 0; i < bf.getWidth(); i++) {
            for (int j = 0; j < bf.getHeight(); j++) {
                //colors[x][y] = new Color(bf.getRGB(x, y));
                arr[i][j]=new PixelObject(i, j, bf.getRGB(i, j));
            }
        }
        return arr;
    }
}


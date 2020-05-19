package mesh;

import java.awt.*;
import java.util.Random;

public class QuadTree {
    Data d;
    Random direction;
    ColorReader cr = new ColorReader();

    public QuadTree(Data d) {
        this.d = d;
        direction = new Random(System.currentTimeMillis());
    }

    public boolean checkk(Part p) {
        int check=0;
        PixelObject[][] arr = new PixelObject[d.pic.getWidth()][d.pic.getHeight()];
        for (int i = p.origin.x; i < (p.origin.x + p.width); i++) {
            for (int j = p.origin.y; j < (p.origin.y + p.height); j++) {
                //Color current = new Color(d.pic.getRGB(i, j));
                arr[i][j] = new PixelObject(i, j, d.pic.getRGB(i, j));
                if(arr[i][j].identifier == 8320877){
                    check++;
                }
            }
        }
        return check > 0;
    }

    public String check(Part p) {

        boolean mixed = false;
        boolean black = false;
        boolean white = false;

        String what = null;


        for (int i = p.origin.x; i < (p.origin.x + p.width); i++) {
            for (int j = p.origin.y; j < (p.origin.y + p.height); j++) {
                Color current = new Color(d.pic.getRGB(i, j));
                if (current.getRed() == 0 && current.getGreen() == 0 && current.getBlue() == 0) {
                    black = true;
                    what = "black";
                }
                if (current.getRed() == 255 && current.getGreen() == 255 && current.getBlue() == 255) {
                    white = true;
                    what = "white";
                }
                if (black && white) {
                    mixed = true;
                    what = "mixed";
                }
            }
        }
        return what;
    }

    public Boolean checkInRange(Part p, Particle pa){
        return ((pa.x > p.origin.x) && (pa.x < p.origin.x+p.width) && (pa.y > p.origin.y) && (pa.y < p.origin.y+p.height));
    }

    void splitColor(Node root) {
        if (checkk(root.p)) {
            if (root.p.width % 2 != 0) {
                root.p.width = root.p.width - 1;
            }
            if (root.p.height % 2 != 0) {
                root.p.height = root.p.height - 1;
            }
            int width = root.p.width / 2;
            int height = root.p.height / 2;
            if (width > d.tolerance && height > d.tolerance) {
                Node n1 = new Node(new Part(new Point(root.p.origin.x, root.p.origin.y), width, height), null, null, null, null);
                Node n2 = new Node(new Part(new Point(root.p.origin.x + width, root.p.origin.y), width, height), null, null, null, null);
                Node n3 = new Node(new Part(new Point(root.p.origin.x, root.p.origin.y + height), width, height), null, null, null, null);
                Node n4 = new Node(new Part(new Point(root.p.origin.x + width, root.p.origin.y + height), width, height), null, null, null, null);
                root.n1 = n1;
                root.n2 = n2;
                root.n3 = n3;
                root.n4 = n4;
                splitColor(n1);
                splitColor(n2);
                splitColor(n3);
                splitColor(n4);
                if (checkk(n1.p))
                    splitColor(n1);
                if (checkk(n2.p))
                    splitColor(n2);
                if (checkk(n3.p))
                    splitColor(n3);
                if (checkk(n4.p))
                    splitColor(n4);
            }
        }
    }


    void split(Node root) {
        if (check(root.p).equals("mixed")) {
            if (root.p.width % 2 != 0) {
                root.p.width = root.p.width - 1;
            }
            if (root.p.height % 2 != 0) {
                root.p.height = root.p.height - 1;
            }
            int width = root.p.width / 2;
            int height = root.p.height / 2;
            if (width > d.tolerance && height > d.tolerance) {
                Node n1 = new Node(new Part(new Point(root.p.origin.x, root.p.origin.y), width, height), null, null, null, null);
                Node n2 = new Node(new Part(new Point(root.p.origin.x + width, root.p.origin.y), width, height), null, null, null, null);
                Node n3 = new Node(new Part(new Point(root.p.origin.x, root.p.origin.y + height), width, height), null, null, null, null);
                Node n4 = new Node(new Part(new Point(root.p.origin.x + width, root.p.origin.y + height), width, height), null, null, null, null);
                root.n1 = n1;
                root.n2 = n2;
                root.n3 = n3;
                root.n4 = n4;
                split(n1);
                split(n2);
                split(n3);
                split(n4);
                if (check(n1.p).equals("mixed"))
                    split(n1);
                if (check(n2.p).equals("mixed"))
                    split(n2);
                if (check(n3.p).equals("mixed"))
                    split(n3);
                if (check(n4.p).equals("mixed"))
                    split(n4);
            }
        }
    }

    void split2(Node root) {
        if (check(root.p).equals("black")) {
            if (root.p.width % 2 != 0) {
                root.p.width = root.p.width - 1;
            }
            if (root.p.height % 2 != 0) {
                root.p.height = root.p.height - 1;
            }
            int width = root.p.width / 2;
            int height = root.p.height / 2;
            if (width > d.tolerance && height > d.tolerance) {
                Node n1 = new Node(new Part(new Point(root.p.origin.x, root.p.origin.y), width, height), null, null, null, null);
                Node n2 = new Node(new Part(new Point(root.p.origin.x + width, root.p.origin.y), width, height), null, null, null, null);
                Node n3 = new Node(new Part(new Point(root.p.origin.x, root.p.origin.y + height), width, height), null, null, null, null);
                Node n4 = new Node(new Part(new Point(root.p.origin.x + width, root.p.origin.y + height), width, height), null, null, null, null);
                root.n1 = n1;
                root.n2 = n2;
                root.n3 = n3;
                root.n4 = n4;
                split(n1);
                split(n2);
                split(n3);
                split(n4);
                if (check(n1.p).equals("black"))
                    split(n1);
                if (check(n2.p).equals("black"))
                    split(n2);
                if (check(n3.p).equals("black"))
                    split(n3);
                if (check(n4.p).equals("black"))
                    split(n4);
            }
        }
    }


    public void buildTree() {
        int width = 700;
        int height = 500;
        Part mainPart = new Part(new Point(0, 0), width, height);
        d.rootNode = new Node(mainPart, null, null, null, null);
        split(d.rootNode);
    }

    public void buildTreeC() {
        int width = d.pic.getWidth();
        int height = d.pic.getHeight();
        Part mainPart = new Part(new Point(0, 0), width, height);
        d.rootNode = new Node(mainPart, null, null, null, null);
        splitColor(d.rootNode);
    }

    public void buildTree2() {
        int width = 700;
        int height = 500;
        Part mainPart = new Part(new Point(0, 0), width, height);
        d.rootNode = new Node(mainPart, null, null, null, null);
        split2(d.rootNode);
    }

    public void generateParticles(int amount){
        Random rand = new Random(System.currentTimeMillis());
        for(int i = 0; i < amount; i++){
            d.particles.add(new Particle(rand.nextInt(d.width), rand.nextInt(d.height)));
        }
    }
}





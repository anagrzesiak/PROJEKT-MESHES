package mesh;

import java.awt.*;
import java.util.ArrayList;

public class QuadTree {
    Data d;

    public QuadTree(Data d) {
        this.d = d;
    }


    public boolean checkk(Part p, int identifier) {
        int check=0;
        PixelObject[][] arr = new PixelObject[d.pic.getWidth()][d.pic.getHeight()];
        for (int i = p.origin.x; i < (p.origin.x + p.width); i++) {
            for (int j = p.origin.y; j < (p.origin.y + p.height); j++) {
                //Color current = new Color(d.pic.getRGB(i, j));
                arr[i][j] = new PixelObject(i, j, d.pic.getRGB(i, j));
                if(arr[i][j].id == identifier){
                    check++;
                }
            }
        }
        return (check>0);
    }

    public ArrayList<Integer> getIDs(Node root){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = root.p.origin.x; i < (root.p.origin.x + root.p.width); i++) {
            for (int j = root.p.origin.y; j < (root.p.origin.y + root.p.height); j++) {
                if(!(list.contains(-(d.pic.getRGB(i, j))/2))){
                    list.add(-(d.pic.getRGB(i, j))/2);
                }
            }
        }
       return list;
    }


    public boolean checkMeshAll(Part p) {
      return true;
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


    void splitColor(Node root, int id) {
        if (checkk(root.p, id)) {
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
                splitColor(n1, id);
                splitColor(n2, id);
                splitColor(n3, id);
                splitColor(n4, id);
                if (checkk(n1.p, id))
                    splitColor(n1, id);
                if (checkk(n2.p, id))
                    splitColor(n2, id);
                if (checkk(n3.p, id))
                    splitColor(n3, id);
                if (checkk(n4.p, id))
                    splitColor(n4, id);
            }
        }
    }

    void splitColorMeshAll(Node root) {
        if (checkMeshAll(root.p)) {
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
                splitColorMeshAll(n1);
                splitColorMeshAll(n2);
                splitColorMeshAll(n3);
                splitColorMeshAll(n4);
                if (checkMeshAll(n1.p))
                    splitColorMeshAll(n1);
                if (checkMeshAll(n2.p))
                    splitColorMeshAll(n2);
                if (checkMeshAll(n3.p))
                    splitColorMeshAll(n3);
                if (checkMeshAll(n4.p))
                    splitColorMeshAll(n4);
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


    public void buildTree() {
        int width = 700;
        int height = 500;
        Part mainPart = new Part(new Point(0, 0), width, height);
        d.rootNode = new Node(mainPart, null, null, null, null);
        split(d.rootNode);
    }

    public void buildTreeC(int id) {
        int width = d.pic.getWidth();
        int height = d.pic.getHeight();
        Part mainPart = new Part(new Point(0, 0), width, height);
        d.rootNode = new Node(mainPart, null, null, null, null);
        splitColor(d.rootNode, id);
    }


    public void buildTreeCMeshAll() {
        int width = d.pic.getWidth();
        int height = d.pic.getHeight();
        Part mainPart = new Part(new Point(0, 0), width, height);
        d.rootNode = new Node(mainPart, null, null, null, null);
        splitColorMeshAll(d.rootNode);
    }

}





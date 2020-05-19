package mesh;

import java.util.ArrayList;
import java.util.List;

public class QT {

    static class Node {
        int x, y, value;

        Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value; /* some data*/
        }
    }
    /* Using two points of Rectangular (Top,Left) & (Bottom , Right)*/
    public static class Boundry {
        public int getxMin() {
            return xMin;
        }

        public int getyMin() {
            return yMin;
        }

        public int getxMax() {
            return xMax;
        }

        public int getyMax() {
            return yMax;
        }

        public Boundry(int xMin, int yMin, int xMax, int yMax) {
            super();
            /*
             *  Storing two diagonal points
             */
            this.xMin = xMin;
            this.yMin = yMin;
            this.xMax = xMax;
            this.yMax = yMax;
        }

        public boolean inRange(int x, int y) {
            return (x >= this.getxMin() && x <= this.getxMax()
                    && y >= this.getyMin() && y <= this.getyMax());
        }

        int xMin, yMin, xMax, yMax;

    }

    public static class QuadTree {
        final int MAX_CAPACITY = 4;
        int level = 0;
        List<Node> nodes;
        QuadTree northWest = null;
        QuadTree northEast = null;
        QuadTree southWest = null;
        QuadTree southEast = null;
        Boundry boundry;

        public QuadTree(int level, Boundry boundry) {
            this.level = level;
            nodes = new ArrayList<Node>();
            this.boundry = boundry;
        }

        void split() {
            int xOffset = this.boundry.getxMin()
                    + (this.boundry.getxMax() - this.boundry.getxMin()) / 2;
            int yOffset = this.boundry.getyMin()
                    + (this.boundry.getyMax() - this.boundry.getyMin()) / 2;

            northWest = new QuadTree(this.level + 1, new Boundry(
                    this.boundry.getxMin(), this.boundry.getyMin(), xOffset,
                    yOffset));
            northEast = new QuadTree(this.level + 1, new Boundry(xOffset,
                    this.boundry.getyMin(), xOffset, yOffset));
            southWest = new QuadTree(this.level + 1, new Boundry(
                    this.boundry.getxMin(), xOffset, xOffset,
                    this.boundry.getyMax()));
            southEast = new QuadTree(this.level + 1, new Boundry(xOffset, yOffset,
                    this.boundry.getxMax(), this.boundry.getyMax()));

        }

        public void insert(int x, int y, int value) {
            if (!this.boundry.inRange(x, y)) {
                return;
            }

            Node node = new Node(x, y, value);
            if (nodes.size() < MAX_CAPACITY) {
                nodes.add(node);
                return;
            }
            // Exceeded the capacity so split it in FOUR
            if (northWest == null) {
                split();
            }

            // Check coordinates belongs to which partition
            if (this.northWest.boundry.inRange(x, y))
                this.northWest.insert(x, y, value);
            else if (this.northEast.boundry.inRange(x, y))
                this.northEast.insert(x, y, value);
            else if (this.southWest.boundry.inRange(x, y))
                this.southWest.insert(x, y, value);
            else if (this.southEast.boundry.inRange(x, y))
                this.southEast.insert(x, y, value);
            else
                System.out.printf("ERROR : Unhandled partition %d %d", x, y);
        }
    }
}

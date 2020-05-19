package mesh;

public class Node {
    public Part p;
    public Node n1, n2, n3, n4;

    public Node(Part p, Node n1, Node n2, Node n3, Node n4){
        this.p=p;
        this.n1=n1;
        this.n2=n2;
        this.n3=n3;
        this.n4=n4;
    }
}

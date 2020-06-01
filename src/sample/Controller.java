package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mesh.*;
import mesh.Point;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Controller implements Serializable {
    private final Data d = new Data();
    private Mesh m;
    @FXML
    private Button colorsButton;
    @FXML
    private Button pictureButton;
    @FXML
    private Button rhombButton;
    @FXML
    private Button trapezeButton;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField inputField1;
    @FXML
    private TextField inputField2;
    @FXML
    private TextField inputHeight;
    @FXML
    private TextField inputWidth;
    @FXML
    private Button triangleButton;
    @FXML
    private Button squareButton;
    @FXML
    private Canvas canvas;

    QuadTree q = new QuadTree(d);
    ColorReader c=new ColorReader();

    public void clickColors(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CHOOSE YOUR FIGHTER");
        alert.setHeaderText(":)");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("MESH ALL");
        ButtonType buttonTypeTwo = new ButtonType("MESH CHOSEN COLOR");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            colors1();
        } else if (result.get() == buttonTypeTwo) {
            colors2();
        }
    }

    public void colors1() throws IOException {
        drawAndBuild();
        q.buildTreeCMeshAll();
        drawSplitColorMeshAll(d.rootNode);
        saveAsPng();
    }

    public void colors2() throws IOException {
        drawAndBuild();
        q.buildTreeC();
        drawSplitColor(d.rootNode);
        saveAsPng();
    }

    private void drawAndBuild() throws IOException {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        File bmpFile = new File("image.bmp");
        c.bf = ImageIO.read(bmpFile);
        convertToFxImage(c.bf);
        gc.drawImage(convertToFxImage(c.bf), 0 , 0);
        PixelObject o=new PixelObject(0, 0, 0);
        ArrayList<PixelObject> arr = PixelObject.output(ColorReader.colors(c.bf), c.bf.getWidth(), c.bf.getHeight());
        System.out.println(arr.size());
        d.pic=c.bf;
    }


    @FXML
    public void saveAsPng() {
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        File file = new File("blagam.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.print("nwm o co chodzi");
        }
    }



    public void tryRhomb(ActionEvent event){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        add();
        MeshTriangle mesh = new MeshTriangle(d);
        d.height=Integer.parseInt(inputHeight.getText());
        d.width=Integer.parseInt(inputWidth.getText());
        d.x0 = Integer.parseInt(inputField1.getText());
        d.y0 = Integer.parseInt(inputField2.getText());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(4);
        gc.strokeLine(20, 20, 20+(d.width/2), 20);
        gc.strokeLine(20+(d.width/2), 20, 20+d.width, 20+d.height);
        gc.strokeLine(20+d.width, 20+d.height, 20+(d.width/2), 20+d.height);
        gc.strokeLine(20+(d.width/2), 20+d.height, 20, 20);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        mesh.generateMesh();
        draw();
    }


    public void tryTrapeze(ActionEvent event){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        add();
        MeshTriangle mesh = new MeshTriangle(d);
        d.height=Integer.parseInt(inputHeight.getText());
        d.width=Integer.parseInt(inputWidth.getText());
        d.x0 = Integer.parseInt(inputField1.getText());
        d.y0 = Integer.parseInt(inputField2.getText());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(4);
        gc.strokeLine(20+(d.width/5), 20, 20+(4*(d.width/5)), 20);
        gc.strokeLine(20+(4*(d.width/5)), 20, 20+d.width, 20+d.height);
        gc.strokeLine(20+d.width, 20+d.height, 20, 20+d.height);
        gc.strokeLine(20, 20+d.height, 20+(d.width/5), 20);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        mesh.generateMesh();
        draw();
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }


    public void setPictureButton(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        add();
        try {
            d.pic = ImageIO.read(new File("bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gc.drawImage(convertToFxImage(d.pic), 0, 0);
        q.buildTree();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(":)");
        alert.setHeaderText("CHOOSE OPTION");
        alert.setContentText("Do you want to mesh it with...?");

        ButtonType buttonTypeOne = new ButtonType("RECTANGLES");
        ButtonType buttonTypeTwo = new ButtonType("TRIANGLES");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            gc.drawImage(convertToFxImage(d.pic), 0 , 0);
            drawSplit(d.rootNode);
        } else if (result.get() == buttonTypeTwo) {
            gc.drawImage(convertToFxImage(d.pic), 0 , 0);
            drawSplitT(d.rootNode);
        }
    }

    void drawSplitColor(Node root){
        getGC(root);
        if(root.n1 != null && (q.checkk(root.n1.p)))
            drawSplitColor(root.n1);
        if(root.n2 != null && (q.checkk(root.n2.p)))
            drawSplitColor(root.n2);
        if(root.n3 != null && (q.checkk(root.n3.p)))
            drawSplitColor(root.n3);
        if(root.n4 != null && (q.checkk(root.n4.p)))
            drawSplitColor(root.n4);
    }

    void drawSplitColorMeshAll(Node root) {
        getGC(root);
        if (root.n1 != null && (q.checkMeshAll(root.n1.p)))
            drawSplitColorMeshAll(root.n1);
        if (root.n2 != null && (q.checkMeshAll(root.n2.p)))
            drawSplitColorMeshAll(root.n2);
        if (root.n3 != null && (q.checkMeshAll(root.n3.p)))
            drawSplitColorMeshAll(root.n3);
        if (root.n4 != null && (q.checkMeshAll(root.n4.p)))
            drawSplitColorMeshAll(root.n4);
    }

    private void getGC(Node root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(0.5);
        strokeRect(root, gc);
    }

    private void strokeRect(Node root, GraphicsContext gc) {
        gc.strokeRect(root.p.origin.x, root.p.origin.y, root.p.width, root.p.height);
        gc.strokeLine(root.p.origin.x, root.p.origin.y, root.p.origin.x + root.p.width, root.p.origin.y + root.p.height);
    }

    void drawSplitT(Node root){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.HOTPINK);
        if((q.check(root.p).equals("mixed") || (q.check(root.p).equals("black"))))  {
            strokeRect(root, gc);
        }
        if(root.n1 != null)
            drawSplitT(root.n1);
        if(root.n2 != null)
            drawSplitT(root.n2);
        if(root.n3 != null)
            drawSplitT(root.n3);
        if(root.n4 != null)
            drawSplitT(root.n4);
    }

    void drawSplit(Node root){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.HOTPINK);
        gc.strokeRect(root.p.origin.x, root.p.origin.y, root.p.width, root.p.height);
        if(root.n1 != null && (q.check(root.n1.p).equals("mixed") || q.check(root.n1.p).equals("black")))
            drawSplit(root.n1);
        if(root.n2 != null && (q.check(root.n2.p).equals("mixed") || q.check(root.n2.p).equals("black")))
            drawSplit(root.n2);
        if(root.n3 != null && (q.check(root.n3.p).equals("mixed") || q.check(root.n3.p).equals("black")))
            drawSplit(root.n3);
        if(root.n4 != null && (q.check(root.n4.p).equals("mixed") || q.check(root.n4.p).equals("black")))
            drawSplit(root.n4);
    }



    public void d(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(4);
        d.height=Integer.parseInt(inputHeight.getText());
        d.width=Integer.parseInt(inputWidth.getText());
        gc.strokeRect(20, 20, d.width, d.height);
    }

    public void add(){
        d.points.add(new Point(20, 20));
    }

    public void draw(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        for(Point p : d.nodes){
            gc.strokeOval((p.x-2), (p.y-2), 2, 2);
        }

        gc.setStroke(Color.GRAY);
        for(Element e : d.elements){
            gc.strokeLine(e.p1.x, e.p1.y, e.p2.x, e.p2.y);
        }
    }

    public void clickSquare(ActionEvent event){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        add();
        Mesh mesh=new Mesh(d);
        d.height=Integer.parseInt(inputHeight.getText());
        d.width=Integer.parseInt(inputWidth.getText());
        d.x0 = Integer.parseInt(inputField1.getText());
        d.y0 = Integer.parseInt(inputField2.getText());
        mesh.generateMesh();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        d();
        draw();
    }


    public void clickTriangle(ActionEvent event) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        add();
        MeshTriangle mesh = new MeshTriangle(d);
        d.height = Integer.parseInt(inputHeight.getText());
        d.width = Integer.parseInt(inputWidth.getText());
        d.x0 = Integer.parseInt(inputField1.getText());
        d.y0 = Integer.parseInt(inputField2.getText());
        mesh.generateMesh();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        d();
        draw();
    }

    public void clickRefresh(ActionEvent event){
        clean();
    }

    public void clean(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        inputField2.clear();
        inputField1.clear();
        inputHeight.clear();
        inputWidth.clear();
    }

    public void clickImport(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("IMPORT");
        alert.setHeaderText(":)");
        alert.setContentText("What do you want to import.");

        ButtonType buttonTypeOne = new ButtonType("simple meshes");
        ButtonType buttonTypeTwo = new ButtonType("png");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            try {
                d.elements=new ArrayList<>();
                FileInputStream fis = new FileInputStream("meshes.csv");
                ObjectInputStream in = new ObjectInputStream(fis);
                d.elements = (ArrayList<Element>) in.readObject();
                fis.close();
                in.close();
                d.points=new ArrayList<>();
                FileInputStream fis1 = new FileInputStream("points.csv");
                ObjectInputStream in1 = new ObjectInputStream(fis1);
                d.points = (ArrayList<Point>) in1.readObject();
                fis1.close();
                in1.close();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                draw();
            }

            catch (IOException I) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error Dialog");
                a.setHeaderText("Look, an Error Dialog");
                a.setContentText("CANNOT IMPORT - ERROR LOADING FILE");

                a.showAndWait();

            }
        } else if (result.get() == buttonTypeTwo) {
            try {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                c.bf = ImageIO.read(new File("blagam.png"));
                convertToFxImage(c.bf);
                gc.drawImage(convertToFxImage(c.bf), 0 , 0);
            }
            catch (IOException I) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error Dialog");
                a.setHeaderText("Look, an Error Dialog");
                a.setContentText("CANNOT IMPORT - ERROR LOADING FILE");

                a.showAndWait();

            }
        }
    }

    public void clickExport(ActionEvent event) throws IOException {
        FileOutputStream fos = new FileOutputStream("meshes.csv");
        ObjectOutputStream out = new ObjectOutputStream(fos);

        out.writeObject(d.elements);
        out.flush();
        out.close();

        FileOutputStream fos1 = new FileOutputStream("points.csv");
        ObjectOutputStream out1 = new ObjectOutputStream(fos1);

        out1.writeObject(d.points);
        out1.flush();
        out1.close();

        exportButton.setVisible(true);
    }
}

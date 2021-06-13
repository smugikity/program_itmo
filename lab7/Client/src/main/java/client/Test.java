package client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import lab5.legacy.Person;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Test extends Application {
    @FXML
    public void start(final Stage stage) {
        Canvas canvas = new Canvas(1200,600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        System.out.println("bruh1");

        gc.setFill(Color.RED);
        gc.fillText("NIGGA1",30,30,50);
        gc.setTransform(new Affine(Transform.translate(50,50)));
        gc.fillText("NIGGA2",30,30,50);
        gc.translate(30,30);
        gc.fillText("NIGGA2.5",30,30,50);
        gc.restore();
        gc.translate(-30,-30);
        gc.fillText("NIGGA3",30,30,50);
        gc.setTransform(new Affine(Transform.translate(0,0)));
        gc.fillText("NIGGA4",30,30,50);

        gc.getCanvas().setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getX()+" "+mouseEvent.getY());
        });

        //new Cartesian(gc,new HashSet<>(),20,20,1140,550,0,300,300,300,400);

        StackPane layout = new StackPane(canvas);
        stage.setTitle("test");
        stage.setScene(new Scene(layout, Color.rgb(35, 39, 50)));
        stage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }

    private class Cartesian {
        private GraphicsContext gc;
        private double x;
        private double y;
        private double xscale;
        private double yscale;
        private double xo;
        private double yo;
        private double width;
        private double height;
        private double angle;
        private double xlmax;
        private double xrmax;
        private double ylmax;
        private double yrmax;
        private Set<Person> idc = new HashSet<>();

        public Cartesian(GraphicsContext gc,HashSet<Person> people,double xo,double yo,double width,int height,double angle, double xlmax, double xrmax, double ylmax, double yrmax) {
            //scene width = 1140, scene height = 550
            //people.stream().map(Person::getId,Person::getCoordinates)

            this.gc = gc;
            gc.getCanvas().setOnMouseClicked(event -> {
                if (!idc.isEmpty())
                idc.stream().filter(c->event.getX()>c.getCol_coord_x()-5&&event.getX()<c.getCol_coord_x()+5&&event.getY()>c.getCol_coord_y()-5&&event.getY()<c.getCol_coord_y()+5).forEach(System.out::println);

                System.out.println("mouse clicked");
                //people.add();
                //addNode(new Person(40,"lmao",new Coordinates(15,259),30f,30l, lab5.legacy.Color.BLACK, Country.GERMANY,new Location(5d,5l,5d,"dark")));
            });
            this.xo = xo;
            this.yo = yo;
            this.width = width;
            this.height = height;
            this.angle = angle;
            this.xlmax = xlmax;
            this.ylmax = ylmax;
            this.xrmax = xrmax;
            this.yrmax = yrmax;



            //load();

        }
        private void load() {
            y = yrmax/(yrmax+ylmax)*height+yo;
            x = xlmax/(xlmax+xrmax)*width+xo;
            if ((xlmax+xrmax)/width>(ylmax+yrmax)/height) {
                xscale = drawArrow(x,y,angle,width,xlmax,xrmax);
                //System.out.println((xrmax+xlmax)/width*height*ylmax/(ylmax+yrmax)+" "+ylmax);
                double ylmax2 = (xrmax+xlmax)/width*height*ylmax/(ylmax+yrmax);
                double yrmax2 = (xrmax+xlmax)/width*height*yrmax/(ylmax+yrmax);
                ylmax = ylmax2; yrmax = yrmax2;
                yscale = drawArrow(x,y,angle-Math.PI/2,height,ylmax2,yrmax2);

            } else {
                yscale = drawArrow(x,y,angle-Math.PI/2,height,ylmax,yrmax);
                double xlmax2 = (yrmax+ylmax)/height*width*xlmax/(xrmax+xlmax);
                double xrmax2 = (yrmax+ylmax)/height*width*xrmax/(xrmax+xlmax);
                xlmax = xlmax2; xrmax = xrmax2;
                xscale = drawArrow(x,y,angle,width,xlmax2,xrmax2);
            }
            idc.forEach(this::drawNode);
        }

        public void addNode(Person p) {
            idc.add(p);
            drawNode(p);
        }

        public void drawNode(Person p){
            gc.setFill(Color.rgb(((p.getOwner_id()-p.getOwner_id()%16)/16%4+1)*60,((p.getOwner_id()-p.getOwner_id()%4)/4%4+1)*60,(p.getOwner_id()%4+1)*60));
            gc.setTransform(new Affine(Transform.translate(0,0)));
            gc.fillRect(x+p.getCol_coord_x()/(xlmax+xrmax)*width-5,y-p.getCol_coord_y()/(ylmax+yrmax)*height-p.getHeight()/2,10,p.getHeight());
        }

        public void removeNode(Long id) {
            idc = idc.stream().filter(c->c.getId()!=id).collect(Collectors.toSet());
            load();
        }

        public double drawArrow(double x1, double y1, double angle, double len, double lmax, double rmax) {
            double ARR_SIZE = 4;
            gc.setFill(Color.BLACK);

            double max = rmax+lmax;
            int unit = (int) Math.pow(10,Math.round(Math.log10(max))-1);
            double scale = unit/max*len;

            Transform transform = Transform.translate(x1,y1);
            transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle),0,0));
            gc.setTransform(new Affine(transform));

            gc.strokeLine(-lmax/max*len,0,rmax/max*len,0);
            gc.fillPolygon(new double[]{rmax/max*len,rmax/max*len,rmax/max*len+1.5*ARR_SIZE}, new double[]{-ARR_SIZE,ARR_SIZE,0},3);
            for (double i=-unit*Math.floor(lmax/unit);i<rmax;i+=unit) {
                if (i==0d) continue;
                double x = (i/max)*len;
                gc.strokeLine(x,-5,x,5);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText(String.valueOf((int)i),x,15*(Math.abs(angle)>=Math.PI/4?-1:1),scale/1.5);
            }
            return scale;
        }
    }
}

package client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import lab5.legacy.Person;

import java.util.List;
import java.util.stream.Collectors;

public class Cartesian {
    private final GraphicsContext gc;
    private double x;
    private double y;
    private final double xo;
    private final double yo;
    private final double width;
    private final double height;
    private final double angle;
    private double xlmax;
    private double xrmax;
    private double ylmax;
    private double yrmax;
    private List<Person> idc;

    public GraphicsContext getGc() {
        return gc;
    }

    public Cartesian(GraphicsContext gc, List<Person> people, double xo, double yo, double width, int height, double angle) {
        //scene width = 1140, scene height = 550
        //System.out.println((-xlmax)+" "+xrmax+" "+(-ylmax)+" "+yrmax);
        this.idc = people;
        this.gc = gc;
        gc.getCanvas().setOnMouseClicked(event -> {
            if (!idc.isEmpty()) {
                StringBuilder s = new StringBuilder();
                for (Person c : idc) {
                    if (event.getX() > x + c.getCol_coord_x() / (xrmax + xlmax) * width - 5 && event.getX() < x + c.getCol_coord_x() / (xrmax + xlmax) * width + 5
                            && event.getY() > y - c.getCol_coord_y() / (ylmax + yrmax) * height - ((c.getHeight() < 20) ? 10 : (c.getHeight() / 2)) && event.getY() < y - c.getCol_coord_y() / (ylmax + yrmax) * height + ((c.getHeight() < 20) ? 10 : (c.getHeight() / 2))) {
                        s.append(c.toString());
                    }
                    Connection.getInstance().getMain().getTextArea().setText(s.toString());
                }
            }


            System.out.println("mouse clicked");
            //people.add();
            //addNode(new Person(40,"lmao",new Coordinates(15,259),30f,30l, lab5.legacy.Color.BLACK, Country.GERMANY,new Location(5d,5l,5d,"dark")));
        });
        this.xo = xo;
        this.yo = yo;
        this.width = width;
        this.height = height;
        this.angle = angle;
        load();
    }

    public void load() {
        gc.setTransform(new Affine(Transform.translate(0,0)));
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        xrmax = idc.stream().map(Person::getCol_coord_x).max(Double::compareTo).get();
        xlmax = Math.abs(idc.stream().map(Person::getCol_coord_x).min(Double::compareTo).get());
        yrmax = idc.stream().map(Person::getCol_coord_y).max(Integer::compareTo).get();
        ylmax = Math.abs(idc.stream().map(Person::getCol_coord_y).min(Integer::compareTo).get());
        xrmax = xrmax>100?xrmax*1.1:100;
        xlmax = xlmax>100?xlmax*1.1:100;
        yrmax = yrmax>100?yrmax*1.1:100;
        ylmax = ylmax>100?ylmax*1.1:100;

        y = yrmax/(yrmax+ylmax)*height+yo;
        x = xlmax/(xlmax+xrmax)*width+xo;

        if ((xlmax+xrmax)/width>(ylmax+yrmax)/height) {
            double ylmax2 = (xrmax + xlmax) / width * height * ylmax / (ylmax + yrmax);
            double yrmax2 = (xrmax + xlmax) / width * height * yrmax / (ylmax + yrmax);
            ylmax = ylmax2;
            yrmax = yrmax2;
        } else {
            double xlmax2 = (yrmax+ylmax)/height*width*xlmax/(xrmax+xlmax);
            double xrmax2 = (yrmax+ylmax)/height*width*xrmax/(xrmax+xlmax);
            xlmax = xlmax2; xrmax = xrmax2;
        }

        drawArrow(x,y,angle,width,xlmax,xrmax);
        drawArrow(x,y,angle-Math.PI/2,height,ylmax,yrmax);
        idc.forEach(this::drawNode);
    }

    public void addNode(Person p) {
        idc.add(p);
        drawNode(p);
    }

    public void drawNode(Person p){
        gc.setFill(Color.rgb(((p.getOwner_id()-p.getOwner_id()%16)/16%4+1)*60,((p.getOwner_id()-p.getOwner_id()%4)/4%4+1)*60,(p.getOwner_id()%4+1)*60));
        gc.setTransform(new Affine(Transform.translate(x,y)));
        //System.out.println("x " +p.getCol_coord_x()+" "+(p.getCol_coord_x()/(xlmax+xrmax))+" "+(p.getCol_coord_x()/(xlmax+xrmax)*width));
        //System.out.println("y "+p.getCol_coord_y()+" "+(p.getCol_coord_y()/(yrmax+ylmax))+" "+(p.getCol_coord_y()/(yrmax+ylmax)*height)+" "+(y-p.getCol_coord_y()/(ylmax+yrmax)*height-((p.getHeight()<20)?10:(p.getHeight()/2))));
        gc.fillRect(p.getCol_coord_x()/(xlmax+xrmax)*width-5,-p.getCol_coord_y()/(ylmax+yrmax)*height-((p.getHeight()<20)?10:(p.getHeight()/2)),10,p.getHeight()<20?20:p.getHeight());
    }

    public void removeNode(Long id) {
        idc = idc.stream().filter(c-> !c.getId().equals(id)).collect(Collectors.toList());
        load();
    }

    public void drawArrow(double x1, double y1, double angle, double len, double lmax, double rmax) {
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
    }
}
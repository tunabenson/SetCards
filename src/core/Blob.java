package core;

import java.awt.Point;
import java.util.*;

public class Blob {
    private ArrayList<Point> blob;
    private ArrayList<Point> edges;
    //private Point [] corners;
    private short cornerR;
    private short cornerG;
    private short cornerB;
    public String color;
    public Blob() {
        this.blob= new ArrayList<>();
        this.edges= new ArrayList<>();
        cornerR=(short)(Math.random()*256);
        cornerG=(short)(Math.random()*256);
        cornerB=(short)(Math.random()*256);
    }
    public Blob(ArrayList<Point> blob) {
        this.blob= blob;
    }
    public void add(Point p) {
        blob.add(p);

    }
    public ArrayList<Point> get(){
        return blob;
    }

    public int size() {
        return blob.size();
    }

    public int [] getColor(){
        return new int []{cornerR,cornerG,cornerB};
    }

    public static DImage highlight(DImage src, ArrayList<Blob> bList) {
        short [][] red= src.getRedChannel();
        short [][] blue= src.getBlueChannel();
        short [][] green= src.getGreenChannel();

        short [][] newRed= new short [red.length][red[0].length];
        short [][] newBlue= new short [red.length][red[0].length];
        short [][] newGreen= new short [red.length][red[0].length];

        for (Blob blob:bList) {
            for(Point p: blob.get()) {
                newRed[p.x][p.y]=red[p.x][p.y];
                newGreen[p.x][p.y]=green[p.x][p.y];
                newBlue[p.x][p.y]=blue[p.x][p.y];
            }
            blob.highlightCorner(newRed, newBlue, newGreen);
        }


        src.setColorChannels(newRed, newGreen, newBlue);
        return src;
    }





    private void order(){
        blob.sort(new Comparator<Point>() {
            @Override
            public int compare(Point n1, Point n2) {
                if (n1.y != n2.y) {
                    return Integer.compare(n1.y, n2.y);
                } else {
                    return Integer.compare(n1.x, n2.x);
                }
            }
        });
    }

    public void correctHoles(){
        order();
        for (int i = 1; i < blob.size(); i++) {
            Point p1= blob.get(i-1);
            Point p2= blob.get(i);
            if((p1.y==p2.y) && Math.abs(p2.x-p1.x)>1){
                for (int j = p1.x; j <p2.x; j++) {
                    blob.add(new Point(j, p1.y));
                }
            }
        }
        order();
    }



    private Point [] getCorners(){
        Point topLeft = blob.get(0);
        Point topRight = blob.get(0);
        Point bottomLeft = blob.get(0);
        Point bottomRight = blob.get(0);

        for (Point p : blob) {
            if (p.x <= bottomLeft.x && p.y <= bottomLeft.y) {
                bottomLeft = p;
            }
            if (p.x >= bottomRight.x && p.y <= bottomRight.y) {
                bottomRight = p;
            }
            if (p.x <= topLeft.x && p.y >= topLeft.y) {
                topLeft = p;
            }
            if (p.x >= topRight.x && p.y >= topRight.y) {
                topRight = p;
            }
        }
        return new Point[]{topLeft,topRight,bottomLeft,bottomRight};
    }


    public int [] getDimensions(){
//        Point [] corners=getCorners();
        int xMax=0, xMin=Integer.MAX_VALUE,yMax=0,yMin=Integer.MAX_VALUE;
        for (Point corner: blob) {
            xMax=Math.max(xMax, corner.x);
            xMin=Math.min(xMin,corner.x);
            yMax=Math.max(yMax, corner.y);
            yMin=Math.min(yMin,corner.y);
        }
        return new int []{xMin, xMax, yMin,yMax};
        }
    public short [] toVector(DImage source) {
        short[][] img = source.getBWPixelGrid();
        int [] dim=getDimensions();
        int size=(dim[1]-dim[0])*(dim[3]-dim[2]);
        System.out.println(size+" and "+ blob.size());
        short[] vect = new short[size];
        int i=0;
        for (int x = dim[0]; x < dim[1]; x++) {
            for (int y = dim[2]; y <dim[3] ; y++) {
                vect[i] = img[x][y];
                i++;
            }
        }
        return vect;
    }



    private void highlightCorner(short [][] red, short[][] blue, short[][] green) {
        for (Point p: edges) {
            for (int i =p.x-3; i < p.x+3; i++) {
                for (int j =p.y-3; j < p.y+3; j++) {
                    if(i>0 && i<red.length && j>0 && j<red[0].length) {
                        red[i][j]=cornerR;
                        blue[i][j]=cornerB;
                        green[i][j]=cornerG;
                    }
                }
            }
        }
    }



    @Deprecated
    public void findEdges() {
        edges = ConvexHulls.convexHull(blob); //finds prominent points
        for (int i = edges.size()-2; i >=0; i--) {
            Point p1=edges.get(i+1);
            Point p2=edges.get(i);
            double [] fx=getFx(p1, p2);
            if(Double.isInfinite(fx[0])) {
                if(p1.y>p2.y) {
                    for (int j = p1.y; j >=p2.y; j--) {
                        edges.add(new Point((int)fx[1],j));
                    }
                }
                else {
                    for (int j = p2.y; j >= p1.y; j--) {
                        edges.add(new Point((int)fx[1],j));
                    }
                }
            }

            if(fx[0]==0) {
                if(p1.x>p2.x) {
                    for (int j = p1.x; j >=p2.x; j--) {
                        edges.add(new Point(j,p1.y));
                    }
                }
                else {
                    for (int j = p2.x; j >= p1.x; j--) {
                        edges.add(new Point(j,p1.y));
                    }
                }
            }

            else {
                if(p1.x>p2.x) {
                    for (int j = p1.x; j >=p2.x; j--) {
                        edges.add(new Point(j,(int)((j*fx[0])+fx[1])));
                    }
                }
                else {
                    for (int j = p2.x; j >= p1.x; j--) {
                        edges.add(new Point(j, (int) ((j * fx[0]) + fx[1])));
                    }
                }
            }

        }
    }
    @Deprecated
    private double [] getFx(Point p1, Point p2) {
        if(p1.x-p2.x==0) { // edge case to deal with
            return new double []{Double.NEGATIVE_INFINITY, p1.x}; // flag vertical line
        }
        if(p1.y==p2.y) {
            return new double[] {0, p1.y};
        }
        double m=(double)(p1.y-p2.y)/(p1.x-p2.x);
        double b= p1.y-(m*p1.x);
        return new double [] {m,b};

    }
    @Deprecated
    public static void assignColors(ArrayList<Blob> bList, DImage original) {
        short [][] blueChannel=original.getBlueChannel();
        short [][] redChannel=original.getRedChannel();
        short [][] greenChannel=original.getGreenChannel();
        short [] red= new short [] {255,69,0};
        short [] green= new short [] {0,128,0};
        short [] purple= new short [] {148,0,211};

        for (Blob blob : bList) {
            int index=-1;
            ArrayList<Point> blobz=blob.get();
            double minDist=Double.MAX_VALUE;
            for (Point p: blobz) {
                short r=redChannel[p.x][p.y];
                short g=greenChannel[p.x][p.y];
                short b=blueChannel[p.x][p.y];
                double [] dists= { Utilities.distance(red[0],red[1],red[2],r,g,b)
                        ,Utilities.distance(purple[0],purple[1],purple[2],r,g,b)
                        ,Utilities.distance(green[0],green[1],green[2],r,g,b)};
                for (int i = 0; i < dists.length; i++) {
                    if(dists[i]<minDist) {
                        minDist=dists[i];
                        index=i;
                    }
                }
            }
            switch (index) {
                case 0: blob.color="red";break;
                case 1: blob.color="purple";break;
                case 2: blob.color="green";break;
            }


        }
    }
}




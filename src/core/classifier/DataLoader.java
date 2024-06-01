package core.classifier;

import core.Blob;
import core.DImage;
import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataLoader {
    private ArrayList<DataPoint> test;
    private int k;

    short [] v1;
    public DataLoader(int k, Blob b,DImage src) {
        int [] dimensions=b.getDimensions();
        this.v1=b.toVector(src);
        this.k=k;
        test= new ArrayList<>();
        try {
            Scanner s = new Scanner(new File("data/labels.csv"));
            while (s.hasNextLine()) {
                String[] line = s.nextLine().split(",");

                PImage temp=new PImage(ImageIO.read(new File("./data/final/final" + line[0])));
                //resize to fit correct dimensionality
                temp.resize(dimensions[1]-dimensions[0],dimensions[3]-dimensions[2]);
                //load image
                DImage img= new DImage(temp);

                test.add(new DataPoint(img.getBWPixelArray(), line[1]));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String classify() {
        if (test.isEmpty()) return "no training data";
        return getString(getMajority(getKnearest(v1)));
    }


    private int getMajority(DataPoint [] arr){
        int [] options= new int[26];
        for(DataPoint p: arr){
            options[getIndexingValue(p.getLabel())]++;
        }
        int max=Integer.MIN_VALUE, index=-1;
        for (int i = 0; i < options.length; i++) {
            if(max<options[i]){
                max=options[i];
                index=i;
            }
        }
        return index;
    }

    private String getString(int value){
        switch (value) {
            case 20:
                return "2 cir close";
            case 23:
                return "2 kite line";
            case 4:
                return "2 sqg open";
            case 6:
                return "1 cir line";
            case 1:
                return "1 sqg close";
            case 0:
                return "1 sqg open";
            case 8:
                return "1 kite close";
            case 22:
                return "2 cir line";
            case 11:
                return "3 sqg line";
            case 15:
                return "3 kite open";
            case 14:
                return "3 cir open";
            case 12:
                return "3 cir close";
            case 21:
                return "2 cir open";
            case 13:
                return "3 cir line";
            case 2:
                return "1 sqg line";
            case 17:
                return "3 kite close";
            case 16:
                return "3 kite line";
            case 19:
                return "2 sqg close";
            case 5:
                return "1 cir open";
            case 10:
                return "3 sqg close";
            case 7:
                return "1 kite line";
            case 3:
                return "1 cir close";
            case 9:
                return "3 sqg open";
            case 24:
                return "2 kite close";
            case 18:
                return "2 sqg line";
            case 25:
                return "2 kite open";
            default:
                return "Invalid number";
        }
    }
    private int getIndexingValue(String input){
        switch (input) {
            case "2 cir close": return 20;
            case "2 kite line": return 23;
            case "2 sqg open": return 4;
            case "1 cir line": return 6;
            case "1 sqg close": return 1;
            case "1 sqg open": return 0;
            case "1 kite close": return 8;
            case "2 cir line": return 22;
            case "3 sqg line": return 11;
            case "3 kite open": return 15;
            case "3 cir open": return 14;
            case "3 cir close": return 12;
            case "2 cir open": return 21;
            case "3 cir line": return 13;
            case "1 sqg line": return 2;
            case "3 kite close": return 17;
            case "3 kite line": return 16;
            case "2 sqg close": return 19;
            case "1 cir open": return 5;
            case "3 sqg close": return 10;
            case "1 kite line": return 7;
            case "1 cir close": return 3;
            case "3 sqg open": return 9;
            case "2 kite close": return 24;
            case "2 sqg line": return 18;
            case "2 kite open": return 25;
            default: return -1;
        }
    }

    private DataPoint[] getKnearest(short [] vector) {
        DataPoint[] points = new DataPoint[k + 1];
        points[0] = new DataPoint( vector, "NaN");
        for (int i = 1; i < points.length; i++) {
            double minDistance = Integer.MAX_VALUE;
            DataPoint low = null;
            for (DataPoint p : test) {
                double currDistance = distance(p.getVector(), vector);
                if (minDistance > currDistance && currDistance >= distance(vector, points[i - 1].getVector())) {
                    minDistance = currDistance;
                    low = p;
                }
            }
            points[i] = low;
        }
        return Arrays.copyOfRange(points, 1, points.length);
    }
    private double distance(short[] d1, short[] d2) {
        double sum=0;
        for (int i = 0; i < d1.length ; i++) {
            sum+= (d1[i]-d2[i])*(d1[i]-d2[i]);
        }
        return sum;
    }
}

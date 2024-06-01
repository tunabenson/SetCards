package core.classifier;

import core.DImage;

public class DataPoint {
    private short [] vector;
    private String label;
    DataPoint(short [] vector, String label){
        this.vector=vector;
        this.label=label;
    }

    public short[] getVector() {
        return vector;
    }

    public void setVector(short[] vector) {
        this.vector = vector;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

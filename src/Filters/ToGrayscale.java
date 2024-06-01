package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class ToGrayscale implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] bw = img.getBWPixelGrid();
        img.setPixels(bw);
        return img;
    }
}

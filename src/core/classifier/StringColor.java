package core.classifier;

import java.util.ArrayList;
import java.util.List;

public class StringColor {
    private static List<ColorName> colorList;

    public static void init() {
        colorList = initColorList();
    }

    private static List<ColorName> initColorList() {
        List<ColorName> colorList = new ArrayList<>();
        colorList.add(new ColorName("Red", 255, 0, 0));
        colorList.add(new ColorName("Green", 0, 255, 0));
        colorList.add(new ColorName("Blue", 0, 0, 255));
        colorList.add(new ColorName("Black", 0, 0, 0));
        colorList.add(new ColorName("White", 255, 255, 255));
        colorList.add(new ColorName("Gray", 128, 128, 128));
        colorList.add(new ColorName("Yellow", 255, 255, 0));
        colorList.add(new ColorName("Cyan", 0, 255, 255));
        colorList.add(new ColorName("Magenta", 255, 0, 255));
        colorList.add(new ColorName("Maroon", 128, 0, 0));
        colorList.add(new ColorName("Olive", 128, 128, 0));
        colorList.add(new ColorName("Navy", 0, 0, 128));
        colorList.add(new ColorName("Teal", 0, 128, 128));
        colorList.add(new ColorName("Purple", 128, 0, 128));
        colorList.add(new ColorName("Silver", 192, 192, 192));
        colorList.add(new ColorName("Lime", 0, 255, 0));
        colorList.add(new ColorName("Aqua", 0, 255, 255));
        colorList.add(new ColorName("Fuchsia", 255, 0, 255));
        colorList.add(new ColorName("Orange", 255, 165, 0));
        colorList.add(new ColorName("Brown", 165, 42, 42));
        colorList.add(new ColorName("Pink", 255, 192, 203));
        colorList.add(new ColorName("Gold", 255, 215, 0));
        colorList.add(new ColorName("Beige", 245, 245, 220));
        colorList.add(new ColorName("Lavender", 230, 230, 250));
        colorList.add(new ColorName("Turquoise", 64, 224, 208));
        colorList.add(new ColorName("Coral", 255, 127, 80));
        colorList.add(new ColorName("Salmon", 250, 128, 114));
        colorList.add(new ColorName("Khaki", 240, 230, 140));
        colorList.add(new ColorName("Plum", 221, 160, 221));
        colorList.add(new ColorName("Orchid", 218, 112, 214));
        colorList.add(new ColorName("Crimson", 220, 20, 60));
        colorList.add(new ColorName("Mint", 189, 252, 201));
        colorList.add(new ColorName("Lavender Blush", 255, 240, 245));
        colorList.add(new ColorName("Peach Puff", 255, 218, 185));
        colorList.add(new ColorName("Slate Blue", 106, 90, 205));
        colorList.add(new ColorName("Peru", 205, 133, 63));
        colorList.add(new ColorName("Sea Green", 46, 139, 87));
        colorList.add(new ColorName("Slate Gray", 112, 128, 144));
        colorList.add(new ColorName("Tomato", 255, 99, 71));
        colorList.add(new ColorName("Wheat", 245, 222, 179));
        colorList.add(new ColorName("Lime Green", 50, 205, 50));
        colorList.add(new ColorName("Hot Pink", 255, 105, 180));
        colorList.add(new ColorName("Light Blue", 173, 216, 230));
        colorList.add(new ColorName("Forest Green", 34, 139, 34));
        colorList.add(new ColorName("Sky Blue", 135, 206, 235));
        colorList.add(new ColorName("Dodger Blue", 30, 144, 255));
        colorList.add(new ColorName("Chocolate", 210, 105, 30));
        colorList.add(new ColorName("Rosy Brown", 188, 143, 143));
        colorList.add(new ColorName("Medium Orchid", 186, 85, 211));
        colorList.add(new ColorName("Dark Goldenrod", 184, 134, 11));
        colorList.add(new ColorName("Medium Sea Green", 60, 179, 113));
        colorList.add(new ColorName("Deep Pink", 255, 20, 147));
        colorList.add(new ColorName("Steel Blue", 70, 130, 180));
        colorList.add(new ColorName("Sienna", 160, 82, 45));
        colorList.add(new ColorName("Medium Purple", 147, 112, 219));
        colorList.add(new ColorName("Medium Violet Red", 199, 21, 133));
        colorList.add(new ColorName("Indian Red", 205, 92, 92));
        colorList.add(new ColorName("Royal Blue", 65, 105, 225));
        colorList.add(new ColorName("Fire Brick", 178, 34, 34));
        colorList.add(new ColorName("Dark Orange", 255, 140, 0));
        colorList.add(new ColorName("Dark Salmon", 233, 150, 122));
        colorList.add(new ColorName("Dark Orchid", 153, 50, 204));
        colorList.add(new ColorName("Dark Red", 139, 0, 0));
        colorList.add(new ColorName("Dark Magenta", 139, 0, 139));
        colorList.add(new ColorName("Dark Blue", 0, 0, 139));
        colorList.add(new ColorName("Dark Cyan", 0, 139, 139));
        colorList.add(new ColorName("Dark Slate Gray", 47, 79, 79));
        // Add more colors as needed
        return colorList;
    }

    public static String getNearestColorName(int [] rgb) {
        int r=rgb[0], g=rgb[1], b=rgb[2];
        ColorName nearestColor = null;
        double minDistance = Double.MAX_VALUE;

        for (ColorName color : colorList) {
            double distance = colorDistance(r, g, b, color);
            if (distance < minDistance) {
                minDistance = distance;
                nearestColor = color;
            }
        }

        return nearestColor != null ? nearestColor.getName() : "Unknown";
    }

    private static double colorDistance(int r, int g, int b, ColorName color) {
        int rDiff = r - color.getR();
        int gDiff = g - color.getG();
        int bDiff = b - color.getB();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

    private static class ColorName {
        private String name;
        private int r;
        private int g;
        private int b;

        public ColorName(String name, int r, int g, int b) {
            this.name = name;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public String getName() {
            return name;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }
    }
}


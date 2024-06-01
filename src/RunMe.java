import core.DisplayWindow;

public class RunMe {
    public static void main(String[] args) {
        // --== Load an image to filter ==--

        DisplayWindow.showFor("images/image5.jpg", 850, 850, "core.ToGrayScale");

        // --== Determine your input interactively with menus ==--
        // DisplayWindow.getInputInteractively(800,600);
    }
}

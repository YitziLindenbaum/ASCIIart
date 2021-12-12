package ascii_art;

import ascii_art.img_to_char.CharRenderer;

/**
 * Managing function of ascii art enterprise.
 */
public class Driver {

    /**
     * Main function to run.
     * @param args
     */
    public static void main(String[] args) {
        boolean[][] cs = CharRenderer.getImg('d', 16, "Ariel");
        CharRenderer.printBoolArr(cs);
    }
}

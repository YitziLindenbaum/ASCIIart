package ascii_art.img_to_char;

import image.Image;

public class BrightnessImgCharMatcher {

    private final Image img;
    private final String fontName;

    public BrightnessImgCharMatcher(Image img, String fontName) {

        this.fontName = fontName;
        this.img = img;
    }

    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        char[][] ret = {{'0'}};
        return ret;
    }

    private float getCharBrightness(char c) {
        int counter = 0;
        boolean[][] cs = CharRenderer.getImg(c, 16, fontName);
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                if (cs[row][col]) {
                    counter++;
                }
            }
        }
        return (float) counter / (16 * 16);
    }


    private char[][] convertImageToAscii() {
        char[][] ret = {{'0'}};
        return ret;
    }
}

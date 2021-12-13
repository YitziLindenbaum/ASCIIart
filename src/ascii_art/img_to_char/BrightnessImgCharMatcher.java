package ascii_art.img_to_char;

import image.Image;

import java.util.Comparator;

public class BrightnessImgCharMatcher {

    private class CharComparator implements Comparator<Character> {


        @Override
        public int compare(Character c1, Character c2) {

            float brightness1 = getCharBrightness(c1);
            float brightness2 = getCharBrightness(c2);

            if (brightness1 == brightness2) {
                return 0;
            }
            if (brightness1 > brightness2) {
                return 1;
            }
            return -1;
        }
    }

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

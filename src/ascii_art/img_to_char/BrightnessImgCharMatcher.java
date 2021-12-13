package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
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

    private Image img;
    private final String fontName;

    public BrightnessImgCharMatcher(String fontName) {
        this.fontName = fontName;
    }

    public BrightnessImgCharMatcher(Image img, String fontName) {
        this.fontName = fontName;
        this.img = img;
    }

    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        char[][] ret = {{'0'}};
        return ret;
    }

    private float[] getBrightnessArr(Character[] charSet) {
        float[] brightnessArr = new float[charSet.length];
        for (int i = 0; i < brightnessArr.length; i++) {
            brightnessArr[i] = getCharBrightness(charSet[i]);
        }
        return brightnessArr;
    }

    private float getCharBrightness(Character c) {
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

    private void sortCharSet(Character[] charSet) {
        Arrays.sort(charSet, new CharComparator());
    }

    private void normalizeBrightnessArray(float[] brightnessArr) {
        float minBrightness = brightnessArr[0];
        float maxBrightness = brightnessArr[brightnessArr.length - 1];

        for (int i = 0; i < brightnessArr.length; i++) {
            brightnessArr[i] = (brightnessArr[i] - minBrightness) / (maxBrightness - minBrightness);
        }
    }

    private static float getImgBrightness(Image image) {
        int counter = 0;
        float brightness = 0;
        for (Color pixel: image.pixels()) {
            brightness += (pixel.getRed() * 0.2126) + (pixel.getGreen() * 0.7152) + (pixel.getBlue() * .0722);
            counter++;
        }
        return brightness / (counter * 255);
    }


    private char[][] convertImageToAscii(int numCharsInRow, Character[] charSet) {
        int pixels = img.getWidth() / numCharsInRow;
        int counter = 0;
        char[][] asciiArt = new char[img.getHeight()/pixels][numCharsInRow];
        for (Image subImg: img.squareSubImagesOfSize(pixels)) {
            asciiArt[counter / numCharsInRow][counter % numCharsInRow] =
                findClosestCharacter(subImg, charSet);
            counter++;
        }
        return asciiArt;
    }

    private char findClosestCharacter(Image img, Character[] charSet) {
        sortCharSet(charSet);
        float[] charBrightnesses = getBrightnessArr(charSet);
        normalizeBrightnessArray(charBrightnesses);
        float imgBrightness = getImgBrightness(img);
        int stopIndex = 0;
        for (int i = 0; i < charSet.length; i++) {
            if (charBrightnesses[i] > imgBrightness) {
                stopIndex = i;
                break;
            }
        }
        if (stopIndex == 0) {
            return charSet[0];
        }
        if (charBrightnesses[stopIndex] - imgBrightness < imgBrightness - charBrightnesses[stopIndex - 1]) {
            return charSet[stopIndex];
        }
        return charSet[stopIndex - 1];
    }

    public static void main(String[] args) {
        Character[] test = {'a', 'b', 'c', 'd'};
        BrightnessImgCharMatcher tester = new BrightnessImgCharMatcher("Times New Roman");

        tester.sortCharSet(test);
        float[] result = tester.getBrightnessArr(test);
        tester.normalizeBrightnessArray(result);
        for (int i = 0; i < 4; i++) {
            System.out.println(result[i]);

        }
        Image image = Image.fromFile("board.jpeg");
        System.out.println(tester.getImgBrightness(image));
    }


}

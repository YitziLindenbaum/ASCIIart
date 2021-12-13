package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Object to match images to characters for ascii-art.
 */
public class BrightnessImgCharMatcher {

    private final String fontName;
    private final Image img;

    /**
     * Construct a new BrightnessImgCharMatcher object.
     * @param img Image to be converted.
     * @param fontName to use when generating ascii art.
     */
    public BrightnessImgCharMatcher(Image img, String fontName) {
        this.fontName = fontName;
        this.img = img;
    }

    /**
     * Find the most suitable characters to represent image.
     * @param numCharsInRow Number of charactes to put in row in ascii-art image.
     * @param charSet Set of characters from which to choose.
     * @return 2d array of characters, each inner array representing a row of the image in ascii-art.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        return convertImageToAscii(numCharsInRow, charSet);
    }

    /**
     * Gives the average pixel brightness of an image.
     * @param image
     * @return Avg brightness as number in [0,1].
     */
    private static float getImgBrightness(Image image) {
        int counter = 0;
        float brightness = 0;
        for (Color pixel: image.pixels()) {
            brightness += (pixel.getRed() * 0.2126) + (pixel.getGreen() * 0.7152) + (pixel.getBlue() * .0722);
            counter++;
        }
        return brightness / (counter * 255);
    }

    /**
     * Gives an array of the brightness levels of an array of characters.
     * @param charSet Set of characters.
     * @return Array in which each element is the brightness (as number in [0,1]) of the corresponding
     * element in the input character set.
     */
    private float[] getBrightnessArr(Character[] charSet) {
        float[] brightnessArr = new float[charSet.length];
        for (int i = 0; i < brightnessArr.length; i++) {
            brightnessArr[i] = getCharBrightness(charSet[i]);
        }
        return brightnessArr;
    }

    /**
     * Gives the brightness of a single character.
     * @param c
     * @return Character brightness.
     */
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

    /**
     * Sorts a set of characters in place in ascending order of brightness.
     * @param charSet
     */
    private void sortCharSet(Character[] charSet) {
        Arrays.sort(charSet, new CharComparator());
    }

    /**
     * Stretches array of brightnesses linearly - for use on SORTED character brightness array.
     * @param brightnessArr
     */
    private void normalizeBrightnessArray(float[] brightnessArr) {
        float minBrightness = brightnessArr[0];
        float maxBrightness = brightnessArr[brightnessArr.length - 1];

        for (int i = 0; i < brightnessArr.length; i++) {
            brightnessArr[i] = (brightnessArr[i] - minBrightness) / (maxBrightness - minBrightness);
        }
    }

    /**
     * Converts image (given in constructor) to 2d array of characters.
     * @param numCharsInRow
     * @param charSet
     * @return 2d array of corresponding characters.
     */
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

    /**
     * Finds the closest character in given set to the brightness of the given image.
     * @param img To be found a character to match.
     * @param charSet Array of chars from which to choose.
     * @return closest character in brightness.
     */
    private char findClosestCharacter(Image img, Character[] charSet) {
        sortCharSet(charSet);
        float[] charBrightnesses = getBrightnessArr(charSet);
        normalizeBrightnessArray(charBrightnesses);
        float imgBrightness = getImgBrightness(img);

        int stopIndex = -1;
        for (int i = 0; i < charSet.length; i++) {
            if (charBrightnesses[i] >= imgBrightness) {  // charSet is sorted by brightness, so either
                // current or previous character is the closest
                stopIndex = i;
                break;
            }
        }
        if (stopIndex == 0) {  // image is entirely black
            return charSet[0];
        }
        if (charBrightnesses[stopIndex] - imgBrightness < imgBrightness - charBrightnesses[stopIndex - 1]) {
            // current character is closer
            return charSet[stopIndex];
        }
        // previous character is closer
        return charSet[stopIndex - 1];
    }

    /**
     * Comparator to compare characters by brightness.
     */
    private class CharComparator implements Comparator<Character> {


        /**
         * Compare two characters by brightness.
         * @param c1
         * @param c2
         * @return -1, 0, or 1 as the first argument is less than, equal to, or greater than the second in
         * brightness.
         */
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


}

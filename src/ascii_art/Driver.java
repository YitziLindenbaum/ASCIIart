package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.CharRenderer;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.Arrays;

/**
 * Managing function of ascii art enterprise.
 */
public class Driver {

    /**
     * Main function to run.
     * @param args
     */
    public static void main(String[] args) {
        //Character[] test = {'a', 'b', 'd', 'c'};
        Image img = Image.fromFile("NetPic.jpg");
        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Ariel");
        Character[] charSet = { 'a', 'b', 'c', 'd', 'e', 'f', '`', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};
        var chars = charMatcher.chooseChars(128, charSet);
        HtmlAsciiOutput asciiOutput = new HtmlAsciiOutput("NetPic.html", "Ariel");
        asciiOutput.output(chars);
        }
    }


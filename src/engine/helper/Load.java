package engine.helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class Load {

    public static BufferedImage loadImage(String path) {
        BufferedImage img = null;
        try (InputStream is = Load.class.getResourceAsStream(path)) {
            if (is == null) throw new FileNotFoundException(path);
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
        return img;

    }
}

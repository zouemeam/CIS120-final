
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

//A singleton HashMap of type of game objects to appearance
//If the HashMap does not contain the appearance, it will try to look for
//the file with the same name
public class AppearanceResource {
    private static AppearanceResource singleton = new AppearanceResource();

    public static AppearanceResource get() {
        return singleton;
    }

    private HashMap<String, Appearance> appearances = new HashMap<String, Appearance>();

    public Appearance getAppearance(String type) {
        if (appearances.containsKey(type)) {
            return appearances.get(type);
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(type));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(type + " does not exist");
        }
        Appearance newType = new Appearance(img);
        appearances.put(type, newType);
        return newType;

    }
}

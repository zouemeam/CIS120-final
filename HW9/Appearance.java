
import java.awt.Graphics;
import java.awt.Image;

//Takes in an image and provides the functionality to either draw the image or change the image
//Can also return image height and width
public class Appearance {
    private Image img;

    public Appearance(Image image) {
        this.img = image;
    }

    public int getWidth() {
        return img.getWidth(null);
    }

    public int getHeight() {
        return img.getHeight(null);
    }

    public void display(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
    }

    public void setLook(Image img) {
        this.img = img;
    }
}

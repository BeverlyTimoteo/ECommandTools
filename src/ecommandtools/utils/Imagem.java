package ecommandtools.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Imagem {

    public BufferedImage toGrayScaleGraphics2D(BufferedImage image) throws IOException {
        BufferedImage imagemNova = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = imagemNova.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return imagemNova;
    }

    public BufferedImage toGrayScale(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color cor = new Color(image.getRGB(x, y));
                int i = (cor.getRed() + cor.getGreen() + cor.getBlue()) / 3;
                image.setRGB(x, y, new Color(i, i, i).getRGB());
            }
        }
        return image;
    }

}

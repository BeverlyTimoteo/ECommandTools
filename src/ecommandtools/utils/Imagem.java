package ecommandtools.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

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

    public BufferedImage getImage(File f) throws Exception {
        return ImageIO.read(f);
    }

    public String bufferedImageToBase64(BufferedImage imagem, String formatName) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ImageIO.write(imagem, formatName, os);
        os.flush();

        return Base64.getEncoder().encodeToString(os.toByteArray());
    }

    public BufferedImage base64ToBufferedImage(String imagem) throws Exception {
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(imagem));
        return ImageIO.read(in);
    }
}

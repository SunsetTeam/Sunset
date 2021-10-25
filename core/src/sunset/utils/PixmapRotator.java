package sunset.utils;

import arc.graphics.Pixmap;
import arc.util.Tmp;
import mma.type.pixmap.PixmapProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixmapRotator {
    public static Pixmap rotate(Pixmap image, float angle) {
        final int size = Math.max(image.width, image.height);

        Pixmap rotated = new Pixmap(size, size);
        PixmapProcessor.staticProcessor.drawCenter(rotated, image);
        Pixmap pixmap = rotated.copy();
        BufferedImage bufferedImage = new BufferedImage(size, size, Transparency.TRANSLUCENT);
        Graphics2D graphics = (Graphics2D) bufferedImage.createGraphics();
//        graphics.rotatedBufferImage(Math.toRadians(angle));
        pixmap.each((x, y) -> {
            Tmp.c1.set(pixmap.get(x, y));
            graphics.setColor(new java.awt.Color(Tmp.c1.r, Tmp.c1.g, Tmp.c1.b, Tmp.c1.a));
            graphics.fillRect(x, y, 1, 1);
        });
        graphics.dispose();
        BufferedImage rotatedBufferImage = rotate(bufferedImage, Math.toRadians(angle));
        bufferedImage = rotatedBufferImage;
//        try {
//            Fi out = resources.child("out").child("test.png");
//            out.mkdirs();
//            out.writePng(pixmap);
//            ImageIO.write(bufferedImage,"png", out.file());
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
        final int xoff = rotated.width / 2 - rotatedBufferImage.getWidth() / 2, yoff = rotated.width / 2 - rotatedBufferImage.getWidth() / 2;
        rotated.each((x, y) -> {
//            PixmapProcessor.staticProcessor.drawCenter();
            int argb = rotatedBufferImage.getRGB(x - xoff, y - yoff);
            java.awt.Color rgb = new java.awt.Color(argb);
//            rotated.set(x,y, Tmp.c1.set(rgb.getRed()/255f,rgb.getGreen()/255f,rgb.getBlue()/255f,rgb.getAlpha()/255f).a(1f).rgba());
            rotated.set(x, y, Tmp.c1.argb8888(argb).rgba());
        });
        return rotated;
    }

    private static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
}

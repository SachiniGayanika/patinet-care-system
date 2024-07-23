package medicalrecords;

import com.google.zxing.LuminanceSource;
import java.awt.image.BufferedImage;

public class BufferedImageLuminanceSource extends LuminanceSource {
    private final BufferedImage image;
    private final int[] cachedLuminances;

    public BufferedImageLuminanceSource(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;
        cachedLuminances = new int[image.getWidth() * image.getHeight()];
    }

    @Override
    public byte[] getMatrix() {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        // Convert ARGB to grayscale
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];
            int luminance = (int) ((0.2126 * ((pixel >> 16) & 0xFF)) +
                                   (0.7152 * ((pixel >> 8) & 0xFF)) +
                                   (0.0722 * (pixel & 0xFF)));
            cachedLuminances[i] = luminance;
        }

        return toByteArray(cachedLuminances);
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        if (row == null || row.length < image.getWidth()) {
            row = new byte[image.getWidth()];
        }

        int width = image.getWidth();
        image.getRGB(0, y, width, 1, cachedLuminances, 0, width);

        // Convert ARGB to grayscale
        for (int i = 0; i < width; i++) {
            int pixel = cachedLuminances[i];
            int luminance = (int) ((0.2126 * ((pixel >> 16) & 0xFF)) +
                                   (0.7152 * ((pixel >> 8) & 0xFF)) +
                                   (0.0722 * (pixel & 0xFF)));
            row[i] = (byte) luminance;
        }

        return row;
    }

    private static byte[] toByteArray(int[] luminances) {
        byte[] byteArray = new byte[luminances.length];
        for (int i = 0; i < luminances.length; i++) {
            byteArray[i] = (byte) luminances[i];
        }
        return byteArray;
    }
}

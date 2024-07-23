package medicalrecords;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MatrixToImageWriter {

    public static void writeToFile(BitMatrix matrix, String format, String filePath) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        File file = new File(filePath);
        ImageIO.write(image, format, file);
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Corrected line: use get method instead of g
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    public static void writeToPath(BitMatrix bitMatrix, String format, Path path) throws IOException {
        BufferedImage image = toBufferedImage(bitMatrix);
        File file = path.toFile();
        ImageIO.write(image, format, file);
    }
}

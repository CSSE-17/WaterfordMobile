package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Mahendra Tennakoon
 */
public class FileUtil {
    public boolean copyFile(Path src, Path dest) {
        try {
            Files.copy(src, dest);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteFile(Path src) {
        File imageFile = new File(src.toString());
        boolean result = imageFile.delete();
        return result;
    }
}

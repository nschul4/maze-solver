package maze.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

public class FileUtil {

    private FileUtil() {
    }

    public static String readText(final String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static String[] readTextLines(final String fileName) throws IOException {
        return StringUtils.split(readText(fileName), "\r\n");
    }

    public static String[] splitTextIntoTextLines(final String text) throws IOException {
        return StringUtils.split(text, "\r\n");
    }
}

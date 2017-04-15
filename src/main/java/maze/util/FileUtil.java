package maze.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    public static byte[][] readText(final String fileName) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(fileName));
        final List<byte[]> byteArrayList = new ArrayList<byte[]>();
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.length() < 1) {
                    continue;
                }
                byteArrayList.add(line.getBytes());
            }
        } finally {
            reader.close();
        }
        final byte[][] byteArrayListToByteArrayOfArrays = new byte[byteArrayList.size()][];
        return byteArrayList.toArray(byteArrayListToByteArrayOfArrays);
    }

    public static String textToString(final byte[][] textLines) {
        final StringBuilder sb = new StringBuilder();
        for (final byte[] textLine : textLines) {
            sb.append(new String(textLine));
            sb.append('\n');
        }
        return sb.toString();
    }
}

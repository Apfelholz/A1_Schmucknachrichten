import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileLogger {
    private final BufferedWriter writer;

    public FileLogger(String filePath) throws IOException {
        this.writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(filePath, false), StandardCharsets.UTF_8)
        );
    }

    public void print(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String s) {
        print(s + System.lineSeparator());
    }

    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

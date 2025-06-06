package life;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.kohsuke.github.GitHub;

public class PatternsIO {

    private static final String PATTERNDIRECTORY = "patterns";

    public static boolean save(boolean[][] array, String filename) {
        File patterns = new File(getPatternsDirectory(), filename);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(patterns))) {
            out.writeObject(array);
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    public static boolean[][] load(String filename) throws IOException, ClassNotFoundException {
        File patterns = new File(getPatternsDirectory(), filename);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patterns))) {
            return (boolean[][]) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            downloadPatterns();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patterns))) {
            return (boolean[][]) ois.readObject();
        }
    }

    private static void downloadPatterns() throws MalformedURLException, IOException {
        URL url = new URL("https://github.com/wolftxt/Game-Of-Life/raw/refs/heads/master/src/life/patterns/help");
        InputStream in = url.openStream();
        File patterns = new File(getPatternsDirectory(), "help");
        if (!patterns.exists()) {
            patterns.mkdirs();
        }
        Files.copy(in, patterns.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static File getPatternsDirectory() {
        File codeDirectory = new File(LifeWindow.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        return new File(codeDirectory.getParent(), PATTERNDIRECTORY);
    }
}

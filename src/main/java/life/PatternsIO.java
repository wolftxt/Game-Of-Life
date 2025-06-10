package life;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
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

    public static boolean[][] load(String filename) throws IOException, ClassNotFoundException, URISyntaxException {
        File pattern = new File(getPatternsDirectory(), filename);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pattern))) {
            return (boolean[][]) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            downloadPatterns();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pattern))) {
            return (boolean[][]) ois.readObject();
        }
    }

    private static void downloadPatterns() throws MalformedURLException, IOException, URISyntaxException {
        String repoOwner = "wolftxt";
        String repoName = "Game-Of-Life";
        String branch = "master";
        String folderPath = "src/main/resources";

        GitHub github = GitHub.connectAnonymously();
        GHRepository repo = github.getRepository(repoOwner + "/" + repoName);

        List<GHContent> contents = repo.getDirectoryContent(folderPath, branch);
        for (GHContent content : contents) {
            if (content.isFile()) {
                downloadFile(content.getDownloadUrl(), content.getName());
            }
        }
    }

    private static void downloadFile(String urlString, String fileName) throws IOException, URISyntaxException {
        URL url = new URI(urlString).toURL();
        InputStream in = url.openStream();
        File patterns = new File(getPatternsDirectory(), fileName);
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

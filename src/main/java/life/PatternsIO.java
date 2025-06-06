package life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PatternsIO {

    public static boolean save(boolean[][] array, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(array);
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    public static boolean[][] load(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (boolean[][]) in.readObject();
        }
    }
}

package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {
    public static List<String> read(String filename) {
        Scanner file;
        try {
            file = new Scanner(new File(filename));
            List<String> out = new ArrayList<>();
            while (file.hasNextLine()) { out.add(file.nextLine()); }
            file.close();
            return out;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }   
}
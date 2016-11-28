package project.fedorova.polyglotte.data;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {
    public static final String DICT_LIST = "dictlist";
    public static final String THEMES_LIST = "themes";

    private FileManager() {}

    public static void write(Context cntx, String file, String... content) {
        try {
            File outputFile = new File(cntx.getFilesDir() + "/" + file);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Arrays.toString(content).replace("[", "").replace("]", ""));
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(cntx, "File to write not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(cntx, "Error with writing to file.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public static String[] readArray(Context cntx, String file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(cntx.openFileInput(file)));
            ArrayList<String> res = new ArrayList<>();
            String s ;
            while((s = br.readLine()) != null){
                res.addAll(Arrays.asList(s.split(", ")));
            }
            return Arrays.copyOf(res.toArray(), res.size(), String[].class);
            //return br.lines().map(line -> line.split(", ")).toArray(String[]::new);
        } catch (FileNotFoundException e) {
            Toast.makeText(cntx, "File to read not found.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(cntx, "Error with reading file.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}

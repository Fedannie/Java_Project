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
import java.util.List;

public class ReadWriteManager {

    public static volatile ReadWriteManager instance;

    public static final String DICT_LIST = "dictlist";
    public static final String THEMES_LIST = "themes";

    private ReadWriteManager() {}

    public void writeToFile(Context cntx, String file, String content) {
        try {
            File outputFile = new File(cntx.getFilesDir() + "/" + file);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(cntx, "File to write not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(cntx, "Error with writing to file.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String readFromFile(Context cntx, String file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(cntx.openFileInput(file)));
            String res = "";
            String s = "";
            while((s = br.readLine()) != null){
                res += s;
            }
            return res;
        } catch (FileNotFoundException e) {
            Toast.makeText(cntx, "File to read not found.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(cntx, "Error with reading file.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String convertArrayToString(String... content) {
        return Arrays.toString(content).replace("[", "").replace("]", "");
    }

    public String[] convertStringToArray(String content) {
        List<String> res = Arrays.asList(content.split(", "));
        return Arrays.copyOf(res.toArray(), res.size(), String[].class);
    }

    public static ReadWriteManager getInstance() {
        ReadWriteManager localInstance = instance;
        if (localInstance == null) {
            synchronized (DictList.class){
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReadWriteManager();
                }
            }
        }
        return localInstance;
    }
}

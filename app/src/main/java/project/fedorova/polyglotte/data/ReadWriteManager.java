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
import java.util.HashSet;
import java.util.Set;

import project.fedorova.polyglotte.R;

public class ReadWriteManager {
    private static volatile ReadWriteManager instance;

    private ReadWriteManager() {}

    public void writeToFile(Context context, String file, String content) {
        try {
            File outputFile = new File(context.getFilesDir() + "/" + file);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.msg_file_write_not_found), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.msg_error_write_file), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String readFromFile(Context context, String file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(file)));
            String res = "";
            String s = "";
            while((s = br.readLine()) != null){
                res += s;
            }
            return res;
        } catch (FileNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.msg_file_read_not_found), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.msg_error_read_file), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String convertSetToString(Set<String> content) {
        if (content == null) {
            return "";
        }
        return Arrays.toString(content.toArray()).replace("[", "").replace("]", "");
    }

    public Set<String> convertStringToSet(String content) {
        if (content == null) {
            return null;
        }
        Set<String> res = new HashSet<>();
        res.addAll(Arrays.asList(content.split(", ")));
        return res;
    }

    public String convertArrayListToString(ArrayList<String> content, char div) {
        if (content == null) {
            return "";
        }
        String ans = "";
        for (String s : content) {
            ans += s + div;
        }
        return ans;
    }

    public ArrayList<String> convertStringToArrayList(String content, char div) {
        if (content == null) {
            return null;
        }
        ArrayList<String> res = new ArrayList<>();
        String newDiv = "" + div;
        res.addAll(Arrays.asList(content.split(newDiv)));
        return res;
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

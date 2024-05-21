package sk.karab.util.files;

import sk.karab.FPinata;
import sk.karab.util.debug.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class PluginFileTool {


    public static void copyFileFromRes(File folder, String fileName) {

        if (!folder.exists())
            folder.mkdir();

        File file = new File(folder, fileName);
        if (file.exists())
            return;

        InputStream resStream = FPinata.instance.getResource(fileName);
        if (resStream == null) {
            Log.err("Attempted to copy menu file " + fileName + ", but couldn't, because there was no input resource stream.");
            return;
        }

        try {
            Files.copy(resStream, file.getAbsoluteFile().toPath());
        } catch (IOException exception) {
            exception.printStackTrace();
            Log.err(exception.getMessage());
        }
    }


}

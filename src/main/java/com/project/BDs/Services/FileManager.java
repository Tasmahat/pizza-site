package com.project.BDs.Services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public void upload(byte[] resource, String keyName) throws IOException {
        Path path = Paths.get(
                "./frontend/src/Components/images",
                keyName + ".jpg"
        );
        path = path.toAbsolutePath();
        path = path.normalize();
        System.out.println(path);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }

    public void delete(String keyName) throws IOException {
        Path path = Paths.get(
                "./frontend/src/Components/images",
                keyName + ".jpg"
        );
        path = path.toAbsolutePath();
        path = path.normalize();
        boolean result = Files.deleteIfExists(path);
        if(result) {
            System.out.println("File was deleted");
        } else {
            System.out.println("File couldn't be deleted");
        }
    }
}

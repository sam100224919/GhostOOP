package com.ghostoop.util;

import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.DataPersistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard Java Object Serialization file persistence implementation.
 * Safely persists and restores serializable entity collections to disk.
 *
 * @param <T> the serializable entity type
 */
public class FileDataPersistence<T extends Serializable> implements DataPersistence<T> {

    private final File targetFile;

    public FileDataPersistence(File targetFile) {
        if (targetFile == null) {
            throw new ValidationException("Target file cannot be null.");
        }
        this.targetFile = targetFile;
    }

    public FileDataPersistence(Path filePath) {
        if (filePath == null) {
            throw new ValidationException("File path cannot be null.");
        }
        this.targetFile = filePath.toFile();
    }

    public File getTargetFile() {
        return targetFile;
    }

    @Override
    public void saveAll(List<T> data) throws IOException {
        if (data == null) {
            throw new ValidationException("Data collection to persist cannot be null.");
        }

        File parentDir = targetFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(targetFile))) {
            oos.writeObject(new ArrayList<>(data));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadAll() throws IOException, ClassNotFoundException {
        if (!targetFile.exists()) {
            return new ArrayList<>();
        }

        if (targetFile.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(targetFile))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<T>) obj;
            }
            throw new IOException("Unexpected file format: content is not a List.");
        }
    }
}

package base.scm.threads.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class TransferFileService {

    public synchronized boolean transferFile(File f,String destinationPath) throws IOException {
        File myFolder = new File(destinationPath);
        Files.copy(f.toPath(), myFolder.toPath().resolve(f.getName()), REPLACE_EXISTING);
        return f.delete();
    }
}

package eapli.base.rawmaterialmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class DataSheet implements ValueObject {

    private String name;

    private String path;

    @Lob
    @Column(length = 100000)
    private byte[] pdf;

    public DataSheet(String name, String path) {
        this.name = name;
        this.path = path;
        pdf = convertFile(path);
    }

    public String fileName() {
        return this.name;
    }

    public String filePath() {
        return this.path;
    }

    public byte[] pdf() {
        return this.pdf;
    }


    public static byte[] convertFile(String path) {
        byte[] pdf = null;
        try {
            Path pdfPath = Paths.get(path);
            pdf = Files.readAllBytes(pdfPath);
        } catch (IOException e) {
            System.out.println("File not Found!");
        } catch (Exception e) {
            System.out.println("Invalid File!");
        }
        return pdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSheet dataSheet = (DataSheet) o;
        return Objects.equals(name, dataSheet.name) &&
                Objects.equals(path, dataSheet.path) &&
                Arrays.equals(pdf, dataSheet.pdf);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, path);
        result = 31 * result + Arrays.hashCode(pdf);
        return result;
    }
}

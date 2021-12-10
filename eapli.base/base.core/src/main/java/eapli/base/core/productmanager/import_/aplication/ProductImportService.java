/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.import_.aplication;

import com.opencsv.CSVReader;
import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.general.domain.model.Designation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Pedro Cardoso
 */
public class ProductImportService {

    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    public void importCSV(String filename) throws FileNotFoundException, IOException {

        try {
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            br.readLine();
            int i = 0;
            String[] values = new String[6];

            while ((line = br.readLine()) != null) {
                tempArr = line.split(";");
                for (String tempStr : tempArr) {
                    values[i] = tempStr;
                    i++;
                }
                Product p = new Product(Designation.valueOf(values[0]), values[1], values[2], values[3], values[4], values[5]);
                productRepository.save(p);
                i = 0;
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

package eapli.base.factoryfloor.application;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExportXMLService {

    public void xmlToHtml(String pathXSL, String pathXML, String extension) {
        try {

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xslDoc = new StreamSource(pathXSL);
            Source xmlDoc = new StreamSource(pathXML);
            String outputFileName= "GroundFloor"+extension;
            OutputStream file = new FileOutputStream(outputFileName);
            Transformer trasform = tFactory.newTransformer(xslDoc);
            trasform.transform(xmlDoc, new StreamResult(file));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

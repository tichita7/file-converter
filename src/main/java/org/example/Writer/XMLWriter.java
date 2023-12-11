package org.example.Writer;

import org.example.ProcessCount.StudentCount;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class XMLWriter implements IWriter {
    FileWriter myWriter = null;
    public void write(List<StudentCount> stu_count, String path) throws IOException {
        try{

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("StudentCount");
            doc.appendChild(root);

            Element Details = doc.createElement("StuCount");
            root.appendChild(Details);


            for (int i=0;i<stu_count.size();i++) {

                Element dept = doc.createElement("Department");
                //System.out.println( dept.appendChild(doc.createTextNode(String.valueOf(stu.getDept()))));
                dept.appendChild(doc.createTextNode(String.valueOf(stu_count.get(i).getDept())));
                Details.appendChild(dept);

                Element count = doc.createElement("Count");
                count.appendChild(doc.createTextNode(String.valueOf(stu_count.get(i).getCount())));
                Details.appendChild(count);
            }

                myWriter = new FileWriter(path);

            try
            {
                DOMSource domSource = new DOMSource(doc);
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.transform(domSource, result);
                myWriter.write( writer.toString());
            }
            catch(TransformerException ex)
            {
                ex.printStackTrace();
            }

                myWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

}



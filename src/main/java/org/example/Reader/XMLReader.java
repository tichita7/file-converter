package org.example.Reader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XMLReader implements IReader {
    public static List<Student> students=new ArrayList<>();

    public List<Student> read(String path) throws IOException {
        String type=path.substring(path.lastIndexOf(".")+1);
        String fileName=type+"File";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs;
        try {
            conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
            stmt = conn.createStatement();
            stmt.execute("drop table if exists "+fileName);
            stmt.execute("create table "+fileName+" (Student varchar(100), Department varchar(100))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        {
            try {
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(new File(path));
                document.getDocumentElement().normalize();
                NodeList stuList = document.getElementsByTagName("students");

                for (int i = 0; i < stuList.getLength(); i++) {
                    Node stu = stuList.item(i);
                    if (stu.getNodeType() == Node.ELEMENT_NODE) {
                        Element stuElement = (Element) stu;
                        NodeList studetails = stuElement.getChildNodes();
                        for (int j = 0; j < studetails.getLength(); j++) {
                            Node detail = studetails.item(j);
                            if (detail.getNodeType() == Node.ELEMENT_NODE) {
                                Element detailElement = (Element) detail;
                                String name = (String) detailElement.getElementsByTagName("Name").item(0).getTextContent();
                                String dept = (String) detailElement.getElementsByTagName("Department").item(0).getTextContent();
                                stmt.execute("INSERT INTO "+fileName+" (Student, Department)" +
                                        "VALUES ('"+name+"','"+dept+"')");
                            }
                        }
                    }
                }

            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
   DBReader db=new DBReader();
        return db.dbList(fileName);
    }
}

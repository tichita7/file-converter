package org.example.Reader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class XMLReaderSAX implements IReader{
    public List<Student> read(String path) throws IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        String type=path.substring(path.lastIndexOf(".")+1);
        String fileName=type+"File";
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();

            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                ResultSet rs;
                conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
                stmt = conn.createStatement();
                stmt.execute("drop table if exists "+fileName);
                stmt.execute("create table "+fileName+" (Student varchar(100), Department varchar(100))");
                Statement finalStmt = stmt;
                DefaultHandler handle = new DefaultHandler() {
                    String name="",dept="";
                    StringBuilder buffer = new StringBuilder();
                    boolean n = false, d = false;

                    public void startElement(String uri, String localName,
                                             String qName, Attributes attributes) throws SAXException {
                        if (qName.equals("Name")) n = true;
                        if (qName.equals("Department")) d = true;
                    }

                    public void endElement(String uri, String localName, String qName)
                            throws SAXException {
                        if (n) {
                            name = buffer.toString();
                            buffer.setLength(0);
                            n = false;
                        }

                        if (d) {
                            dept = buffer.toString();
                            buffer.setLength(0);
                            d = false;
                        }
                          name=name.trim();
                        dept=dept.trim();


                        if (!name.isEmpty() && !dept.isEmpty()) {
                            try {
                                finalStmt.execute("INSERT INTO "+fileName+" (Student, Department)" +
                                        "VALUES ('"+name+"','"+ dept+"')");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            name = "";
                            dept = "";
                        }
                    }

                    @Override
                    public void characters(char[] ch, int start, int length) throws SAXException {
                        buffer.append(ch, start, length);
                    }
                };




                saxParser.parse("C:\\Users\\tichita\\Downloads\\XMLex.xml", handle);

            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        DBReader obj=new DBReader();
        return obj.dbList(fileName);
    }
}


/*


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class XMLReaderSAX extends DefaultHandler implements IReader {
    private List<Student> students = new ArrayList<>();
    private String currentElement = null;
    private String name = null;
    private String dept = null;
    Connection conn = null;
    Statement stmt = null;

    public List<Student> read(String path) {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
            stmt = conn.createStatement();
            stmt.execute("drop table if exists csvdata");
            stmt.execute("create table csvdata (Student varchar(100), Department varchar(100))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(path), this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        DBReader db = new DBReader();
        return db.dbList();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("student")) {
            try {
                stmt.execute("INSERT INTO csvdata (Student, Department)" +
                        "VALUES ('" + name + "','" + dept + "')");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).trim();
        if (currentElement.equalsIgnoreCase("name")) {
            name = value;
        } else if (currentElement.equalsIgnoreCase("department")) {
            dept = value;
        }
    }
}

*/
package org.example.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JSONReader implements IReader {
    public static List<Student> stu = new ArrayList<>();

    public List<Student> read(String path) throws IOException {
        JSONParser parser = new JSONParser();
        String type=path.substring(path.lastIndexOf(".")+1);
        String fileName=type+"File";
        try {
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arrayjson = (JSONArray) ((JSONObject) obj).get("tichita");
            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            ResultSet rs;
            conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
            stmt = conn.createStatement();
            stmt.execute("drop table if exists "+fileName);
            stmt.execute("create table "+fileName+" (Student varchar(100), Department varchar(100))");

            for (int i = 0; i < arrayjson.size(); i++) {
                JSONObject jsonObject1 = (JSONObject) arrayjson.get(i);
                String name = (String) jsonObject1.get("Name");
                String dept = (String) jsonObject1.get("Department");
                stmt.execute("INSERT INTO "+fileName+" (Student, Department)" +
                        "VALUES ('"+name+"','"+dept+"')");
            }
            rs = stmt.executeQuery("select * from "+fileName);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DBReader db=new DBReader();
        return db.dbList(fileName);
    }


}



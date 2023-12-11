package org.example.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements IReader {
    //public static List<Student> students = new ArrayList<>();

    public List<Student> read(String path) throws IOException {
        List<Student> s=new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = " ";
        String tempArr[];
        String type=path.substring(path.lastIndexOf(".")+1);
        String fileName=type+"File";
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
            while ((line = br.readLine()) != null) {
                tempArr = line.split(",");
                String name = tempArr[0];
                String dept = tempArr[1];
                if(name.equals("Student"))
                    continue;

                stmt.execute("INSERT INTO "+fileName+" (Student, Department)" +
                       "VALUES ('"+name+"','"+dept+"')");


            }
            rs = stmt.executeQuery("select * from "+fileName);
            while (rs.next()) {
                System.out.println(  " Student " + rs.getString("Student") + " Department " + rs.getString("Department") );
            }





            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBReader db=new DBReader();
        return db.dbList(fileName);
    }

}



package org.example.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBReader {
    public List<Student> dbList(String fileName)
    {
        Connection conn = null;
        List<Student> students=new ArrayList<>();
        Statement stmt = null;
        ResultSet rs;
        try {
            conn = DriverManager.getConnection("jdbc:h2:~/test", "", "");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from "+fileName);
            while(rs.next())
            {
                Student s = new Student();
                s.setName(rs.getString("Student"));
                s.setDepartment(rs.getString("Department"));
                students.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;

    }
}
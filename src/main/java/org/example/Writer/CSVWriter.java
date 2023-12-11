package org.example.Writer;

import org.example.ProcessCount.Count;
import org.example.ProcessCount.StudentCount;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter implements IWriter{
   public void write(List<StudentCount> stu_count, String path) throws IOException
    {
        Count c=new Count();
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(path);
            myWriter.write("Department,Count\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String str="";
            for(StudentCount s:stu_count){
                str=str+s.getDept()+","+s.getCount()+"\n";
            }
            System.out.println(str);
            myWriter.write(str);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

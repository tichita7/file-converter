package org.example.Writer;

import org.example.ProcessCount.StudentCount;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONWriter implements IWriter{
    JSONObject obj=new JSONObject();
    public void write(List<StudentCount> stu_count, String path) throws IOException
    {
        FileWriter myWriter = null;
        for(StudentCount stu: stu_count)
        {
            obj.put(stu.getDept(),stu.getCount());
        }
        System.out.println(obj.toJSONString());
        myWriter = new FileWriter(path);
        myWriter.write(obj.toJSONString());
        myWriter.close();
    }
}

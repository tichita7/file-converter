package org.example.driver;

import org.example.Factory.AbstractFactory;
import org.example.Factory.FactoryCreator;
import org.example.ProcessCount.Count;
import org.example.ProcessCount.StudentCount;
import org.example.Reader.IReader;
import org.example.Reader.Student;
import org.example.Writer.IWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\tichita\\Downloads\\XMLex.xml");
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(file.getName());
        if (file.exists()) {
            AbstractFactory ReaderFactory = FactoryCreator.getFactory("Reader");
            IReader reader = ReaderFactory.getReader(mimeType); //=> ReaderTest
            List<Student> students = reader.read(file.getPath());
            Count processor = new Count();
            List<StudentCount> studentCountList=processor.countfunc(students);
            AbstractFactory WriterFactory = FactoryCreator.getFactory("Writer");
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the type of the file you want to write: ");
            String WriteFileType=br.readLine();
            System.out.print("Enter the path where you want to write the file ");
            String WritePath=br.readLine();
            IWriter writer  = WriterFactory.getWriter(WriteFileType);
            writer.write(studentCountList,WritePath);
        }
        else
            throw new RuntimeException("File does not exits");
        }
    }



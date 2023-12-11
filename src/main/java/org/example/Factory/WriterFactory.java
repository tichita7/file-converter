package org.example.Factory;

import org.example.Reader.IReader;
import org.example.Writer.CSVWriter;
import org.example.Writer.IWriter;
import org.example.Writer.JSONWriter;
import org.example.Writer.XMLWriter;

public class WriterFactory implements AbstractFactory {
    @Override
    public IReader getReader(String FileType) {
        return null;
    }

    @Override
    public IWriter getWriter(String WriteFileType) {
        if(WriteFileType == null){
            return null;
        }
        if(WriteFileType.equalsIgnoreCase("json")){
            return new JSONWriter();
        } else if(WriteFileType.equalsIgnoreCase("csv")){
            return new CSVWriter();
        } else if(WriteFileType.equalsIgnoreCase("xml")){
            return new XMLWriter();
        }
        return null;
    }
}

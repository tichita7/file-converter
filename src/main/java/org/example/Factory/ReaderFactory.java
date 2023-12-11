package org.example.Factory;

import org.example.Reader.CSVReader;
import org.example.Reader.IReader;
import org.example.Reader.JSONReader;
import org.example.Reader.XMLReaderSAX;
import org.example.Writer.IWriter;

public class ReaderFactory implements AbstractFactory {

    @Override
    public IReader getReader(String FileType) {
        if(FileType == null){
            return null;
        }
        if(FileType.equalsIgnoreCase("application/json")){
            return new JSONReader();
        } else if(FileType.equalsIgnoreCase("application/xml")){
            return new XMLReaderSAX();
        } else if(FileType.equalsIgnoreCase("text/csv")){
            return new CSVReader();
        }
        return null;
    }

    @Override
    public IWriter getWriter(String WriteFileType) {
        return null;
    }

}

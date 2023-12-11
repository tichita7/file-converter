package org.example.Factory;

import org.example.Reader.IReader;
import org.example.Writer.IWriter;

public interface AbstractFactory {
    public IReader getReader(String FileType);
    public IWriter getWriter(String WriteFileType);

}

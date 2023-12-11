package org.example.Reader;

import java.io.IOException;
import java.util.List;

public interface IReader {
    public List<Student> read(String s)  throws IOException;
}

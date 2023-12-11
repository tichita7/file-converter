package org.example.Writer;
import org.example.ProcessCount.StudentCount;

import java.io.IOException;
import java.util.List;

public interface IWriter {
   public void write(List<StudentCount> obj, String path) throws IOException;
}

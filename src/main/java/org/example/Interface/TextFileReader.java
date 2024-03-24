package org.example.Interface;

import java.util.List;

public interface TextFileReader <T>{
    List<T> readTextFiles (List<String> paths);
}

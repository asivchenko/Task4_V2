package org.example;

import org.example.Interface.TextFileReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class FileReaderComponent <T> implements TextFileReader<T> {
    @Override
    public List<T> readTextFiles (List<String> paths)
    {
          List<T>  lines =new ArrayList<>();
        for (String path : paths) {
            File directory = new File (path); //Cписок всех файлов в директории
            File[] files =directory.listFiles((dir, name) -> name.endsWith(".txt"));
            Arrays.stream(files).forEach(file -> {
                System.out.println("Читаем строки файла  " + file.getName());
                try  (BufferedReader reader=new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line =reader.readLine()) != null ) {
                        //T data = parseline(line, file.getName());
                        lines.add((T) new LineFile (line, file.getName()));  // делаем cast как преждагают
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return lines;
    }
    /*   здесь можно навернуть все что  надо в класс
    private T parseline (String line, String namefile )
    {
        return (T) new LineFile (line, namefile);
    }
    */

}



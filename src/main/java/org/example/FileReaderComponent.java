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
                            String[] attributes = line.split(";");
                            if (attributes.length != 4) {
                                lines.add((T) new LineFile(null,null,null, null,
                                                                   file.getName(),line, "Не верен формат строки"));

                            }
                            else
                                lines.add((T) new LineFile ( attributes[0],attributes[1],attributes[2],
                                                             attributes[3],file.getName(),line,null));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return lines;
    }
}



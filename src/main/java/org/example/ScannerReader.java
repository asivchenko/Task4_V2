package org.example;

import org.example.Interface.PathReader;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Component
public class ScannerReader implements PathReader<String> {
    @Override
    public List<String> filereader() {

        List<String> paths = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите путь к текстовому файлу или 'ВСЕ' для завершения: ");
            String path = scanner.nextLine();

            if (path.equalsIgnoreCase("ВСЕ")) {
                break;
            }

            File file = new File(path);
            if (!file.exists()) {
                System.out.println("Ошибка: Нет такого пути.");
                continue;
            }

            if (!file.isDirectory()) {
                System.out.println("Ошибка: Указанный путь не является директорией.");
                continue;
            }

            File[] files = file.listFiles((dir, name) -> name.endsWith(".txt"));
            if (files == null || files.length == 0) {
                System.out.println("Ошибка: В указанной директории нет текстовых файлов.");
                continue;
            }

            paths.add(path);
            System.out.println ("Директория принята ");
        }

        scanner.close();
        return paths;
    }
}



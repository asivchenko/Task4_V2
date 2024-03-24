package org.example;

import org.example.Interface.DataValidator;
import org.example.LogTransformation.LogTransformation;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class DataValidatorComponent  implements DataValidator<LineFile,ValidRecord> {
    @Override
    @LogTransformation(LogFile = "Validator.log")
    public List<ValidRecord> validatordata  (List<LineFile> lines)  {

        List<ValidRecord> validRecords = new ArrayList<>();
        //  System.out.println ("ВАЛИДАЦИЯ");
        Date accessDatetime ;
        Timestamp timestamp ;

        SimpleDateFormat formater =new SimpleDateFormat("YYYY-MM-dd:HH:mm:ss");

        for (LineFile line : lines) {
            String[] fields = line.getLine().split(";");

          //  System.out.println ("----------------ПРОЧИТАЛИ  строку=" + Arrays.toString(fields));

            if (fields.length != 4) {
                line.setTextError("Неверный формат строки");
                continue;
            }

            String login = fields[0].trim().toUpperCase();
            String fio = fields[1].trim().toUpperCase();
            String access_string = fields[2].trim();
            String appType = fields[3].trim().toUpperCase();

            // Проверка наличия значений для каждого реквизита
            if (login.isEmpty()) {
                line.setTextError("Логин пользователя не заполнен");
                continue;
            }

            if (fio.isEmpty()) {
                line.setTextError("ФИО не заполнено");
                continue;
            }

            if (access_string.isEmpty()) {
                line.setTextError("Дата доступа не заполнена");
                continue;
            }

            if (appType.isEmpty()) {
                line.setTextError("Тип приложения не заполнен");
                continue;
            }

            // Проверка логина
            if (!login.matches("[a-zA-Z]+")) {
                line.setTextError("Логин пользователя содержит не латинские буквы");
                continue;
            }

            // Проверка ФИО только русские
            if (!fio.matches("[a-zA-Zа-яА-Я\\s]+")) {
                line.setTextError("Запрещенные символы для ФИО");
                continue;
            }

            // Проверка даты
            if (!access_string.matches("\\d{4}-\\d{2}-\\d{2}:\\d{2}:\\d{2}:\\d{2}")) {
                line.setTextError("Неверный формат даты");
                continue;
            }

            try
            {
                accessDatetime=formater.parse(access_string);
                timestamp =new Timestamp(accessDatetime.getTime());
                // timestamp  =  Timestamp.valueOf(accessDatetime.toString());

            } catch (ParseException e) {
                //throw new RuntimeException(e);
                line.setTextError("Ошибка при парсинге даты");
                continue;
            }


            // Проверка типа приложения
            if (!appType.toLowerCase().equals("mobile") && !appType.toLowerCase().equals("web")) {
                line.setTextError("Предупреждение. Тип приложения не соответствует значениям : mobile, web");
                appType="OTHER";
            }

            // Если все проверки прошли успешно, добавляем валидную запись в список

            validRecords.add(new ValidRecord(login, formatFio(fio), timestamp, appType));
        }
        return validRecords;
    }
    public static String  formatFio (String input)
    {
        String[]  words =input.split ("\\s+"); // выделяем слова
        String result="";
        for (String word : words)
        {
            word =word.trim();
            result =result +word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase() +" ";
        }
        return result.trim(); // убираем пробел
    }


}


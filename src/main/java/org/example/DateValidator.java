package org.example;

import org.example.Interface.DataValidationComponent;
import org.example.LogTransformation.LogTransformation;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@LogTransformation(LogFile = "ValidatorDate.log")
@Component
public class DateValidator implements Ordered, DataValidationComponent<LineFile> {
    @Override
    public int getOrder() {
        return 10;  //порядок выполнения
    }
    @Override
    public boolean validate(LineFile line) {
        String dateString= line.getDate_string();
        if (dateString ==null) {
            line.setTextError("Дата не задана");
            return false;
        }

        dateString=dateString.trim();
        if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}:\\d{2}:\\d{2}:\\d{2}")) {
            line.setTextError("Неверный формат даты =" +dateString);
            return false;
        }

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
        try
        {
            LocalDateTime dateTime =LocalDateTime.parse(dateString,formatter);
            line.setAccess_stamp(Timestamp.valueOf(dateTime));
            return true;

        } catch (Exception e) {
            line.setTextError("Ошибка при парсинге даты " + dateString );
            return false;
        }
    }
}

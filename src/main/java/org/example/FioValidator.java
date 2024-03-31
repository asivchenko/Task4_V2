package org.example;

import org.example.Interface.DataValidationComponent;
import org.example.LogTransformation.LogTransformation;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

//@LogTransformation(LogFile = "ValidatorFIO.log")
@Component
public class FioValidator implements Ordered, DataValidationComponent <LineFile>{
    @Override
    public int getOrder () {
        return 30;  //порядок выполнения
    }

    @Override
    public boolean validate(LineFile line) {
        String fio =line.getFio();
        if (fio==null) {
            line.setTextError("ФИО не заполнено");
            return false;
        }
        fio=fio.trim();
        String[]  words =fio.split ("\\s+"); // выделяем слова
        String result="";
        StringBuilder stringBuilder =new StringBuilder();
        for (String word : words)
        {
            word =word.trim();
            stringBuilder.append(word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase() +" ");
        }
        line.setFio(stringBuilder.toString().trim()); // убираем пробел
        return true;
    }
}

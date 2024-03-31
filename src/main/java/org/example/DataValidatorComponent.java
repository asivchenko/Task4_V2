package org.example;

import org.example.Interface.DataValidationComponent;
import org.example.Interface.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class DataValidatorComponent  implements DataValidator<LineFile,ValidRecord> {
    @Autowired
    private List <DataValidationComponent <LineFile>> validators;
    @Override
    public List<ValidRecord> validatordata  (List<LineFile> lines)  {
        List<ValidRecord> validRecords = new ArrayList<>();
        System.out.println ("ВАЛИДАЦИЯ");
        for (LineFile line : lines)
        {
            boolean isvalid=true;
            for (DataValidationComponent <LineFile> validator : validators )
            {
                if (!validator.validate(line)) {
                  //  System.out.println("ЗАПИСЬ НЕ ВАЛИДНА для "+ line.getFio());
                    isvalid=false;
                    break;
                }
            }
            if (isvalid) {
                validRecords.add(new ValidRecord(line.getLogin(),line.getFio(), line.getAccess_stamp(),line.getApp()));
                //     System.out.println("ЗАПИСЬ ВАЛИДНА для "+ line.getFio());
            }
        }
        return validRecords;
    }

}


package org.example;

import org.example.Interface.DataValidator;
import org.example.data.DatabaseWorks;
import org.example.LogTransformation.LogTransformationProxyCreate;
import org.example.LogTransformation.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Scheduler{

    private ScannerReader  scannerReader;
    private DataValidator < LineFile, ValidRecord>  validatorComponent;
    private  FileReaderComponent fileReaderComponent;
    private DatabaseWorks databaseWorks;
    private Logger logger;

    public Scheduler (
        ScannerReader scannerReader,
        //DataValidatorComponent validatorComponent,
        DataValidator < LineFile, ValidRecord>  validatorComponent,  //чуть голова не отвалилась пока до этого догадался
                                                                   // попробовать
        FileReaderComponent fileReaderComponent,
        DatabaseWorks databaseWorks,
        Logger logger

        ){
        this.scannerReader=scannerReader;
        this.fileReaderComponent = fileReaderComponent;
        this.validatorComponent = validatorComponent;  // абстракный тип
        this.validatorComponent = LogTransformationProxyCreate.createproxy(validatorComponent,logger);
        this.databaseWorks=databaseWorks;
        this.logger=logger;
    }

    @Scheduled(initialDelay = 0,  fixedRate = Long.MAX_VALUE)
    public void task() throws Exception {

        List<String>  paths =scannerReader.filereader();
        if (paths.size()!=0 ) {
            List<LineFile> lines = fileReaderComponent.readTextFiles(paths);
            List<ValidRecord> valids = validatorComponent.validatordata(lines);
            databaseWorks.writeErrorToDateBase(lines);
            databaseWorks.writeToDateBase(valids);
            System.out.println("Отработали все компоненты. Работа окончена");
        }
        System.exit(0); //конец работы
    }
}

package org.example;

import org.example.data.DatabaseWorks;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class Scheduler{
    private ScannerReader  scannerReader;
    //private DataValidator < LineFile, ValidRecord>  validatorComponent;
    private DataValidatorComponent validatorComponent;
    private  FileReaderComponent fileReaderComponent;
    private DatabaseWorks databaseWorks;
    //private Logger logger;
    public Scheduler (
        ScannerReader scannerReader,
        DataValidatorComponent validatorComponent,
        FileReaderComponent fileReaderComponent,
        DatabaseWorks databaseWorks

        ){
        this.scannerReader=scannerReader;
        this.fileReaderComponent = fileReaderComponent;
        this.validatorComponent = validatorComponent;
        this.databaseWorks=databaseWorks;
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

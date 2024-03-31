import org.example.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class TestForTask4 {

@Test
@DisplayName("Тестируем работу ScannerReader")
public void testFileReader ()
{
    //создаем макет
    ScannerReader scannerReader = Mockito.mock(ScannerReader.class);
    //Готовим данные
    String path1 =getClass().getResource("/task1").getPath();
    String path2 =getClass().getResource("/task2").getPath();
    String input =path1 +"\n" +path2 +"\nВСЕ\n";
    //String input ="с:\\task4\nc:\\task41\nВСЕ\n";
    System.out.println("input="+input);
    InputStream inputStream  =new ByteArrayInputStream(input.getBytes() );
    System.setIn(inputStream);
    //ожидаем результат
    List<String> expectedpath =List.of(path1,path2 );
    //задаем поведение макета
    when (scannerReader.filereader()).thenCallRealMethod(); // вызываем реальный метод
    List<String> actualpaths =scannerReader.filereader();
    // проверяем
    assertEquals(expectedpath,actualpaths);
}

    @Test
    @DisplayName("Тестируем работу FileReaderComponent")
    public void testFileReade ()
    {
        FileReaderComponent fileReaderComponent =new FileReaderComponent();
        String path1 =getClass().getResource("/task1").getPath();
        String path2 =getClass().getResource("/task2").getPath();
        List<String>paths=List.of(path1,path2 );
        List<LineFile>  lines =fileReaderComponent.readTextFiles(paths);
        assertEquals(4,lines.size()); // 4 строки в двух файлах
    }
    @Test
    @DisplayName("Тестируем работу Порядок выполнения валидаторов ")
    public void testValidatorOrder ()
    {
        DataValidatorComponent valid1 =Mockito.mock(DataValidatorComponent.class);
        DataValidatorComponent valid2 =Mockito.mock(DataValidatorComponent.class);
        DataValidatorComponent valid3 =Mockito.mock(DataValidatorComponent.class);
        DataValidatorComponent dataValidatorComponent =new DataValidatorComponent();

    }
    @Test
    @DisplayName("Контроль валидатора ФИО")
    public void testValidatorFio() {
        FioValidator validator =new FioValidator();
        LineFile line =new LineFile("logintest", "иванов иван иванович",
                "2024-03-30:12:10:10", "app", "filetest.txt",null,null );
        boolean result =validator.validate(line);
        assertEquals(true,result);
        String expectedFio ="Иванов Иван Иванович";
        assertEquals(expectedFio,line.getFio());
    }

    @Test
    @DisplayName("Контроль валидатора AppType")
    public void testValidatorAppType() {
        AppTypeValidator validator =new AppTypeValidator();
        LineFile line =new LineFile("logintest", "иванов иван иванович",
                "2024-03-30:12:10:10", "app", "filetest.txt",null,null );
        boolean result =validator.validate(line);
        assertEquals(true,result);
        String expectedFio ="other: app";
        assertEquals(expectedFio,line.getApp());
    }
    @Test
    @DisplayName("Контроль валидатора Дата")
    public void testValidatorDate() {

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
        LocalDateTime dateTime =LocalDateTime.parse("2024-03-30:12:10:10",formatter);
        Timestamp expectedStamp =Timestamp.valueOf(dateTime);

        DateValidator validator =new DateValidator();
        LineFile line =new LineFile("logintest", "иванов иван иванович",
                "2024-03-30:12:10:10", "app", "filetest.txt",null,null );
        boolean result =validator.validate(line);

        assertEquals(true,result);
        assertEquals(expectedStamp,line.getAccess_stamp());
    }
    @Test
    @DisplayName("Контроль ошибки на дате")

    public void testValidatorDateError() {

        DateValidator validator =new DateValidator();
        LineFile line =new LineFile("logintest", "иванов иван иванович",
                "2024-40-30:12:10:10", "app", "filetest.txt",null,null );
        boolean result =validator.validate(line);

        assertEquals(false,result);
        assertEquals(null,line.getAccess_stamp());
        assertNotNull(line.getTextError());
    }



}


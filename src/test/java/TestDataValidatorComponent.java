import org.example.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Mock;
//import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TestDataValidatorComponent {
    @Mock
    private FioValidator fioValidator;
    @Mock
    private DateValidator dateValidator;
    @Mock
    private AppTypeValidator appTypeValidator;
    @Autowired
    private DataValidatorComponent validatorComponent ;
    @Test
    public void testDataValidator ()    {
        when(dateValidator.getOrder()).thenReturn(1);
        when(fioValidator.getOrder()).thenReturn(2);
        when(appTypeValidator.getOrder()).thenReturn(3);

       LineFile line =new LineFile("logintest", "иванов иван иванович",
               "2024-03-30:12:10:10", "app", "filetest.txt",null,null );
       List<LineFile> lines = Arrays.asList(line);
        System.out.println("----------------выполняем валидацию");
        List<ValidRecord> validRecords =validatorComponent.validatordata(lines);
        //проверяем факт выполнения валидации
        InOrder order = Mockito.inOrder(dateValidator,fioValidator,appTypeValidator);
        order.verify(dateValidator).validate(any(LineFile.class));    //
        order.verify(fioValidator).validate(any(LineFile.class));
        order.verify(appTypeValidator).validate(any(LineFile.class));
        verify(dateValidator).validate(any(LineFile.class));
        verify(fioValidator).validate(any(LineFile.class));
        verify(appTypeValidator).validate(any(LineFile.class));
    }

}

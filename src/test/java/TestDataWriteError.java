import org.example.LineFile;
import org.example.data.DatabaseWorks;
import org.example.data.inter.LogErrorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TestDataWriteError {

    @Mock
    private LogErrorRepository logErrorRepository;
    @InjectMocks
    private DatabaseWorks databaseWorks;

    @Test
    public void testWriteRrrorToDataBase ()
    {
        LineFile errorline1 = Mockito.mock(LineFile.class);
        Mockito.when(errorline1.getTextError()).thenReturn("Error1");
        LineFile errorline2 = Mockito.mock(LineFile.class);
        Mockito.when(errorline2.getTextError()).thenReturn("Error2");
        List <LineFile>  errorLines = new ArrayList<>();
        errorLines.add(errorline1);
        errorLines.add(errorline2);
        databaseWorks.writeErrorToDateBase(errorLines);
        // проверяем что метод save вызван в LogErrorRepository для каждой ошибочной записи
        Mockito.verify(logErrorRepository,Mockito.times(errorLines.size())).save(Mockito.any());
    }

}

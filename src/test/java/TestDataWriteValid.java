import org.example.LineFile;
import org.example.ValidRecord;
import org.example.data.DatabaseWorks;
import org.example.data.entity.Users;
import org.example.data.inter.LogErrorRepository;
import org.example.data.inter.LoginRepository;
import org.example.data.inter.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TestDataWriteValid {

    @Mock
    private LoginRepository  loginRepository;
    @Mock
    private UserRepository  userRepository;

    @InjectMocks
    private DatabaseWorks databaseWorks;

    @Test
    @DisplayName( " тест записи в базу логина который есть в базе ")
    public void testWriteValidToDataBaseExistUser ()
    {
        ValidRecord validRecord1=new ValidRecord("login1","fio1",new Timestamp(System.currentTimeMillis()), "app");
        List<ValidRecord> valids =new ArrayList<>();
        valids.add(validRecord1);
        //симулируем поведение припоиске по Login1 пользователя находим  в базе
        // при поиске по Login2 пользователь нет в базе
        Optional<Users>  existUser =Optional.of(new Users());
        Mockito.when (userRepository.findbyNameUser("login1")).thenReturn(existUser);
        databaseWorks.writeToDateBase(valids);
        Mockito.verify(loginRepository,Mockito.times(1)).save(Mockito.any()); // если польхователь есть только ыфму для logins
        Mockito.verify(userRepository,Mockito.times(0)).save(Mockito.any());  // не вызывается если пользователь есть
    }
    @Test
    @DisplayName( " тест записи в базу нового логина которого нет в базе ")
    public void testWriteValidToDataBaseNewUser ()
    {
        ValidRecord validRecord2=new ValidRecord("login2","fio2",new Timestamp(System.currentTimeMillis()), "web");
        List<ValidRecord> valids =new ArrayList<>();
        valids.add(validRecord2);
        Mockito.when (userRepository.findbyNameUser("login2")).thenReturn(Optional.empty());
        databaseWorks.writeToDateBase(valids);
        Mockito.verify(loginRepository,Mockito.times(1)).save(Mockito.any()); // если польхователь есть только ыфму для logins
        Mockito.verify(userRepository,Mockito.times(1)).save(Mockito.any());  // не вызывается если пользователь есть
    }

}


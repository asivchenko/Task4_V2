package org.example.data;
import org.example.*;
import org.example.data.entity.LogErrors;
import org.example.data.entity.Logins;
import org.example.data.entity.Users;
import org.example.data.inter.DatabaseWriter;
import org.example.data.inter.LogErrorRepository;
import org.example.data.inter.LoginRepository;
import org.example.data.inter.UserRepository;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Component
public class DatabaseWorks implements DatabaseWriter<LineFile,ValidRecord> {
    private final LogErrorRepository logErrorRepository;
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    //.. для подключения

    private DataSource dataSource;

    public DatabaseWorks (DataSource dataSource,LogErrorRepository logErrorRepository,
                          UserRepository userRepository,
                          LoginRepository loginRepository
                          ) {
        this.dataSource=dataSource;
        this.logErrorRepository=logErrorRepository;
        this.userRepository=userRepository;
        this.loginRepository=loginRepository;
    }

     //spring сам берет это подключение   @value установим по умолчанию из applicatio.properties
    public void  setDatabaseConnection (@Value("${spring.datasource.url}") String url,
                                        @Value("${spring.datasource.username}") String username,
                                        @Value("${spring.datasource.password}")String password
                                      )


    {
        ((PGSimpleDataSource)dataSource).setURL(url);
        ((PGSimpleDataSource)dataSource).setUser(username);
        ((PGSimpleDataSource)dataSource).setPassword(password);
    }


    @Override
    @Transactional///запись ошибочных записей
    public void writeErrorToDateBase(List<LineFile> errorsLines) {
        //logErrorRepository.deleteAll();   // Для простоты  все чистим  так как пускаем много раз
        /*
        for (LineFile errorLine : errorsLines) {
            if (errorLine.getTextError() != null) {
                System.out.println("пишем ошибки в  базу logerrors");
                LogErrors error = new LogErrors();
                error.setLine(errorLine.getLine());
                error.setError_text(errorLine.getTextError());
                error.setName_file(errorLine.getNameFile());
                savelogerror(error);
            }
        }
        */   // Учимся писать лямбду
        errorsLines.stream().filter(line ->line.getTextError()!= null)
                .forEach(errorLine->{
                    LogErrors error = new LogErrors();
                    error.setLine(errorLine.getLine());
                    error.setError_text(errorLine.getTextError());
                    error.setName_file(errorLine.getNameFile());
                    logErrorRepository.save(error);

                });
    }
    @Override  // запись правильных записей
    @Transactional
    public void writeToDateBase(List<ValidRecord> valids) {
        //loginRepository.deleteAll(); отладка
        //userRepository.deleteAll();
        for (ValidRecord validRecord : valids) {
            Users user = new Users();
            Optional<Users> optionuser = userRepository.findbyNameUser(validRecord.getLogin());
            if   (optionuser.isEmpty() ) { // нет такого логина
                user.setNameuser(validRecord.getLogin());
                user.setFio(validRecord.getFio());
                try {
                    userRepository.save(user);
                    //System.out.println(" Сохраняем пользователя и получаем его ID" + user.getId());
                }
                catch  (Exception e) {
                    // Обрабатываем ошибку
                    e.printStackTrace(); // Выводим информацию об ошибке в консоль
                }

            }
            else {
                user = optionuser.get();
                //   System.out.println(" не сохраняем пользователя берем так как такой пользователь есть в базе"+user.getId());
            }

            Logins login = new Logins();
            login.setAccessDate(validRecord.getAccessDateTime());

            // Устанавливаем ID пользователя в поле user_id записи входа
            login.setUser(user);  //внешний ключ
            login.setName_app(validRecord.getAppType());

            try {
                // Сохраняем запись входа (login)
                loginRepository.save(login);
            } catch (Exception e) {
                e.printStackTrace(); // Выводим информацию об ошибке в консоль
            }

        }


    }

}


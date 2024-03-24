package org.example.data.inter;
import org.example.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    // первый вариант  Users findbyNameUser  (String nameuser ); // сделаем метод поиска по Login
    @Query("select u  from Users u  where u.nameuser=:nameuser")
    Optional <Users> findbyNameUser (@Param("nameuser")String  nameuser);
}

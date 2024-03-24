package org.example.data.inter;


import org.example.data.entity.LogErrors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogErrorRepository extends JpaRepository<LogErrors, Integer> {
}



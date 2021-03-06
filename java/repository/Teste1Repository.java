package com.trans.transpiladorCobolJava.repository.Teste1Repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface Teste1Repository extends JpaRepository<Teste1, Integer>{

	@Query(value = "SELECT NUMIMP, NUMC FROM Teste1 WHERE SUMA = 1004 ", nativeQuery = true)
	List<Object> teste11select();

	@Query(value = "INSERT INTO Teste1(NUMIMP, NUMC) VALUES (?1, ?2)", nativeQuery = true)
	void teste12insert(Integer Numb1, Integer Numc2);

	@Query(value = "UPDATE Teste1 SET NUMC = ?1 WHERE NUMB = 1003 AND numa < 5 ", nativeQuery = true)
	void teste13update(Integer Suma1);

	@Query(value = "DELETE FROM Teste1 WHERE NUMB = ?1 ", nativeQuery = true)
	void teste14delete(Integer Numa1);

}

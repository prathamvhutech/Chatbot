package com.hutech.faq.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hutech.faq.entity.Questions;

public interface ChatRepository extends JpaRepository<Questions, Integer> {

	Questions findByQuestion(String question);

	@Modifying
	//@Query("DELETE FROM Questions e WHERE e.id=(SELECT MIN(id) FROM Questions)")
    @Query("DELETE FROM Questions e WHERE e.id = (SELECT MiN(id) FROM Questions)")
	void deleteFirstData();
}

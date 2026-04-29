package com.miniproject.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.miniproject.entities.ChatSession;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    @Query("SELECT s FROM ChatSession s WHERE s.username = :username ORDER BY s.createdAt DESC")
    List<ChatSession> findByUsername(@Param("username") String username);

}

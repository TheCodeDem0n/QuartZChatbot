package com.miniproject.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.miniproject.entities.ChatMessage;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.session.id = :sessionId ORDER BY m.timestamp ASC")
    List<ChatMessage> findBySessionId(@Param("sessionId") Long sessionId);

}

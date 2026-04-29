package com.miniproject.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatMessage {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  @Column(columnDefinition = "LONGTEXT")
  private String message;

  @Column(columnDefinition = "LONGTEXT")
  private String response;

  private LocalDateTime timestamp;
  @ManyToOne
  @JoinColumn(name = "session_id")
  @JsonBackReference
  private ChatSession session;

  public ChatSession getSession() { 
	  return session; 
	  }
  public void setSession(ChatSession session) { 
	  this.session = session; 
	  }

  public Long getId() {
	return id;
}
  public void setId(Long id) {
	this.id = id;
  }
  public String getUsername() {
	return username;
  }
  public void setUsername(String username) {
	this.username = username;
  }
  public String getMessage() {
	return message;
  }
  public void setMessage(String message) {
	this.message = message;
  }
  public String getResponse() {
	return response;
  }
  public void setResponse(String response) {
	this.response = response;
  }
  public LocalDateTime getTimestamp() {
	return timestamp;
  }
  public void setTimestamp(LocalDateTime timestamp) {
	this.timestamp = timestamp;
  }
  
}


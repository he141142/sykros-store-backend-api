package com.example.sykrosstore.entities;


import java.util.Date;
import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

public class BaseEntity {

  @Column(name ="created_at")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date createdAt = new Date();

  @Column(name ="updated_at")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date updatedAt = new Date();

  @Column(name = "is_deleted")
  private Boolean isDeleted;
  
}

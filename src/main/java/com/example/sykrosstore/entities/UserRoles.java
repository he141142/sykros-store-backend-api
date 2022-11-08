package com.example.sykrosstore.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "user_role")
public class UserRoles  extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  Account account;

  @ManyToOne
  @JoinColumn(name = "role_id")
  Role role;
}

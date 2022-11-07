package com.example.sykrosstore.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.sykrosstore.constants.common.controller.auth.AccountConstants;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Entity
public class Account extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull()
  @NotEmpty
  @Pattern(regexp = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
  String password;

  @Column(name = "user_name")
  @NotNull
  @NotEmpty
  String userName;

  @NotNull @NotEmpty String status;

  @Column(name = "password_hash")
  String passwordHash;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  private List<Customers> customersList = new ArrayList<>();

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  private List<Publisher> publishers = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "account_id"))
  Set<Role> roles = new HashSet<>();

  public Account setPassWordEncoder(PasswordEncoder encoder){
    this.passwordHash = encoder.encode(this.password);
    return this;
  }

  public Account setRole(List<Role> roles){
      roles.forEach(role -> {
        this.getRoles().add(role);
      });
      return this;
  }

  public Account buildCustomer(Customers customer){
    List<Customers> customers1 = new ArrayList<>();
    customer.setAccount(this);
    customers1.add(customer);
      this.setCustomersList(customers1);
      return this;
  }

  public static Account buildForSignup(
          String password,
          String userName
  ){
    Account account = new Account();
    account.setPassword(password);
    account.setStatus(AccountConstants.ACCOUNT_STATUS_ACTIVE);
    account.setUserName(userName);
    return account;
  }

}

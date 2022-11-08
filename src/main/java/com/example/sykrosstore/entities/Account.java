package com.example.sykrosstore.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.sykrosstore.constants.common.controller.auth.AccountConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
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


  @JsonIgnore
  @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private List<UserRoles> userRoles = new ArrayList<>();

  public Account setPassWordEncoder(PasswordEncoder encoder){
    this.passwordHash = encoder.encode(this.password);
    return this;
  }



  public Account setRole(List<Role> roles){
    List<UserRoles> rolesList = new ArrayList<>();
      roles.forEach(role -> {
        UserRoles userRolesEntity = new UserRoles();
        userRolesEntity.setRole(role);
        userRolesEntity.setAccount(this);
        rolesList.add(userRolesEntity);
      });
      this.setUserRoles(rolesList);
    System.out.println(this.getUserRoles().get(0).getRole().getName());
      return this;
  }

  public Account buildCustomer(Customers customer){
    List<Customers> customers1 = new ArrayList<>();
//    customer.setAccount(this);
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

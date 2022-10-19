package com.example.sykrosstore.entities;

import com.example.sykrosstore.configuration.ImportResourceService;
import com.example.sykrosstore.configuration.InitialLoad.Constants;
import com.example.sykrosstore.helper.gson.GsonModule;
import com.example.sykrosstore.helper.reflect.ReflectCustom;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.FileNotFoundException;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.apache.tomcat.util.json.ParseException;
import org.hibernate.Hibernate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Data
@Entity
public class Role extends BaseEntity {
    public Role() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty @NotNull String name;

    @Column
    String description;

    @Column
    @NotNull int authority;
    @Column
    @Nullable
    int amount;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Column(name = "created_at")
    @NotNull
    private Date createdAt = new Date();

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Column(name = "updated_at")
    @NotNull
    private Date updatedAt = new Date();

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Account> accounts = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Role> loadDefault() throws FileNotFoundException, ParseException {
        AbstractApplicationContext abCtx = new ClassPathXmlApplicationContext("role.xml");
        ImportResourceService importResourceServiceXML = (ImportResourceService) abCtx.getBean(ImportResourceService.class);
        GsonModule gsonRole = new GsonModule.SykrosGsonBuilder<Role>().SetClass(Role.class).SetFields(
                (ArrayList<String>) ReflectCustom.getFieldsOfClass(Role.class)).pluginFile(
                importResourceServiceXML.getFileName(Constants.LOAD_TYPE.ROLE),
                importResourceServiceXML.getFileLocation(Constants.LOAD_TYPE.ROLE)
        ).initKeyMapper(new String[][]{
                {"description", "name"}, {"description", "description"},
                {"authority", "authorities"}
        }).SetClassBuilder(Role.class);
        return gsonRole.deserializeArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

}

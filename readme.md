# GSON MODULE:

## SETUP

### STEP 1:Add file .json to resource file

Example:  src/main/resources/roles.json

```json
[
  {
    "name": "ADMIN",
    "description": "admin control the system",
    "authorities": 100
  },
  {
    "name": "USER",
    "description": "Base role for authentication, basic service",
    "authorities": 50
  },
  {
    "name": "PUBLISHER",
    "description": "Control and manage their own books",
    "authorities": 50
  },
  {
    "name": "CUSTOMER",
    "description": "Service for customer",
    "authorities": 70
  },
  {
    "name": "AUTHOR",
    "description": "Support this role in the future",
    "authorities": 70
  },
  {
    "name": "GUEST",
    "description": "Limit the feature by user have this role",
    "authorities": 20
  }
]
```

### STEP2: Config role.xml

ImportResourceService.java

```java
public class ImportResourceService {
    private RoleInitialLoad roleInitialLoad;

    public RoleInitialLoad getRoleInitialLoad() {
        return roleInitialLoad;
    }

    public void setRoleInitialLoad(RoleInitialLoad roleInitialLoad) {
        this.roleInitialLoad = roleInitialLoad;
    }

    public String getInitialFilePath(Constants.LOAD_TYPE load_type) {
        switch (load_type) {
            case ROLE:
                return this.roleInitialLoad.getLocation() + this.roleInitialLoad.getFileName();
            default:
                return "";
        }
    }

    public String getFileName(Constants.LOAD_TYPE load_type) {
        if (load_type == Constants.LOAD_TYPE.ROLE) {
            return this.roleInitialLoad.getFileName();
        }
        return "";
    }

    public String getFileLocation(Constants.LOAD_TYPE load_type) {
        if (load_type == Constants.LOAD_TYPE.ROLE) {
            return this.roleInitialLoad.getLocation();
        }
        return "";
    }
}
```

create InitialLoad class
Example: RoleInitialLoad.java

```java

@Component
public class RoleInitialLoad extends InitialLoad {
    String location;
    String fileName;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
```

add to xml Bean (role.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="importResourceService2" class="com.example.sykrosstore.configuration.ImportResourceService"
          autowire="byName">
    </bean>
    <bean id="roleInitialLoad" class="com.example.sykrosstore.configuration.InitialLoad.RoleInitialLoad">
        <property name="location" value="D:/book-store/sykros-store/src/main/resources"/>
        <property name="fileName" value="/roles.json"/>
    </bean>

</beans>
```

### STEP3: ADD ENTITY CLASS

entities/role.java

```java

@Entity
public class Role extends BaseEntity {
    public Role() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty
    @NotNull
    String name;

    @Column
    String description;

    @Column
    @NotNull
    int authority;
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
```

### STEP4: Convert JSON to Entity

```java
public class TestModule {
    public void process() throws FileNotFoundException, JSONException, ParseException {
        AbstractApplicationContext abCtx = new ClassPathXmlApplicationContext("role.xml");
        ImportResourceService importResourceServiceXML = (ImportResourceService) abCtx.getBean(ImportResourceService.class);
        GsonModule gsonRole = new SykrosGsonBuilder<Role>().SetClass(Role.class).SetFields(
                (ArrayList<String>) ReflectCustom.getFieldsOfClass(Role.class)).pluginFile(
                importResourceServiceXML.getFileName(Constants.LOAD_TYPE.ROLE),
                importResourceServiceXML.getFileLocation(Constants.LOAD_TYPE.ROLE)
        ).initKeyMapper(new String[][]{
                {"description", "name"}, {"description", "description"},
                {"authority", "authorities"}
        }).SetClassBuilder(Role.class);
        ArrayList<Role> roles = gsonRole.deserializeArray();
        printRoles(roles);

    }


    public void printRoles(ArrayList<Role> roles) {
        System.out.printf("%-20s%-20s%-150s\n", "name", "authority", "description");
        for (int i = 0; i < roles.size(); i++) {
            System.out.printf("%-20s%-20s%-150s\n", roles.get(i).getName(), roles.get(i).getAuthority(), roles.get(i).getDescription());
        }
    }
}
```

#### Main.java

```java

@SpringBootApplication
@ImportResource({"classpath:file-config.xml"})
public class SykrosStoreApplication {
    public static void main(String[] args)
            throws FileNotFoundException, JSONException, ParseException {
        new TestModule().process();
    }
}
```

#### Output:
```text
name                authority           description                                                                                                                                           
ADMIN               100                 admin control the system                                                                                                                              
USER                50                  Base role for authentication, basic service                                                                                                           
PUBLISHER           50                  Control and manage their own books                                                                                                                    
CUSTOMER            70                  Service for customer                                                                                                                                  
AUTHOR              70                  Support this role in the future                                                                                                                       
GUEST               20                  Limit the feature by user have this role              
```

## HOW TO SETUP GSON MODULE?
- 1: Config module Document
```bash
SykrosGsonBuilder<T>: module builder
public SykrosGsonBuilder SetClass(Class<T> typeParam) #use to set class to convert
public SykrosGsonBuilder SetFields(ArrayList<String> fields) #use to set fields
public SykrosGsonBuilder pluginFile(String fileName, String fileLocation) #add file json to read
public SykrosGsonBuilder initKeyMapper(String[][] keyMapper) #add mapper to map field in JSON file to field in Entity.class
```
Example:
```bash
 GsonModule gsonRole = new SykrosGsonBuilder<Role>().SetClass(Role.class).SetFields(
                (ArrayList<String>) ReflectCustom.getFieldsOfClass(Role.class)).pluginFile(
                importResourceServiceXML.getFileName(Constants.LOAD_TYPE.ROLE),
                importResourceServiceXML.getFileLocation(Constants.LOAD_TYPE.ROLE)
        ).initKeyMapper(new String[][]{
                {"description", "name"}, {"description", "description"},
                {"authority", "authorities"}
        }).SetClassBuilder(Role.class);
```
- 2: Start convert to object
```bash
  ArrayList<Role> roles = gsonRole.deserializeArray();
 ```
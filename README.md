# spring-boot-liquibase-mybatisplus

Springboot整合liquibase和mybatis-plus实现数据库操作

所需项目以来pom.xml
<!--liquibase-->    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.liquibase</groupId>
            <artifactId>liquibase-core</artifactId>
        </dependency>
    <!--mybatis plus-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0-RELEASE</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>


配置application.yml
spring:
  datasource:
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?serverTimezone=UTC
    password: 157382
  liquibase:
    change-log: classpath:liquibase/master.xml
    enabled: true
#mybatis-plus
  mybatis-plus.mapper-locations=classpath:mapper/xml/*.xml
  mybatis-plus.type-aliases-package=com.hand.demo.pojo


配置master.xml文件
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
    <include file="classpath:db/changelog/release.xml" relativeToChangelogFile="false"/>

</databaseChangeLog>


配置release.xml文件实现建表
<?xml version="1.0" encoding="utf-8"?>
<databaseChangeLog
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                      http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.4.xsd">
    <property name="now" value="now()" dbms="mysql,h2"/>
    <property name="now" value="sysdate" dbms="oracle"/>
    <property name="autoIncrement" value="true" dbms="mysql,h2,postgresql,oracle"/>
    <property name="amount" value="decimal(20,2)"/>

    <changeSet id="25227" author="zmk">
        <createTable tableName="department" remarks="部门表">
            <column name="id" type="int">
                <constraints nullable="false" primaryKey="true"/>
            </column>
            <column name="name" type="varchar2(50)" remarks="部门名称"/>
            <column name="active" type="boolean" remarks="是否活跃"/>
        </createTable>
    </changeSet>
</databaseChangeLog>


建立实体类：
@TableName("department")
public class Department {
    @TableId(value = "id")
    private int id;
    @TableField
    private String name;
    @TableField
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}


创建mapper
@Component
public interface TestMapper extends BaseMapper<Department> { }


编写service
#服务接口

public interface TestService {

    public Department selectDepartment(int id);
}


#服务实现类
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public Department selectDepartment(int id) {
        return testMapper.selectById(id);
    }
}

编写Controller
@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @GetMapping("/getDepartment")
    public Department getDepartmentInfo(@RequestParam(name = "id") int id) {
        System.out.println(id);
        Department department = testService.selectDepartment(id);
        return testService.selectDepartment(id);
    }
}


最后在springboot的运行程序上添加注解@MapperScan("com.hand.demo.dao")
@SpringBootApplication
@MapperScan("com.hand.demo.dao")
public class DemoSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootApplication.class, args);
    }
}

完成，运行程序即可

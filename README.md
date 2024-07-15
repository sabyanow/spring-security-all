# Spring_security_all_local
 
SPRINGSECWITHJDBCV1SS project is the starting point of spring security POC . It uses Spring boot 2 along with spring security 5 .
 We will be using h2 in memory database.
 
 ::project structure and goal for intitial commit ::
 
 We have a HomeResource.java controller which has protected methods . 
 With spring secutiy we want to achive authication before accesing any of controller methods .
 
 Step1 : 
 put schema.sql and data.sql in resource folder 
 
 Schema.sql 
 
 create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

data .sql 

INSERT INTO users( username, password, enabled)
values('user','pass',true);

INSERT INTO users( username, password, enabled)
values('admin','pass',true);


INSERT INTO authorities(username, authority)
values('user', 'ROLE_USER');


INSERT INTO authorities(username, authority)
values('admin', 'ROLE_ADMIN');

in application.properties do h2 config as below 

spring.application.name=SPRINGSECWITHJDBCV1SS

spring.h2.console.enabled=true

spring.datasource.url=jdbc:h2:mem:testdb1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

in SpringsecurityConfiguration.java extend WebSecurityConfigurerAdapter and override configure method 
and create bean of NoOpPasswordEncoder as we are not using any encoding for password .

once application is up .try hitting http://localhost:8080/ , it try to invole below method of controller 

@GetMapping("/")
    public String home() {
        return("<h1>Welcome</h1>");
    }
	
but spring secutity kicks in and log in form is displayed . Log in with user given in data.sql and then try to access this page (http://localhost:8080/) once again .Success this time .
to log out use below URL 

(http://localhost:8080/logout
	

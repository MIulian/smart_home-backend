#Creat de Iulian
#Versiunea 01

create table commandBoard (
board_id int auto_increment primary key,
board_name varchar(255),
board_serial varchar(255) unique,
board_start time,
board_auto_start int,
board_contor int,
board_off int,
UNIQUE KEY unique_serial (board_serial)
);

CREATE TABLE CONNECTIONS (
CONNECTION_ID int auto_increment primary key,
FK_USER_ID int,
FK_BOARD_ID int,
foreign key (FK_USER_ID) references USER(ID),
foreign key (FK_BOARD_ID) references commandboard(board_id)
);

CREATE TABLE USER(
id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(255) NOT NULL,
email_address VARCHAR(255), 
password VARCHAR(255) NOT NULL,
firstName VARCHAR(255),
lastName VARCHAR(255),
age INT,
created_by VARCHAR (255) not null,
created_date DATE not null,
modify_by VARCHAR (255),
modify_date DATE,
address VARCHAR (255),
phone INT,
UNIQUE KEY unique_email (email),
UNIQUE KEY unique_username (username));

#parola: 12345
INSERT INTO user (username, email_address, password, firstname, lastname, age, created_by, created_date, address, phone) 
VALUES ('iulian01', 'iulian@domaine.ro', '$2a$10$IowXzryzKxbNUM8TXk/FqO/L5.pWUebBcDgZpaJvJChZBKkn8Trtq', 'Iulian', 'M', 30, 'Iulian',
str_to_date('01-11-2019','%d-%m-%Y'), 'Timisoara 46', 0748123456);

INSERT INTO user (username, email_address, password, firstname, lastname, age, created_by, created_date, address, phone) 
VALUES ('iulica01', 'iulica@domaine.ro', '$2a$10$IowXzryzKxbNUM8TXk/FqO/L5.pWUebBcDgZpaJvJChZBKkn8Trtq', 'Iulica', 'M', 2, 'Iulica',
str_to_date('11-11-2019','%d-%m-%Y'), 'Timisoara 46', 074987654543);

INSERT INTO user (username, email_address, password, firstname, lastname, age, created_by, created_date, address, phone) 
VALUES ('ana01', 'aan@domaine.ro', '$2a$10$IowXzryzKxbNUM8TXk/FqO/L5.pWUebBcDgZpaJvJChZBKkn8Trtq', 'Ana', 'N', 20, 'Ana',
str_to_date('20-11-2019','%d-%m-%Y'), 'Timisoara 46', 0987098);

insert into commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off)
values('priza1','aa00001', '08:00:00', 0, 1, 0);

insert into commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off)
values('priza2','aa00002', '09:00:00', 0, 0, 0);

insert into commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off)
values('priza3','aa00003', '06:00:00', 0, 1, 0);

insert into commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off)
values('intrerupator1','ab00001', '07:00:00', 0, 0, 1);

insert into commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off)
values('intrerupator1','ab00002', '07:30:00', 0, 1, 0);

insert into connections (fk_user_id, fk_board_id) values(1, 1);

insert into connections (fk_user_id, fk_board_id) values(1, 2);

insert into connections (fk_user_id, fk_board_id) values(2, 1);

insert into connections (fk_user_id, fk_board_id) values(1, 5);

insert into connections (fk_user_id, fk_board_id) values(2, 4);



DROP SCHEMA parserdb;

COMMIT;

CREATE DATABASE IF NOT EXISTS parserdb;
 
USE parserdb;

CREATE TABLE requester (
	id int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ip_address varchar(20) NOT NULL UNIQUE,
	status varchar(50) NOT NULL
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE request (
	id int(12) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	request_date DATETIME(3) NOT NULL,
	request_description varchar(100) NOT NULL,
 	request_status varchar(100) NOT NULL,
	request_user_agent varchar(200) NOT NULL,
	id_requester int(10) NOT NULL,
	FOREIGN KEY fk1_id_requester(id_requester) REFERENCES requester(id) ON UPDATE CASCADE ON DELETE RESTRICT
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE report (
	id int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	report_date DATETIME(3) NOT NULL,
	start_date DATETIME(3) NOT NULL,
	duration varchar(20) NOT NULL,
    threshold int(8) NOT NULL
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE report_detail (
	id int(12) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 	status varchar(50) NOT NULL,
	request_count int(10) NOT NULL,
	id_report int(10) NOT NULL,
    id_requester int(10) NOT NULL,
    FOREIGN KEY fk2_id_report(id_report) REFERENCES report(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY fk3_id_requester(id_requester) REFERENCES requester(id) ON UPDATE CASCADE ON DELETE RESTRICT
) DEFAULT CHARSET=utf8;

COMMIT;
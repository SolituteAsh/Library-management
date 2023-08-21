DROP TABLE Borrow;
DROP TABLE Books;
DROP TABLE Members;
DROP TABLE Publisher;
DROP TABLE Location;
DROP TABLE Author;


CREATE TABLE Author (
  Author_id NUMBER PRIMARY KEY,
  Author_name VARCHAR2(100) NOT NULL,
  nationality VARCHAR2(50) CHECK (nationality IN ('indian', 'foreign'))
);


CREATE TABLE Location (
  Street_name VARCHAR2(100) PRIMARY KEY,
  pincode VARCHAR2(10) NOT NULL
);


CREATE TABLE Publisher (
  Publisher_id NUMBER PRIMARY KEY,
  Publisher_name VARCHAR2(100) NOT NULL,
  contact_no VARCHAR2(20) NOT NULL,
  street VARCHAR2(100) REFERENCES Location(Street_name),
  city VARCHAR2(50) NOT NULL,
  state VARCHAR2(50) NOT NULL
);


CREATE TABLE Books (
  Book_id NUMBER PRIMARY KEY,
  Book_name VARCHAR2(100) NOT NULL,
  Author_id NUMBER REFERENCES Author(Author_id),
  Publisher_id NUMBER REFERENCES Publisher(Publisher_id),
  qty NUMBER NOT NULL
);


CREATE TABLE Members (
  Member_id NUMBER PRIMARY KEY,
  Member_name VARCHAR2(100) NOT NULL,
  phone_number VARCHAR2(20) NOT NULL,
  street VARCHAR2(100) REFERENCES Location(Street_name),
  city VARCHAR2(50) NOT NULL,
  state VARCHAR2(50) NOT NULL,
  dues VARCHAR2(3) CHECK (dues IN ('yes', 'no'))
);


CREATE TABLE Borrow (
  Book_id NUMBER REFERENCES Books(Book_id),
  Member_id NUMBER REFERENCES Members(Member_id),
  check_out_date DATE NOT NULL,
  Return_date DATE,
  status VARCHAR2(20) CHECK (status IN ('Returned', 'Not Returned'))
);



INSERT INTO Author VALUES (1, 'Author 1', 'indian');
INSERT INTO Author VALUES (2, 'Author 2', 'foreign');
INSERT INTO Author VALUES (3, 'Author 3', 'indian');
INSERT INTO Author VALUES (4, 'Author 4', 'indian');
INSERT INTO Author VALUES (5, 'Author 5', 'foreign');
INSERT INTO Author VALUES (6, 'Author 6', 'indian');
INSERT INTO Author VALUES (7, 'Author 7', 'foreign');
INSERT INTO Author VALUES (8, 'Author 8', 'indian');
INSERT INTO Author VALUES (9, 'Author 9', 'foreign');
INSERT INTO Author VALUES (10, 'Author 10', 'indian');
COMMIT;


INSERT INTO Location VALUES ('Street 11', '111111');
INSERT INTO Location VALUES ('Street 12', '222222');
INSERT INTO Location VALUES ('Street 13', '333333');
INSERT INTO Location VALUES ('Street 14', '444444');
INSERT INTO Location VALUES ('Street 15', '555555');
INSERT INTO Location VALUES ('Street 16', '666666');
INSERT INTO Location VALUES ('Street 17', '777777');
INSERT INTO Location VALUES ('Street 18', '888888');
INSERT INTO Location VALUES ('Street 19', '999999');
INSERT INTO Location VALUES ('Street 20', '000000');
INSERT INTO Location VALUES ('Street 1', '1111110');
INSERT INTO Location VALUES ('Street 2', '2222220');
INSERT INTO Location VALUES ('Street 3', '3333330');
INSERT INTO Location VALUES ('Street 4', '4444440');
INSERT INTO Location VALUES ('Street 5', '5555550');
INSERT INTO Location VALUES ('Street 6', '6666660');
INSERT INTO Location VALUES ('Street 7', '7777770');
INSERT INTO Location VALUES ('Street 8', '8888880');
INSERT INTO Location VALUES ('Street 9', '9999990');
INSERT INTO Location VALUES ('Street 10', '0000000');
COMMIT;


INSERT INTO Publisher VALUES(1, 'Publisher 1', '1111111111', 'Street 11', 'City 11', 'State 11');
INSERT INTO Publisher VALUES(2, 'Publisher 2', '2222222222', 'Street 12', 'City 12', 'State 12');
INSERT INTO Publisher VALUES(3, 'Publisher 3', '3333333333', 'Street 13', 'City 13', 'State 13');
INSERT INTO Publisher VALUES(4, 'Publisher 4', '4444444444', 'Street 14', 'City 14', 'State 14');
INSERT INTO Publisher VALUES(5, 'Publisher 5', '5555555555', 'Street 15', 'City 15', 'State 15');
INSERT INTO Publisher VALUES(6, 'Publisher 6', '6666666666', 'Street 16', 'City 16', 'State 16');
INSERT INTO Publisher VALUES(7, 'Publisher 7', '7777777777', 'Street 17', 'City 17', 'State 17');
INSERT INTO Publisher VALUES(8, 'Publisher 8', '8888888888', 'Street 18', 'City 18', 'State 18');
INSERT INTO Publisher VALUES(9, 'Publisher 9', '9999999999', 'Street 19', 'City 19', 'State 19');
INSERT INTO Publisher VALUES(10, 'Publisher 10', '0000000000', 'Street 20', 'City 20', 'State 20');
COMMIT;


INSERT INTO Books VALUES (1, 'Book1', 1, 1, 10);
INSERT INTO Books VALUES (2, 'Book2', 2, 2, 5);
INSERT INTO Books VALUES (3, 'Book3', 3, 1, 8);
INSERT INTO Books VALUES (4, 'Book4', 1, 3, 12);
INSERT INTO Books VALUES (5, 'Book5', 2, 2, 3);
INSERT INTO Books VALUES (6, 'Book6', 3, 1, 15);
INSERT INTO Books VALUES (7, 'Book7', 2, 3, 6);
INSERT INTO Books VALUES (8, 'Book8', 1, 1, 9);
INSERT INTO Books VALUES (9, 'Book9', 3, 2, 7);
INSERT INTO Books VALUES (10, 'Book10', 1, 2, 4);
COMMIT;


INSERT INTO Members VALUES (1, 'John Doe', '1234567890', 'Street 1', 'City 1', 'State 1', 'yes');
INSERT INTO Members VALUES (2, 'Jane Smith', '9876543210', 'Street 2', 'City 2', 'State 2', 'no');
INSERT INTO Members VALUES (3, 'Michael Johnson', '5555555555', 'Street 3', 'City 3', 'State 3', 'yes');
INSERT INTO Members VALUES (4, 'Sarah Williams', '1112223333', 'Street 4', 'City 4', 'State 4', 'no');
INSERT INTO Members VALUES (5, 'Robert Brown', '9998887777', 'Street 5', 'City 5', 'State 5', 'yes');
INSERT INTO Members VALUES (6, 'Emily Davis', '4445556666', 'Street 6', 'City 6', 'State 6', 'no');
INSERT INTO Members VALUES (7, 'Daniel Taylor', '7778889999', 'Street 7', 'City 7', 'State 7', 'yes');
INSERT INTO Members VALUES (8, 'Olivia Clark', '2221110000', 'Street 8', 'City 8', 'State 8', 'no');
INSERT INTO Members VALUES (9, 'James Wilson', '3334445555', 'Street 9', 'City 9', 'State 9', 'yes');
INSERT INTO Members VALUES (10, 'Sophia Turner', '6667778888', 'Street 10', 'City 10', 'State 10', 'no');
COMMIT;


INSERT INTO Borrow VALUES(1, 1, TO_DATE('2022-01-01', 'YYYY-MM-DD'), TO_DATE('2022-02-01', 'YYYY-MM-DD'), 'Returned');
INSERT INTO Borrow VALUES(2, 2, TO_DATE('2022-03-01', 'YYYY-MM-DD'), TO_DATE('2022-04-01', 'YYYY-MM-DD'), 'Returned');
INSERT INTO Borrow VALUES(3, 1, TO_DATE('2022-05-01', 'YYYY-MM-DD'), TO_DATE('2022-06-01', 'YYYY-MM-DD'), 'Not Returned');
INSERT INTO Borrow VALUES(4, 3, TO_DATE('2022-07-01', 'YYYY-MM-DD'), TO_DATE('2022-08-01', 'YYYY-MM-DD'), 'Returned');
INSERT INTO Borrow VALUES(5, 2, TO_DATE('2022-09-01', 'YYYY-MM-DD'), TO_DATE('2022-10-01', 'YYYY-MM-DD'), 'Not Returned');
INSERT INTO Borrow VALUES(6, 4, TO_DATE('2022-11-01', 'YYYY-MM-DD'), TO_DATE('2022-12-01', 'YYYY-MM-DD'), 'Returned');
INSERT INTO Borrow VALUES(7, 5, TO_DATE('2023-01-01', 'YYYY-MM-DD'), TO_DATE('2023-02-01', 'YYYY-MM-DD'), 'Not Returned');
INSERT INTO Borrow VALUES(8, 6, TO_DATE('2023-03-01', 'YYYY-MM-DD'), TO_DATE('2023-04-01', 'YYYY-MM-DD'), 'Returned');
INSERT INTO Borrow VALUES(9, 3, TO_DATE('2023-05-01', 'YYYY-MM-DD'), TO_DATE('2023-06-01', 'YYYY-MM-DD'), 'Not Returned');
INSERT INTO Borrow VALUES(10, 7, TO_DATE('2023-07-01', 'YYYY-MM-DD'), TO_DATE('2023-08-01', 'YYYY-MM-DD'), 'Returned');
COMMIT;



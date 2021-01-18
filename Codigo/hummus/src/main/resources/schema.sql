DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    create_at DATE NOT NULL ,
    password VARCHAR(100) NOT NULL
);

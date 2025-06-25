USE prj2;

CREATE TABLE board
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(100)   NOT NULL,
    content    VARCHAR(10000) NOT NULL,
    writer     VARCHAR(50)    NOT NULL,
    created_at DATETIME DEFAULT NOW()
);
DROP TABLE board;
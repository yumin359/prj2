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
# TODO : 삭제되면 바뀔 번호?는 따로 만들어야 하나 현재는 id가 고정임

# 페이징용 글 복사
INSERT INTO board(title, content, writer)
SELECT title, content, writer
FROM board;
SELECT COUNT(*)
FROM board;
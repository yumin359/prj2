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

CREATE TABLE member
(
    id         VARCHAR(30) PRIMARY KEY,
    password   VARCHAR(30) NOT NULL UNIQUE,
    name       VARCHAR(30) NOT NULL,
    nick_name  VARCHAR(30) NOT NULL UNIQUE,
    birth_date DATE        NOT NULL,
    info       VARCHAR(1000)
);

# 회원만 글을 작성할 수 있고,
# 자기가 쓴글은 자신만 수정/삭제 할 수 있으므로
# board.writer를 member.id로 수정해야함
# -> 외래키 제약사항 추가
# 한명의 회원은 여러개의 게시물을 작성할 수 있으므로 member:board=1:N관계
# 즉 board 테이블에 writer를 FK로 설정 references member(id)

# 위와 같은 것이 잘 되는지 확인하기 위해 작성자를 cho(member.id) 또는 min(member.id) 으로 바꿔줌

UPDATE board
SET writer = 'cho'
WHERE id % 2 = 1;
UPDATE board
SET writer = 'min'
WHERE id % 2 = 0;

# 외래키 제약사항 추가
ALTER TABLE board
    ADD FOREIGN KEY (writer) REFERENCES member (id);
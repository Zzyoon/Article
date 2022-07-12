--article 더미 데이터
INSERT INTO article(id, title, content) VALUES(1, '가가가가', '1111');
INSERT INTO article(id, title, content) VALUES(2, '나나나나', '2222');
INSERT INTO article(id, title, content) VALUES(3, '다다다다', '3333');
INSERT INTO article(id, title, content) VALUES(4, '인생 영화는?', '댓글 ㄱ');
INSERT INTO article(id, title, content) VALUES(5, '인생 음식는?', '댓글 ㄱㄱ');
INSERT INTO article(id, title, content) VALUES(6, '취미는?', '댓글 ㄱㄱㄱ');

--comment 더미 데이터
INSERT INTO comment(id, article_id, nickname, body ) VALUES(1,4, 'aaa', 'matrix');
INSERT INTO comment(id, article_id, nickname, body ) VALUES(2,4, 'ab', 'hello');
INSERT INTO comment(id, article_id, nickname, body ) VALUES(3,4, 'cc', 'matrix');

INSERT INTO comment(id, article_id, nickname, body ) VALUES(4,5, 'aa', 'taco');
INSERT INTO comment(id, article_id, nickname, body ) VALUES(5,5, 'bd', 'piizza');
INSERT INTO comment(id, article_id, nickname, body ) VALUES(6,5, 'ccc', 'hi');

INSERT INTO comment(id, article_id, nickname, body ) VALUES(7,6, 'ac', 'surf');
INSERT INTO comment(id, article_id, nickname, body ) VALUES(8,6, 'bb', 'movie');
INSERT INTO comment(id, article_id, nickname, body ) VALUES(9,6, 'cc', 'dance');


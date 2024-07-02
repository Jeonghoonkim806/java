DROP TABLE color;

CREATE TABLE color (
	seq          NUMBER             PRIMARY KEY,     --색상 번호
	title        VARCHAR2(300)      NOT NULL,        --색상 제목
	content      CLOB               NOT NULL,        --색상 내용
	create_date  DATE               DEFAULT sysdate, --색상 등록일자(값이 없으면 현재 날짜로 등록됨)
    read_count   NUMBER                              --색상 조회수
);

--comment 등록 sql
COMMENT ON COLUMN color.seq IS '색상 번호';
COMMENT ON COLUMN color.title IS '색상 제목';
COMMENT ON COLUMN color.content IS '색상 내용';
COMMENT ON COLUMN color.create_date IS '색상 등록일자';
COMMENT ON COLUMN color.read_count IS '색상 조회수';

--sequence생성
CREATE SEQUENCE seq_board_no 
	   INCREMENT BY 1 
	   START WITH 1
;

ALTER TABLE color ADD update_date DATE;

-- 회원 테이블
CREATE TABLE member(
	seq        		NUMBER     		    PRIMARY KEY,  	
	name      		VARCHAR2(300)		NOT NULL,	    
	create_date		DATE                DEFAULT sysdate 
);

-- board테이블에 글 작성한 회원번호 컬럼 추가
ALTER TABLE board ADD member_seq NUMBER;

-- board와 member테이블간의 fk설정
ALTER TABLE board ADD CONSTRAINT fk_board_member
FOREIGN KEY(member_seq) REFERENCES member(seq)
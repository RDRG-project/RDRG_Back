CREATE DATABASE rdrg_db;

USE rdrg_db;

-- 이메일 인증 테이블 생성
CREATE TABLE email_auth_number (
    email VARCHAR(100) PRIMARY KEY, # 유저 이메일 (기본키)
    auth_number VARCHAR(4) NOT NULL # 인증번호
);


# 유저(사용자) 테이블 생성
CREATE TABLE user ( 
    user_id VARCHAR(50) PRIMARY KEY, # 유저 아이디 (기본키)
    user_password VARCHAR(255) NOT NULL, # 유저 비밀번호
    user_email VARCHAR(100) NOT NULL UNIQUE, # 유저 이메일 (email_auth_number에 email에서 끌고 올 예정)
    user_role VARCHAR(100) NOT NULL, # 권한 (유저인지 운영자인지)
    join_path VARCHAR(5) NOT NULL, # 유저 가입경로 (그냥 가입했는지 네이버로 가입했는지 카카오로 가입했는지)
    FOREIGN KEY (user_email) REFERENCES email_auth_number(email) ON DELETE CASCADE # 외래키 지정 (user_email <= email_auth_number.email )
);


# IT 기기 관리 테이블 생성
CREATE TABLE devices_status (
    serial_number VARCHAR(255) PRIMARY KEY, # 시리얼 넘버 (기본키)
    model VARCHAR(50) NOT NULL, # 모델명
    name VARCHAR(100) NOT NULL, # 제품명
    device_explain TEXT NOT NULL, # 제품 설명
    type VARCHAR(20) NOT NULL, # 제품 타입 (노트북, 테블릿, 게임기 등등)
    brand VARCHAR(20) NOT NULL, # 제품 브랜드
    price INT NOt NULL, # 대여 가격(1일 기준)
    devices_img_url VARCHAR(255) NOT NULL # 디바이스 이미지 URL
);


# 문의 게시판 테이블 생성
CREATE TABLE board(
    reception_number int PRIMARY key AUTO_INCREMENT, # 접수번호
    status BOOLEAN NOT NULL DEFAULT('false'), # 상태 (접수중, 답변완료)
    title VARCHAR(100) NOT NULL, # 문의글 제목
    contents TEXT NOT NULL, # 문의글 내용
    writer_id VARCHAR(50) NOT NULL, # 작성자 ID (user에 user_id를 가져옴)
    write_datetime DATETIME NOT NULL DEFAULT(now()), # 작성일 (디폴트로 지금 시간 가져옴)
    comment TEXT DEFAULT(NULL), # 문의글 답변 내용
    FOREIGN KEY (writer_id) REFERENCES user(user_id) ON DELETE CASCADE # 외래키 (writer_id <= user.user_id)
);

# 업로드 관련 테이블
CREATE TABLE upload(
    file_number INT PRIMARY KEY AUTO_INCREMENT, # 업로드 넘버 (기본키)
    link_board_number INT NOT NULL, # 보드 테이블과 연결하는 칼럼
    url VARCHAR(255), # 링크 주소
    FOREIGN KEY(link_board_number) REFERENCES board(reception_number) ON DELETE CASCADE # 외래키 지정 (link_board_no <= board.reception_number)
);

# 대여 내역 테이블
CREATE TABLE device_rent_status (
    rent_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT, # 가상 대여 번호   
    rent_user_id VARCHAR(50) NOT NULL, # 사용자 ID (user에 user_id를 가져옴)
    rent_datetime DATETIME NOT NULL, # 대여일자
    rent_return_datetime DATETIME NOT NULL, # 반납일자
    rent_place VARCHAR(10) NOT NULL, # 대여장소
    rent_return_place VARCHAR(10) NOT NULL, # 반납장소
    rent_total_price int NOT NULL, # 총 합 가격
    rent_status VARCHAR(50), # 대여 가능한 상태
    FOREIGN KEY (rent_user_id) REFERENCES user(user_id) ON DELETE CASCADE # 외래키 지정 (rent_user_id <= user.user_id)
);

# 대여 상세 내역 테이블
CREATE TABLE rent_detail (
    rent_detail_number INT PRIMARY KEY AUTO_INCREMENT,
    rent_number INT NOT NULL,
    serial_number VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (rent_number) REFERENCES device_rent_status(rent_number) ON DELETE CASCADE, #  대여내역 테이블의 대여 정보 확인용
);


# 유저삭제시 메일도 삭제되는 트리거
DELIMITER //

CREATE TRIGGER after_user_delete
AFTER DELETE ON user
FOR EACH ROW
BEGIN
    DELETE FROM email_auth_number WHERE email = OLD.user_email;
END //

DELIMITER ;



# 권한 부여
CREATE USER 'developerRDRG'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON rdrg_db.* TO 'developerRDRG'@'%';
-- Active: 1707867285638@@127.0.0.1@3306@rdbg_db

# 데이터 베이스 생성
CREATE DATABASE RDBG_DB;

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
    FOREIGN KEY (user_email) REFERENCES email_auth_number(email) # 외래키 지정 (user_email <= email_auth_number.email )
);


# IT 기기 관리 테이블 생성
CREATE TABLE devices(
    serial VARCHAR(50) PRIMARY KEY, # 시리얼 넘버 (기본키)
    model VARCHAR(50) NOT NULL, # 모델명
    name VARCHAR(100) NOT NULL, # 제품명
    `explain` TEXT NOT NULL, # 제품 설명
    type VARCHAR(20) NOT NULL, # 제품 타입 (노트북, 테블릿, 게임기 등등)
    price INT NOt NULL # 가격
);

# 업로드 관련 테이블
CREATE TABLE upload(
    file_number INT PRIMARY KEY AUTO_INCREMENT, # 업로드 넘버 (기본키)
    url VARCHAR(255) # 링크 주소
);


# 문의 게시판 테이블 생성
CREATE TABLE board(
    reception_number int PRIMARY key AUTO_INCREMENT, # 접수번호
    status BOOLEAN NOT NULL DEFAULT('false'), # 상태 (접수중, 답변완료)
    title VARCHAR(100) NOT NULL, # 문의글 제목
    contents TEXT NOT NULL, # 문의글 내용
    writer_id VARCHAR(50) NOT NULL, # 작성자 ID (user에 user_id를 가져옴)
    writer_datetime DATETIME NOT NULL DEFAULT(now()), # 작성일 (디폴트로 지금 시간 가져옴)
    comment TEXT DEFAULT(NULL), # 문의글 답변 내용
    upload_file INT, # 첨부파일 (upload_file에 file_number를 끌고 올 예정) (화면에 띄울때는 제품명이 뛰어져야함.)
    FOREIGN KEY (writer_id) REFERENCES user(user_id), # 외래키 (writer_id <= user.user_id)
    FOREIGN KEY (upload_file) REFERENCES upload(file_number), # 외래키 지정 (upload_file <= upload.file_number)
    
);

# 대여 내역 테이블
CREATE TABLE rent_detail (
    rent_user_id VARCHAR(50) NOT NULL PRIMARY KEY, # 사용자 ID (user에 user_id를 가져옴)
    rent_serial VARCHAR(50) NOT NULL, # 빌려간 제품명의 시리얼번호 (it_rent에 serial를 가져옴)
    rent_name VARCHAR(50) NOT NULL, # 빌려간 제품명
    rent_type VARCHAR(20) NOT NULL, #
    rent_datetime DATETIME NOT NULL, # 대여일자
    rent_return_datetime DATETIME NOT NULL, # 반납일자
    rent_place VARCHAR(10) NOT NULL, # 대여장소
    rent_return_place VARCHAR(10) NOT NULL, # 반납장소
    rent_total_price int NOT NULL, # 총 합 가격 
    FOREIGN KEY (rent_user_id) REFERENCES user(user_id), # 외래키 지정 (rent_user_id <= user.user_id)
    Foreign Key (rent_serial) REFERENCES devices(serial), # 외래키 지정 (rent_serial <= it_rent.serial)
    FOREIGN KEY (rent_name) REFERENCES devices(name), # 외래키 지정
    FOREIGN KEY (rent_type) REFERENCES devices(type) # 외래키 지정
);
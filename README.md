
# 다노샵 클론코딩 - BACKEND SERVER REPOSITORY [![Build Status](https://travis-ci.com/Loafly/CloneDano.svg?branch=main)](https://travis-ci.com/Loafly/CloneDano)

## [다노샵 클론코딩](http://hanghae99danoclone.shop.s3-website.ap-northeast-2.amazonaws.com/) 서비스 소개

- 건강/다이어트 식품을 판매하는 소셜커머스 플랫폼을 클론코딩 하였습니다.

## 개요

- 명칭 : 다노샵 클론코딩 - SERVER
- 개발 인원 : 프론트(React) 1명, 백엔드(Spring) 1명
- 개발 기간 : 2021.05.16 ~ 2021.05.
- 업무 내용 :
    - 인프라 구축
    - 테이블 설계
    - 빌드 자동화 및 서비스 무중단 배포 구현
    - 회원가입 / 수정 / 탈퇴 api 구현
    - 상품 전체/개별 조회 api 구현
    - 주문하기 / 주문내역 조회 api 구현
    - 장바구니 등록 / 조회 / 삭제 api 구현
    
- 개발 환경 : Springboot 2.4.5, Jdk 1.8, Spring Security, Spring JPA, Junit5
- 배포 환경 : Gradle, Travis CI, AWS S3, AWS CodeDeploy, AWS EC2
- 웹 서버 : Nginx, Tomcat 9.0
- 데이터베이스 : MySQL 8.0.16 (AWS RDS)
- 협업 도구 : Git, Slack
- 코드 분석 도구 : SonarLint, JaCoCo

## 테이블 설계

![테이블 구조](https://user-images.githubusercontent.com/38175978/120225340-03c86800-c280-11eb-92cd-8cbf9066d012.PNG)

## API 설계

- [Detail api document](https://documenter.getpostman.com/view/15605965/TzRYbPMN)

|기능|Method|URL| Request Params / Body|
|:---|:---:|:---:|:---:|
|현재로그인 조회|GET|/api/user||
|로그인|POST|/api/user/login|userName, password|
|회원 가입|POST|/api/user/signup|userName,password, email, nickName, phone|
|회원 수정|PUT|/api/user|password, email, nickName, phone|
|회원 탈퇴|DELETE|/api/user||
|전체 상품조회|GET|/api/product/all||
|인기 상품조회|GET|/api/product/best||
|신상품조회|GET|/api/product/new||
|다노 상품조회|GET|/api/product/dano||
|알뜰 상품조회|GET|/api/product/thrifty||
|무료배송 상품조회|GET|/api/product/free||
|장바구니 조회|GET|/api/cart||
|장바구니 추가|POST|/api/cart|productId, amount|
|장바구니 개별삭제|DELETE|/api/cart/{cartId}||
|장바구니에서 주문하기|POST|/api/cart/order||
|주문내역 조회|GET|/api/order||
|바로 주문하기|POST|/api/order|productId, amount|

## 무중단 배포 전체 구조

<img width="914" alt="스크린샷 2021-05-26 오후 1 29 54" src="https://user-images.githubusercontent.com/60464424/119602623-7c659980-be26-11eb-9ebf-c679d91e135d.png">
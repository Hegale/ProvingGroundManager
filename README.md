# 주행시험장 관리 시스템

## 구현 목록 (12-07 updated)
### 유저 페이지
1. 시험 기능
   - 시험 진행 날짜 선택 (datepicker)
   - 해당 날짜의 주행시험장 예약내역 중 하나 선택
   - 해당 날짜의 차량 대여내역 중 다수 선택 (필요시 car_maximum 확인)
   - 일반적인 파일 및 주행 경로 파일 등록
   - 제목 및 글 내요 입력
   - 필수 작성칸 Not Null 확인
   - 시험 내역 등록
   - 시험 내역 페이지 구현, 내용 확인 페이지 구현 (우선순위 하)
2. 알림창 구현
   - 회원가입, 로그인 성공/실패 시 알림창 구현
   - 주유 페이지에서 잘못된 정보 입력 시 경고창 구현
3. 마이페이지 구현
   - 유저 정보 확인
   - 관리자 페이지 버튼 추가
  
### 관리자 페이지
1. 등록 기능
   - 차량, 시험장, 주유구, 차종 추가 등록 기능
2. 열람 기능
   - 각 테이블에 대한 열람 기능
   - 각 테이블에 대한 검색 기능
   - 검색 결과나 열람 결과에 대해 수정/삭제 진행 가능
   - 차량 대여, 시험장 예약, 주유 기능은 수정/삭제 불가능
  
### 추가기능
1. 시험 결과 등록 페이지
   - 결과 관련 파일 등록
   - 주행경로 등록


## 중간 산출물 (2023-11-30 updated)
: 산출물 파일은 중간산출물/ 경로에 포함함
### 요구사항 명세서
- 로그인/회원가입 및 유저 요구사항
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/2a95fa37-8416-45df-b508-108ccb6b149a)

- 관리자 요구사항
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/e802a5b2-37a2-40b4-83a2-050aec216dfb)

### 아키텍처 설계서
<img width="1184" alt="스크린샷 2023-11-30 14 18 23" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/68a5aa96-86b1-436d-b38a-f505da9ae37e">

### 화면 설계서
- 유저 화면
   - 메인 화면
  ![메인 화면](https://github.com/Hegale/ProvingGroundManager/assets/92227496/96c8e538-84ce-494d-a0dc-8dd4c23ae87b)
   - 시험장 대여
  ![HOME _ 시험장 대여 _ 시험장 선택](https://github.com/Hegale/ProvingGroundManager/assets/92227496/9e73599f-2fe3-4556-95bc-3a777e264eb2)
   ![HOME _ 시험장 대여 _ 대여 날짜 선택](https://github.com/Hegale/ProvingGroundManager/assets/92227496/a811db30-7f6e-4d29-b365-5313a60feb25)
   ![HOME _ 시험장 예약 _ 예약 내역](https://github.com/Hegale/ProvingGroundManager/assets/92227496/da63c5b9-5166-4103-9047-f42dbbd1f776)

: 전체 화면은 중간 산출물/ 에 추가

### 통합 테스트 시나리오
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/d9fd5197-cb2b-4690-9f8f-a7003c727c1c)

### 테이블 정의서
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/ecb532bb-0928-459f-b573-58e55a92af69)

### 인덱스 정의서
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/0cd8c0bc-2241-4d90-9031-8191bab48d18)

### 물리 ERD
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/9c3ef1cb-dd37-41ad-b563-c068b4c045a1)


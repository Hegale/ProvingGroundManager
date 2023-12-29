<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/eed5fd71-1b2e-4742-ba68-ed61a95b2f23"><img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/184a1d3d-2e93-413e-a4b8-662782f5b1b5">![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/94dc40c6-28c0-4777-8044-1c5041ab8c08)# 주행시험장 관리 시스템

## 구현 결과
- 메인 화면
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/e3175911-925a-4239-96f4-6f75b16a00ea">
- 시험장 예약 기능
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/1414dcaa-8521-448d-9aaf-0a4a4dab5ffc">
<img width="981" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/806e8e9b-78d8-4f5f-b22b-60b7976f59f9">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/3382b9f3-e7a9-42a5-b69f-7455508475c3">
- 시험 내역 작성 및 확인 기능
<img width="992" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/59dae435-9235-41da-a381-122615e30a34">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/13101b43-693f-4a6d-aa09-65d80105e1f7">
<img width="992" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/45353ead-559a-450e-895e-e7ea557efeb5">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/50275984-9260-4faa-ac9a-65fa8301bbe8">
- 관리자 기능
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/c0bafe3c-d2d9-4fe4-a662-e04abcfe71a3">
<img width="992" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/ae52aef1-f62f-46cd-8bcc-bfd5b437f673">
<img width="992" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/61db33cd-67a7-402c-8a6e-5927da296157">


## 구현 목록
### 유저 페이지
1. 시험장 대여 기능
   - 시험장 선택
   - 시험장 예약
   - 시험장 예약 내역 확인 및 날짜로 검색
   - 시험장 예약 취소
2. 차량 대여 기능
   - 차종 선택 및 검색
   - 차량 대여
   - 차량 대여 내역 확인 및 날짜로 검색
   - 차량 대여 취소 및 반납
3. 시험 기능
   - 시험 진행 날짜 선택
   - 해당 날짜의 시험장 예약 및 차량 대여 내역 확인
   - 내용 작성
   - 시험 내역 등록
   - 시험 내역 등록 내역 확인
   - 차량 주행경로 등록 및 확인
4. 주유
   - 주유할 차종 선택 및 검색
   - 쥬유
   - 주유 내역 확인
  
### 관리자 페이지
1. 등록 기능
   - 차량, 시험장, 주유구, 차종 추가 등록 기능
2. 열람 기능
   - 각 테이블에 대한 열람 기능
   - 각 테이블에 대한 검색 기능
   - 검색 결과나 열람 결과에 대해 수정/삭제 진행 가능
   - 차량 대여, 시험장 예약, 주유 내역은 수정/삭제 불가능

## 중간 산출물
: 산출물 파일은 중간산출물/ 경로에 포함함
### 요구사항 명세서
- 로그인/회원가입 및 유저 요구사항
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/2a95fa37-8416-45df-b508-108ccb6b149a)

- 관리자 요구사항
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/e802a5b2-37a2-40b4-83a2-050aec216dfb)

### 아키텍처 설계서
<img width="1122" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/8c216ce2-05f1-4052-b3a0-c93aec2554db">

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
- 공통 기능 관련
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/d306c5fb-efa9-4b17-89c7-6c5044859222)
- 유저 기능 관련
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/30c07e96-3c2c-411c-bb60-bb6b2eb90e2a)
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/76063627-3d90-4adc-a6fc-d69bdead1a71)
- 관리자 기능 관련
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/c2cf0bf6-31d9-4140-96e2-a7d646c330c9)


### 테이블 정의서
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/ecb532bb-0928-459f-b573-58e55a92af69)

### 인덱스 정의서
![image](https://github.com/Hegale/ProvingGroundManager/assets/92227496/08233e97-d9fd-4691-9acd-ee4b66ae5a01)

### 물리 ERD
<img width="750" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/4c872d93-5cc9-4724-b8f5-9d0ce2345eae">


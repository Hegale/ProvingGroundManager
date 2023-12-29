# 주행시험장 관리 시스템

   
## 구현 결과
### 유저 기능
- 메인 화면
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/e3175911-925a-4239-96f4-6f75b16a00ea">

- 시험장 예약 기능
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/1414dcaa-8521-448d-9aaf-0a4a4dab5ffc">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/806e8e9b-78d8-4f5f-b22b-60b7976f59f9">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/3382b9f3-e7a9-42a5-b69f-7455508475c3">

- 차량 대여 기능
<img width="993" alt="스크린샷 2023-12-29 12 09 51" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/f57df486-278d-4f5a-b8c4-c80a4450b891">
<img width="993" alt="스크린샷 2023-12-29 12 10 17" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/1d79f1de-c636-4dad-84cb-e10616d70edc">
<img width="993" alt="스크린샷 2023-12-29 12 10 35" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/ea2dad94-fde4-40cf-bfc2-68ab3852c2ff">

- 시험 내역 작성 및 확인 기능
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/59dae435-9235-41da-a381-122615e30a34">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/13101b43-693f-4a6d-aa09-65d80105e1f7">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/45353ead-559a-450e-895e-e7ea557efeb5">
<img width="993" alt="image" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/50275984-9260-4faa-ac9a-65fa8301bbe8">

- 주유 기능
<img width="993" alt="스크린샷 2023-12-29 12 11 57" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/22dac16f-1527-481f-b022-7a9841d12dbd">
<img width="993" alt="스크린샷 2023-12-29 12 12 07" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/068dd1ef-eb7e-4c4f-a9a5-d68cc3243c30">
<img width="993" alt="스크린샷 2023-12-29 12 12 22" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/c0e1ce76-5625-4dbb-9713-6386d4885f07">

### 관리자 전용 기능
- 차량 관련 (차량 목록 확인, 차량 등록, 차량 대여 내역)
<img width="993" alt="스크린샷 2023-12-29 12 14 33" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/05ba6443-a415-47d3-b016-6d5ce47b11f5">
<img width="993" alt="스크린샷 2023-12-29 12 13 28" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/576dca43-0cee-45bc-a9f4-5d73f28d9f76">
<img width="993" alt="스크린샷 2023-12-29 12 13 41" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/00f50ec5-6705-4406-9582-390fbe42876a">

- 시험장 관련 (시험장 목록 확인, 시험장 등록, 시험장 예약 내역)
<img width="993" alt="스크린샷 2023-12-29 12 15 45" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/7780b6db-c91e-4d26-bf0c-49c94b891847">
<img width="993" alt="스크린샷 2023-12-29 12 15 54" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/59ea4aa3-81ee-40e7-ac95-86a54b1ef877">
<img width="993" alt="스크린샷 2023-12-29 12 16 01" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/3636941e-1ab6-4f2b-8847-c1de8e322cef">

- 시험 목록 확인
<img width="993" alt="스크린샷 2023-12-29 12 17 05" src="https://github.com/Hegale/ProvingGroundManager/assets/92227496/74fa6702-7b66-4ebb-9b2c-95d08c4b8934">

- 주유 관련 (주유 목록 확인, 주유구 등록, 주유 내역)


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


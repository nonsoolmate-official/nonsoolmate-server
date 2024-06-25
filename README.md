## 📓 NONSOOLMATE
> WEB LINK: www.nonsoolmate.com

<br>
<img width="1728" alt="논술메이트_서비스설명" src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/100754581/d67cc993-2aa6-4998-821a-8e1dd8d6fbb2">
<br>

## 📒 Backend Developer
<div align="center">
	<table>
  <th>김성은 @sung-silver</th>
	<th>송민규 @mikekks</th>
	<tr>
		<td><img width="300" alt="성은" src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/ae53eebf-39ba-4264-a143-102c348c124b">
    </td>
		<td><img width="300" alt="민규" src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/6b0af5a7-f20f-4ee9-a129-d98b142ea37f">
    </td>
	</tr>
<th> 역할</th>
<th> 역할</th>
<tr>
<td>
- CI/CD 구축<br>
- 네이버 로그인<br>
- 마이페이지: 대학별 시험 리스트 조회<br>
- 마이페이지: 닉네임<br>
- 시험 보기: 첨삭권 사용<br>
- 내 정보 확인: 첨삭권 개수<br>
- 시험 보기: 문제지<br>
- 시험 보기: 시험 이름 & 제한 시간<br>
- 시험 보기: 답안지 업로드 및 시험 제출<br>
</td>
<td>
- 목표대학 설정: 리스트 조회<br>
- 목표대학 설정: 리스트 선택<br>
- 해제: 문제이미지 & 해제 PDF 조회<br>
- 첨삭: 문제이미지_해제PDF 조회<br>
- 첨삭: 첨삭PDF_해제PDF 조회<br>
- 토큰 재발급
</td>
</tr>
	</table>
</div>

<br>
<br>

## 🔑 Key Features
<img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/7438ad67-592c-4dd9-b0ee-66f79586e47b"> | <img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/84bb366d-3690-42d3-a512-e46670e1f83d"> | <img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/3fcbb9c1-05b6-4882-8fb3-1b9dbb50225c"> |
:---------:|:----------:|:----------:|
로그인 | 대학선택 | 메인|

<img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/24ab57d5-59d4-47de-b308-41a3108c86d2">|<img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/ac900361-c6cb-4944-9c58-7a7eab0329ea">|<img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/f6fbf8e9-8ae4-4a5a-bef8-4a2c19d285c6">|
:---------:|:----------:|:---------:|
시험지 조회 | 시험지 제출 | 제출 완료 후 |

<img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/cd8971b7-7e0d-4b04-8bc4-772077d8ce8a">|<img src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/81363864/13511fb2-8936-4436-ba6e-3f2b50a68d65">|
:---------:|:----------:|
문제/해제 | 첨삭/해제 |

<br><br>

## 💻 Development Tech
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br>
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<br>
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<br>
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white"> <img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
<br>
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<br>
<img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

<br><br>

## 🛠️ Architecture Structure
![image](https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/100754581/ec2e9c0d-e3f9-4feb-81f6-c1cb842beb8b)



<br><br>
## 📂 Project Structure
~~~
📦 nonsoolmateServer
├── 📂 domain
│   ├─ 📂 auth
│   │  ├─ 📂 controller
│   │  ├─ 📂 exception
│   │  └─ 📂 service
│   ├─ 📂 member
│   │  ├─ 📂 controller
│   │  ├─ 📂 entity
│   │  ├─ 📂 exception
│   │  ├─ 📂 repository
│   │  └─ 📂 service
│   ├─ 📂 selectUniversity
│   │  ├─ 📂 controller
│   │  ├─ 📂 entity
│   │  ├─ 📂 exception
│   │  ├─ 📂 repository
│   │  └─ 📂 service
│   ├─ 📂 university
│   │  ├─ 📂 controller
│   │  ├─ 📂 entity
│   │  ├─ 📂 exception
│   │  ├─ 📂 repository
│   │  └─ 📂 service
│   └─ 📂 universityExamRecord
│      ├─ 📂 controller
│      ├─ 📂 entity
│      ├─ 📂 exception
│      ├─ 📂 repository
│      └─ 📂 service
├── 📂 external
│   ├─ 📂 aws
│   │  ├─ 📂 config
│   │  └─ 📂 service
│   ├─ 📂 oauth
│   │  └─ 📂 service
│   └─ 📂 redis
│      ├─ 📂 config
│      ├─ 📂 repository
│      └─ 📂 service
└─ 📂 global
    ├─ 📂 controller
    ├─ 📂 response
    ├─ 📂 error 
    ├─ 📂 jwt
    │  ├─ 📂 utils
    │  └─ 📂 service
    ├─ 📂 security
    │  ├─ 📂 handler
    │  ├─ 📂 service
    │  ├─ 📂 filter
    │  └─ 📂 config
    ├─ 📂 swagger
    │  └─ 📂 config     
    └─ 📂 util
       └─ 📂 mapper
~~~
<br><br>

## 📚 ERD
<img width="989" alt="스크린샷 2024-01-11 오후 10 03 04" src="https://github.com/nonsoolmate/NONSOOLMATE-SERVER/assets/100754581/37a67038-30c9-4198-935e-4618872553b6">

## 🔐 Nonsoolmate Server's Rule

### 1. 👻 Commit Convention

- ex) [FEAT] 목표 대학 리스트 조회 API 개발
- ex) [FIX] 내가 작성하지 않은 리뷰 볼 수 있는 버그 해결

```jsx
- [CHORE]: 내부 파일 수정
- [FEAT] : 새로운 기능 구현
- [ADD] : FEAT 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성 시
- [FIX] : 코드 수정, 버그, 오류 해결
- [DEL] : 쓸모없는 코드 삭제
- [DOCS] : README나 WIKI 등의 문서 개정
- [MOVE] : 프로젝트 내 파일이나 코드의 이동
- [RENAME] : 파일 이름의 변경
- [MERGE]: 다른 브렌치를 merge하는 경우
- [STYLE] : 코드가 아닌 스타일 변경을 하는 경우
- [INIT] : Initial commit을 하는 경우
- [REFACTOR] : 로직은 변경 없는 클린 코드를 위한 코드 수정
```
<br>

### 2. 🐤 Branch Strategy

- `prd branch` : 배포 단위 branch (운영 서버 사용 용도 → 웹잼 이후 사용)
- `dev branch` : 주요 개발 branch, main merge 전 거치는 branch
- `feat branch` : 각자 개발 branch
- 할 일 issue 등록 후, `issue number`를 사용하여 branch 생성 후 작업
    - ex) feat/#issue_number
- branch naming
    - 기능 개발 - `feat`
    - 리팩토링 - `refactor`
    - 수정 - `fix`
    - 프로젝트 셋팅 - `setting`
- 해당 branch 작업 완료 후 PR 보내기
    - 항상 local에서 충돌 해결 후 → remote에 올리기
    - reviewer에 서로 tag후 code-review
    - comment(review) 전 merge 불가!
    - review 반영 후, 본인이 merge
- branch 구조
    
```bash
main
 └── dev 
      ├── feat/#1
      └── feat/#2
```
    
- merge 전략 → rebase and merge
- rebase가 헷갈린다면?
    - private → 성은 → Git Merge 방법 공유 참고하기! : [Git Merge 방법 공유](https://www.notion.so/Git-Merge-ec472be87bb84df8b81e8951bc7bf4e6?pvs=21)
<br>

### 3. 👀 Code Convention

- 아래 3가지는 기억해주세요
 - else 지양하기
 - 우테코 코드 컨벤션 적용하기
 - `cmd + option + L`

### 3.1 우테코 코드 컨벤션

- **아래 xml 파일 다운받고 적용하기**
[woowacourse-docs/styleguide/java at main · woowacourse/woowacourse-docs](https://github.com/woowacourse/woowacourse-docs/tree/main/styleguide/java)
    

### 3.2 메소드명
- ***CRUD Create get(Read) Update Delete로 시작하기!***
    - 예시: getUser(readUser) createUser readBoard updateUser
- **컨트롤러 & 서비스 메서드명 최대한 비슷하게 네이밍**

### 3.3 DTO명

- `Request` ⇒
    - **생성 요청** ⇒ entity명 + RequestDTO
        - 예시: *UserRequest*DTO
    - **수정 요청** ⇒ entity명 + Update + RequestDTO
        - 예시:  *UserUpdateRequest*DTO
- `Response` ⇒
    - **생성 요청에 대한 응답** ⇒ entity명 + created + ResponseDTO
        - 예시: *UserCreatedResponse*DTO
    - **조회 요청에 대한 응답(가공 필요)** ⇒ entity명을 포함한 적절한 이름 + ResponseDTO
        - 예시: UserResponseDTO
        - 복수라면? entity명 + -(e)s + 적절한 설명 + ResponseDTO
            - 예시: *UsersOrderByNameResponse*DTO
    - **entity 그 자체를 리턴할 때(가공 X)** ⇒ entity명 + DTO
        - 예시: *User*DTO
        - 복수라면? entity명+ -(e)s + DTO
            예시:  *Users*DTO

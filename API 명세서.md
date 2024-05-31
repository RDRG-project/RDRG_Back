해당 API 명세서는 '전자기기 렌탈 서비스'의 REST API를 명세하고 있습니다.

- Domain : <http://localhost:4500>  

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Auth 모듈</h2>

인증 및 인가와 관련된 REST API 모듈  
로그인, 회원가입, 소셜 로그인, 소셜 회원가입 등의 API가 포함되어 있습니다.  
  
- url : /rdbg/auth


#### - 로그인  
  
##### 설명

클라이언트로부터 사용자 아이디와 평문의 비밀번호를 입력받고 
아이디와 비밀번호가 일치한다면 성공 처리가되며 access_token과 해당 토큰의 만료 기간을 
반환합니다. 
만약 아이디 혹은 비밀번호가 하나라도 틀리다면 실패 처리됩니다. 
서버 에러, 데이터베이스 에러, 토큰 생성 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/sign-in**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |
| userPassword | String | 사용자의 비밀번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/auth/sign-in" 
 -d "userId" = "user1234" 
 -d "userPassword" = "1q2w3e4r!"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| accessToken | String | 엑세스 토큰 | O |
| expires | int | 만료기간 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "accessToken": "${ACCESS_TOKEN}",
  "expires": 3600
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (로그인 정보 불일치)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "SF",
  "message": "Sign in Failed."
}
```

**응답 : 실패 (토큰 생성 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "TF",
  "message": "Token creation Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 아이디 중복 확인  
  
##### 설명

클라이언트로부터 아이디를 입력받아 해당하는 아이디가 이미 사용중인 아이디인지 확인합니다. 
중복되지 않은 아이디이면 성공처리를 합니다. 
만약 중복되는 아이디라면 실패처리를 합니다. 데이터베이스 오류가 발생할 수 있습니다.

- method : **POST**  
- URL : **/id-check**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 중복확인 할 사용자의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/auth/id-check" \
 -d "userId" = "user1234" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "DI",
  "message": "Duplicated Id."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 이메일 인증
  
##### 설명

클라이언트로부터 이메일 를 입력받아 
해당하는 이메일이 중복이 되었는지 확인하고 인증메일을 보내면 성공처리를 합니다. 
만약 중복되거나 인증메일 전송 실시 실패처리를 합니다. 
데이터베이스 오류가 발생할 수 있습니다.

- method : **POST**  
- URL : **/email-check**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 인증 번호를 확인할 사용자 이메일 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/auth/email-auth-check" \
 -d "userEmail" = "user1234@mail.com"
 -d "authNumber" = "1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```


**응답 : 실패 (중복된 이메일)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "DE",
  "message": "Duplicated Email."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 이메일 인증 확인
  
##### 설명

클라이언트로부터 이메일과 인증 번호를 입력받아 
해당하는 이메일에 전송한 인증번호와 일치하는지 확인합니다. 
일치한다면 성공처리를 합니다. 
만약 일치하지 않는다면 실패처리를 합니다. 데이터베이스 오류가 발생할 수 있습니다.

- method : **POST**  
- URL : **/email-auth-check**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 인증 번호를 확인할 사용자 이메일 | O |
| authNumber | String | 인증 확인할 인증 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/auth/email-auth-check" \
 -d "userEmail" = "user1234@mail.com"
 -d "authNumber" = "1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (이메일 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원가입
  
##### 설명

클라이언트로부터 아이디, 비밀번호, 이메일, 인증번호를 입력받아 회원가입 처리를 합니다. 
정상적으로 회원가입이 완료되면 성공처리를 합니다. 
만약 중복된 아이디, 중복된 이메일, 인증번호 불일치가 발생하면 실패처리를 합니다. 
데이터베이스 오류가 발생할 수 있습니다.

- method : **POST**  
- URL : **/sign-up**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자 아이디 | O |
| userPassword | String | 사용자 비밀번호 (영문+숫자 8~13자) | O |
| userEmail | String | 사용자 이메일 (이메일 형태의 데이터) | O |
| authNumber | String | 인증 확인할 인증 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/auth/sign-up" \
 -d "userId" = "user1234" \
 -d "userPassword" = "1q2w3e4r!" \
 -d "userEmail" = "user1234@mail.com" \
 -d "authNumber" = "1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "DI",
  "message": "Duplicated Id."
}
```

**응답 : 실패 (중복된 이메일)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "DE",
  "message": "Duplicated Email."
}
```

**응답 : 실패 (이메일 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>User 모듈</h2>

사용자 정보와 관련된 REST API 모듈
로그인 유저 권한 부여, 정보 불러오기, 비밀번호 변경, 회원탈퇴등의 API가 포함되어 있습니다.
  
- url : /rdbg/user  


#### - 로그인 유저 정보 반환  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 받으면
해당 토큰의 작성자(subject)에 해당하는 사용자 정보를 반환합니다. 
성공시에는 사용자의 아이디와 권한을 반환합니다. 
인증 실패 및 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/user/" \
 -H "Authorization" : "Bearer {JWT}"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 사용자의 아이디 | O |
| userRole | String | 사용자의 권한 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userId": "user1234",
  "userRole": "ROLE_USER"
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 개인정보 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
유저 id를 입력 받고 개인정보(아이디, 이메일, sns 가입여부를) 가져오면 성공처리 됩니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 유저 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdrg/user/{userId}" \
 -H "Authorization: Bearer {JWT}" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 유저 아이디 | O |
| userEmail | String | 유저 이메일 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userId" : "user1234",
  "userEmail" : "user1234@mail.com"
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NU",
  "message": "No Exist UserId."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***


#### - 비밀번호 변경하기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
아이디, 기존 비밀번호, 변경할 비밀번호를 입력받고 요청을 보내면 기존 비밀번호에서 변경할 비밀번호로 변경합니다.
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PATCH**  
- URL : **/changePW**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자 아이디 | O |


###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/user/changePW" \
 -H "Authorization: Bearer {JWT}" \
 -d "userId={userId}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (비빌번호 변경 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "PCF",
  "message": "Password Change Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 회원 탈퇴
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
사용자 아이디를 받고 그 아이디 및 회원을 삭제를 성공시키면 성공처리를 합니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **{userId}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 유저아이디 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|


###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/user/{userId}" \
 -H "Authorization: Bearer {JWT}" \

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (파일 업로드 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "FUF",
  "message": "File Upload Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Payment 모듈</h2>

대여, 결제와 관련된 Payment API 모듈  
결제 정보 저장, 예약정보 확인, 대여내역 불러오기 등의 API가 포함되어 있습니다.  

#### -  결제 정보 저장
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여
사용자 아이디와 대여내용을 입력 받고 요청을 보내면 해당 하는 결제정보를 저장합니다.
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 인증 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **POST**  
- URL : **/save**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| user_id | String | 유저 아이디 | O |
| serialNumber | String[] | 시리얼넘버 | O|
| name | String | 제품명 | O |
| type | String | 제품 타입 | O |
| rentPlace | String | 대여 지점 | O |
| rentReturnPlace | String | 반납 지점 | O |
| rentDatetime | Datetime | 대여 날짜 | O |
| rentReturnDatetime | Datetime | 반납 날짜 | O |
| price | Int | 가격 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/payment/save" \
 -d "user_id" = "user1234",
 -d "serialNumber" = "1q2w3e4r5t6y7u8i9o0p",
 -d "name" = "A제품",
 -d "type" = "노트북",
 -d "rent-datetime" : "2024-05-14 11:10:00",
 -d "rent-return-datetime" : "2024-05-14 15:10:00",
 -d "rent-place" : "부산",
 -d	"rent-return-place" : "서울"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 예약정보 확인
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청 받으면
로그인 된 userId를 기준으로 대여한 기기의 예약 정보를 리스트를 반환합니다.
만약 작성에 실패하면 실패처리 됩니다. 
인증 실패, 인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자ID | O

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/payment/{userId}" \
 -H "Authorization: Bearer {JWT}" \
 -d "userId" = "user1234" \

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| userId | String | 사용자 아이디 | O |
| rentalPeriod | rentalPeriod[] | 대여기간 | O |
| rentPlace | String | 대여 지점 | O |
| rentTotalPrice | int | 이용 요금 | O |


**rentalPeriod[]**
| name | type | description | required |
|---|:---:|:---:|:---:|
| rentalPeriod | rentalPeriod | 대여기간 | O |


###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "rentalPeriod " : [
	  {
		  "rentalPeriod : 2024-05-14 09:00 ~ 2024-05-15 10:00"
	  }
	]
	"rentPlace : 부산",
	"rentTotalPrice : 200000"
}

```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 대여 내역 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
사용자의 아이디를 입력받고 불러오기에 성공하면 성공처리를 합니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/myrentpage** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 유저아이디 | O |


###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/payment/myrentpage" \
 -H "Authorization: Bearer {JWT}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| rent | rentItem[] | 대여현황 | O |


###### rentItem

| name | type | description | required |
| type | String | 타입 | O
| rentDatetime | Datetime | 대여 날짜 | O |
| rentReturnDatetime | Datetime | 반납 날짜 | O |
| rentStatus | String | 대여 상태 | O |


###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "type " : "노트북",
  "rentDatetime " : "2024-05-14 14:22",
  "rentReturnDatetime " : "2024-05-15 14:22"
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 대여 가능한 기기 리스트 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
대여 가능일을 선택하면 예약일이 겹치지 않는 대여 가능 기기를 불러오기에 성공하면 성공처리를 합니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **get**  
- URL : **/search?start={rent_datetime}&end={rent_return_datetime}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Query Param

| name | type | description | required |
|---|:---:|:---:|:---:|
| start | String | 대여 일자 | O |
| end | String | 반납 일자 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/payment/serch?start={rent_datetime}&end={rent_return_datetime}" \
 -H "Authorization: Bearer {JWT}" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| deviceList | deviceListItem[] | 기기 리스트 | O |

##### deviceListItem
| name | type | description | required |
|---|:---:|:---:|:---:|
| serialNumber | String | 시리얼 넘버 | O |
| model | String | 모델명 | O |
| name | String | 기기명 | O |
| explain | String | 설명 | O |
| type | String | 제품 타입 | O |
| price | Int | 가격 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Device 모듈</h2>

기기 정보와 관련된 REST API 모듈
  
- url : /rdbg/device

***
#### -  기기 리스트 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
요청을 보내면 성공하면 기기 리스트를 반환합니다.
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |


###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/device/list" \
 -H "Authorization: Bearer {JWT}" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| itRentList | itRentList[] | it렌트 리스트 | O |

###### itRentList

**itRentList**
| name | type | description | required |
|---|:---:|:---:|:---:|
| model | String | 모델명 | O |
| type | String | 제품 타입 | O |
| name | String | 제품명 | O |
| explain | String | 제품 설명 | O |
| price | Int | 가격 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "itRentList" : [
	  {
		  "model " : "모델명",
		  "type" : "노트북",
		  "name" : "샘숭 노트북",
		  "explain" : "대답이 없다. 그냥 평범한 노트북이다."
		  "price" : "10000"
		}
	]
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - IT 기기 추가  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
시리얼넘버, 모델명, 제품명, 제품설명, 제품타입, 가격을 입력받고 작성에 성공하면 성공처리를 합니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 인증실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **POST**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| serialNumber | String | 시리얼 번호 | O |
| model | String | 모델명 | O |
| name | String | 제품명 | O |
| explain | String | 제품설명 | O |
| type | String | 제품타입 | O |
| price | Int | 가격 | O |


###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/device/" \
 -H "Authorization" : "Bearer {JWT}" \
 -d "serialNumber" = "1q2w3e4r5t6y7u8i9o0p" \
 -d "model" = "NT-D01"\
 -d "name" = "샘숭 넷타입 디트"\
 -d "explain" = "게이밍용 고사양 노트북 레알 무겁다."\
 -d "type" = "노트북"\
 -d "pice" = 100000
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - IT 기기 삭제
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
시리얼넘버를 입력받고 삭제에 성공하면 성공처리를 합니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 인증실패, 데이터베이스 에러, 존재하지 않는 상품, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/{serialNumber}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| serialNumber | String | 시리얼 번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4500/rdbg/device/{serialNumber}" \
 -H "Authorization" : "Bearer {JWT}" \
 -d "serialNumber" : "1q2w3e4r5t6y7u8i9o0p" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 상품)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "ND",
  "message": "No Exist Device."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Board 모듈</h2>

문의 게시물과 관련된 REST API 모듈
  
- url : /rdbg/board  

***

#### - 문의 게시물 작성  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
제목, 내용, 파일 업로드, 등록된 기기를 입력받고 작성에 성공하면 성공처리를 합니다. 
만약 작성에 실패하면 실패처리 됩니다. 
인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **POST**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| title | String | Q&A 제목 | O |
| contents | String |  내용 | O |
| uploadFile | String[] | 파일 업로드 | X |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/board/" \
 -H "Authorization: Bearer {JWT}" \
 -d "title" = "제목입니다." \
 -d "contents" = "내용입니다"
 -d "uploadFile" = "pdf 땃습니다.jpg"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (파일 업로드 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "FUF",
  "message": "File Upload Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 문의게시판 리스트 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 보내면 
작성일 기준 내림차순으로 자신의 게시물 리스트를 반환합니다. 
만약 불러오기에 실패하면 실패처리를 합니다. 인가 실패, 데이터베이스 에러가 
발생할 수 있습니다.

- method : **GET**  
- URL : **/list**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자ID | O |
| userRole | String | 사용자권한 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/board/list" \
 -H "Authorization" : "Bearer {JWT}"
 -d "userId" = "user1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| writerId | String | 작성자 ID | O |
| boardList | BoardListItem[] | 문의 게시물 리스트 | O |

**BoardListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 상태 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 아이디</br>(첫 글자를 제외한 나머지 문자는 *) | O |
| writeDatetime | String | 작성일</br>(yy.mm.dd 형태) | O |



###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "writerId" : "user1234",  
  "boardList": [
    {
      "status": "false",
      "title": "제목일까요?",
      "writeDateTime": "2024-01-12",
    }, ...
  ]
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authentication Failed."
}
```

**응답 : 실패 (파일 업로드 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "FUF",
  "message": "File Upload Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 문의 게시물 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수번호를 입력받고 요청을 보내면 해당하는 문의 게시물 데이터를 반환합니다. 
만약 불러오기에 실패하면 실패처리를 합니다. 
인가 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- URL : **/{reception_number}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | String | 게시물 번호 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/board/${reception_number}" \
 -H "Authorization" : "Bearer {JWT}"
 -d "userId" = "user1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 상태 | O |
| title | String | 제목 | O |
| writerId | String | 작성자 아이디 | O |
| writeDatetime | String | 작성일</br>(yyyy.mm.dd 형태) | O |
| contents | String | 내용 | O |
| comment | String | 답글 내용 | X |
| uploadFile | int | 첨부 파일| X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "receptionNumber": "1",
  "status": "false",
  "title": "내용일까요?",
  "writerId": "user1234",
  "writeDatetime": "2024-01-12",
  "contents": "제목일까요?",
  "comment": "답변일지도요?"
  "uploadFile": "여기 pdf입니다.mp4",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 문의게시판 게시물 답글 작성  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수번호와 답글 내용을 입력받고 요청을 보내면 해당하는 문의 게시물의 답글이 작성됩니다. 
만약 증가에 실패하면 실패처리를 합니다. 인가 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- URL : **/{reception_number}/comment**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| comment | String | 답글 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/board/${reception_number}/comment" \
 -H "Authorization" : "Bearer {JWT}" 
 -d "receptionNumber" = "1"
 -d "comment" = "답변일지도 모릅니다."
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (이미 작성된 답글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "WC",
  "message": "Written Comment."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 문의 게시물 삭제  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수번호를 입력받고 요청을 보내면 해당하는 문의 게시물이 삭제됩니다. 
만약 삭제에 실패하면 실패처리를 합니다. 
인가 실패, 데이터베이스 에러가 발생할 수 있습니다.

- method : **DELETE**  
- URL : **/{reception_number}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4500/rdbg/board/${reception_number}" \
 -H "Authorization" : "Bearer {JWT}"
 -d "receptionNumber" = "1"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (이미 작성된 답글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "WC",
  "message": "Written Comment."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***

#### - 문의 게시물 수정  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수 번호, 제목, 내용을 입력받고 수정에 성공하면 성공처리를 합니다. 
만약 수정에 실패하면 실패처리 됩니다. 인가 실패, 데이터베이스 에러, 데이터 유효성 검사 실패가 발생할 수 있습니다.

- method : **PUT**  
- URL : **/{reception_number}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber| int | 수정할 접수 번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| title | String | Q&A 제목 | O |
| contents | String | Q&A 내용 | O |

###### Example

```bash
curl -v -X PUT "http://localhost:4500/rdbg/board/{reception_number}" \
 -H "Authorization" : "Bearer {JWT}" \
 -d "receptionNumber" = "1"
 -d "title" = "제목" 
 -d "contents" = "내용"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환하는 Response Body의 Content Type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 메세지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "VF",
  "message": "Validation Failed."
}
```

**응답 : 실패 (인가 실패)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (존재하지 않는 게시물)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NB",
  "message": "No Exist Board."
}
```

**응답 : 실패 (답변 완료된 게시물)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "WC",
  "message": "Written Comment."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (데이터베이스 오류)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8
{
  "code": "DBE",
  "message": "Database Error."
}
```

***
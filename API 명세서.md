해당 API 명세서는 '전자기기 렌탈 서비스'의 REST API를 명세하고 있습니다.

- Domain : <http://localhost:4500>  


***


<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Auth 모듈</h2>

인증 및 인가와 관련된 REST API 모듈  
로그인, 아이디 중복 확인, 이메일 중복 확인 및 인증메일전송, 인증번호 확인, 회원가입 등의 API을 구현하였으며
소셜 로그인, 소셜 회원가입은 외부 API(카카오, 네이버)로 포함되어 있습니다.  
  
- url : /rdbg/auth


#### - 로그인  
  
##### 설명

클라이언트로부터 사용자 아이디와 평문의 비밀번호를 입력받고 
아이디와 비밀번호가 일치한다면 성공 처리가되며 access_token과 해당 토큰의 만료 기간을 반환합니다. 
데이터 유효성 검사 실패, 토큰 생성 실패, 로그인 정보 불일치, 데이터 베이스 오류가 발생할 수 있습니다.

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
| expires | int | 만료기간 | O |

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
입력한 아이디가 중복되지 않는 아이디라면 성공합니다. 
데이터 유효성 검사 실패, 중복된 아이디, 데이터 베이스 오류가 발생할 수 있습니다.

- method : **POST**  
- URL : **/id-check**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디(중복 확인이 필요한 사용자 아이디) | O |

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

#### - 이메일 중복 확인 및 인증메일 전송
  
##### 설명

클라이언트로부터 형식에 맞는 이메일 를 입력받아 
해당하는 이메일이 중복이 되었는지 확인하고 인증메일을 보내면 성공처리를 합니다. 
데이터 유효성 검사 실패, 중복된 이메일, 이메일 전송 실패, 데이터베이스 오류가 발생할 수 있습니다.

- method : **POST**  
- URL : **/email-auth**  

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
curl -v -X POST "http://localhost:4500/rdbg/auth/email-auth" \
 -d "userEmail" = "user1234@mail.com"
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
**응답 : 실패 (이메일 전송 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "MF",
  "message": "Mail send Failed."
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
데이터 유효성 검사 실패, 이메일 인증 실패, 데이터 베이스 오류가 발생합니다.

- method : **POST**  
- URL : **/email-auth-check**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userEmail | String | 사용자 이메일 | O |
| authNumber | String | 인증 번호 | O |

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
데이터 유효성 검사 실패, 중복된 아이디, 중복된 이메일, 이메일 인증 실패, 데이터 베이스 오류가 발생할 수 있습니다.

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
| authNumber | String | 인증 번호 | O |

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
로그인 유저 정보 반환, 유저 정보 불러오기, 비밀번호 변경, 회원탈퇴 등의 API가 구현되어 있습니다.
  
- url : /rdbg/user  


#### - 로그인 유저 정보 반환  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청을 받으면
해당 토큰의 작성자(subject)에 해당하는 사용자 정보를 반환합니다. 
성공시에는 사용자의 아이디와 권한을 반환합니다. 
로그인 토큰 없음, 권한 없음, 데이터 베이스 오류가 발생할 수 있습니다.

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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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

#### - 유저 정보 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
유저 id를 입력 받고 개인정보(아이디, 이메일) 가져오면 성공처리 됩니다. 
데이터 유효성 검사 실패, 로그인 토크 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

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
curl -v -X GET "http://localhost:4500/rdbg/user/{userId}" \
 -H "Authorization" : "Bearer {JWT}" \
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
  "userEmail" : "user1234@mail.com",
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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
데이터 유효성 검사 실패, 로그인 토크 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **PATCH**  
- URL : **/changePw**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자 아이디 | O |
| userPassword | String | 사용자 현재 비밀번호 | O |
| newUserPassword | String | 사용자 새로운 비밀번호 | O |


###### Example

```bash
curl -v -X PATCH "http://localhost:4500/rdbg/user/changePw" \
 -H "Authorization: Bearer {JWT}" \
 -d "userId" = "user1234" \
 -d "userPassword" = "1q2w3e4r" \
 -d "newUserPassword" = "1q2w3e4r5t"
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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
데이터 유효성 검사 실패, 로그인 토크 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

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
curl -v -X DELETE "http://localhost:4500/rdbg/user/{userId}" \
 -H "Authorization" : "Bearer {JWT}" \

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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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


<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>FileUpload 모듈</h2>

파일 업로드 REST API 모듈
  
파일 업로드 와 파일 불러오기 API를 구현하였습니다.
  
- url : /rdbg/file 

*** 

#### - 파일 업로드
  
##### 설명

클라이언트로부터 파일을 받아 업로드는 후 URL로 반환하는 API 모듈을 구현하였습니다.


- method : **POST**  
- URL : **/upload**  

##### Request

###### Header

- `Content-Type: multipart/form-data`

#### Parameters

| name | type | in | required | Description |
|---|:---:|:---:|:---:|
| file | MultipartFile | formData | true | 업로드 파일 |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/file/upload" \
-d "url": "https://localhost:4500/rdbg/file/imge1234.jpg"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| url | String | 파일 URL | O 

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "url": "https://localhost:4500/rdbg/file/"
}
```

***

#### - 파일 불러오기
  
##### 설명

클라이언트로부터 파일명을 받아 그 파일명으로 파일을 가져오는 API 모듈을 구현하였습니다.


- method : **GET**  
- URL : **/{fileName}**  

##### Request

###### Header

#### Path Parameters

| name | type | in | required | Description |
|---|:---:|:---:|:---:|
| fileName | String | Path | true | 파일 이름 |

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/file/img1234" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| url | String | 파일 URL | O 

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: image/
{
 
}

```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Board 모듈</h2>

문의 게시물과 관련된 REST API 모듈
  
문의 게시글 작성, 문의 게시글 답변 작성, 문의 게시글 리스트 호출, 문의 게시글 불러오기, 
문의 게시글 수정, 문의 게시글 삭제의 API을 구현하였습니다.
  
- url : /rdbg/board  

***

#### - 문의 게시물 작성  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
제목, 내용, 파일 업로드, 등록된 기기를 입력받고 작성에 성공하면 성공처리를 합니다. 
데이터 유효성 검사 실패, 로그인 토큰 없음, 권한 없음, 파일 업로드 실패, 데이터 베이스 실패가 발생 할 수 있습니다.

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
| fileList | BoardFileItem[] | 게시물첨부파일 | X |

##### BoardFileItem[]
| name | type | description | required |
|---|:---:|:---:|:---:|
| url | String | url주소 | O |
| originalFileName | String | 파일이름 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/board/" \
 -H "Authorization: Bearer {JWT}" \
 -d "title" = "제목입니다." \
 -d "contents" = "내용입니다"
 -d "fileList" = "{
          "url": "http://localhost:4500/rdrg/file/4babb829-b141-47ce-885b-e3bfa50c6049.png",
          "originalFileName": "에베베"
	 {"
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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
작성일 기준 내림차순으로 게시물 리스트를 반환합니다. 
로그인 토큰 없음, 데이터베이스 오류가 발생 할 수 있습니다.

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

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/board/list" \
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
| boardList | BoardListItem[] | 문의 게시물 리스트 | O |

**BoardListItem**
| name | type | description | required |
|---|:---:|:---:|:---:|
| receptionNumber | int | 접수 번호 | O |
| status | boolean | 답글 상태 | O |
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
  "boardList": [
    {
      "receptionNumber": "false",
      "status": "0",
      "title": "제목일까요",
		  "writerId": "user1234",
		  "writeDatetime": "2024-01-12"
    }
  ]
}

```

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 문의 게시물 불러오기  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수번호를 입력받고 요청을 보내면 해당하는 문의 게시물 데이터를 반환합니다. 
존재 하지 않는 게시물, 로그인 토큰 없음, 데이터베이스 오류가 발생할 수 있습니다.

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
| imageUrl | List<String> | 첨부 파일 URL 리스트 | X |
| originalFileName | List<String> | 사진 이름 리스트 | X |


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
  "comment": "답변일지도요?",
  "imageUrl": [
        "http://localhost:4500/rdrg/file/4babb829-b141-47ce-885b-e3bfa50c6049.png"
    ],
  "originalFileName": [
        "에베베"
    ]
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

**응답 : 실패 (로그인 토큰 없음)**
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
데이터 유효성 검사 실패, 존재하지 않는 게시물, 이미 작성된 답글, 로그인 토큰 없음, 권한 없음, 데이터베이스 오류가 발생할 수 있습니다.

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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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

#### - 문의 게시물 삭제  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수번호를 입력받고 요청을 보내면 해당하는 문의 게시물이 삭제됩니다. 
데이터 유효성 검사 실패, 존재하지 않는 게시물, 로그인 토큰 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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

#### - 문의 게시물 수정  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
접수 번호, 제목, 내용을 입력받고 수정에 성공하면 성공처리를 합니다. 
데이터 유효성 검사 실패, 존재하지 않는 게시물, 답변 완료된 게시물, 로그인 토큰 없음, 권한 없음, 데이터베이스 오류가 발생할 수 있습니다.

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
| title | String | 문의 제목 | O |
| contents | String | 문의 내용 | O |
| fileList | BoardFileItem[] | 이미지 | O | -> 지상 수정

###### BoardFileItem[]
| name | type | description | required |
|---|:---:|:---:|:---:|
| url | String | url 주소 | O |
| originalFileName | String | 파일 이름 | O |



###### Example

```bash
curl -v -X PUT "http://localhost:4500/rdbg/board/{reception_number}" \
 -H "Authorization" : "Bearer {JWT}" \
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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


<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Device 모듈</h2>

대여 기기와 관련된 REST API 모듈
  
대여 기기 추가, 대여 기기 리스트 불러오기, 대여 기기 삭제 API를 구현하였습니다.
  
- url : /rdbg/device

#### - IT 기기 추가  
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
시리얼 넘버, 모델명, 제품명, 제품 설명, 제품 타입, 제품 브랜드, 제품 가격, 제품 이미지를 입력받고 작성에 성공하면 성공처리를 합니다. 
데이터 유효성 검사 실패, 로그인 토큰 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

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
| deviceExplain | String | 제품 설명 | O |
| type | String | 제품 타입 | O |
| brand | String | 제품 브랜드 | O |
| price | Int | 제품 대여 가격 | O |
| devicesImgUrl | String | 제품 이미지 URL | O |
| place | String | 제품을 소지한 지역 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/device/" \
 -H "Authorization" : "Bearer {JWT}" \
 -d "serialNumber" = "1q2w3e4r5t6y7u8i9o0p" \
 -d "model" = "NT-D01"\
 -d "name" = "샘숭 넷타입 디트"\
 -d "deviceExplain" = "게이밍용 고사양 노트북 레알 무겁다."\
 -d "type" = "노트북"\
 -d "brand" = "샘숭" \
 -d "pice" = 100000 \
 -d "devicesImgUrl" = "www.img.com/1234"\
 -d "place" = "부산"
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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

#### - 예약 가능한 기기 리스트 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
요청을 보냈을 경우 성공하면 예약 가능한 기기 리스트를 반환합니다.
데이터 유효성 검사 실패, 로그인 토큰 없음, 데이터베이스 오류가 발생 수 있습니다.

- method : **GET**  
- URL : **/list?start={rent_datetime}&end={rent_return_datetime}&place={place}** 

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
| place | String | 기기 장소 | O |


###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/device/list?start={rent_datetime}&end={rent_return_datetime}&place={place}" \
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
| deviceList | DeviceListItem[] | 대여기기 항목 | O |

###### DeviceListItem

**DeviceList**
| name | type | description | required |
|---|:---:|:---:|:---:|
| serialNumber| String | 시리얼 번호 | O |
| model | String | 모델명 | O |
| name | String | 제품명 | O |
| deviceExplain | String | 제품 설명 | O |
| type | String | 제품 타입 | O |
| brand | String | 제품 브랜드 | O |
| price | Integer | 제품 대여 가격 | O |
| devicesImgUrl | String | 제품 이미지 URL  | O |
| place | String | 제품 현재위치 | O |

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
		  "serialNumber" : "aq2w123"
		  "model " : "모델명",
		  "name" : "파워노트북"
		  "deviceExplain " : "대답이 없다. 그냥 평범한 노트북이다."
		  "type" : "노트북",
		  "brand" : "샘숭",
		  "price" : "10000",
		  "devicesImgUrl" : "www.img.com/1324423",
		  "place" : "BUSAN"
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

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 관리자 권한의 기기 리스트 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 
요청을 보냈을 경우 성공하고 권한이 관리자 권한이면 모든 기기 리스트를 반환합니다.
데이터 유효성 검사 실패, 로그인 토큰 없음, 데이터베이스 오류가 발생 수 있습니다.

- method : **GET**  
- URL : **/adminlist** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |


###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/device/adminlist" \
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
| adminDeviceList | DeviceListItme[] | 대여기기 항목 | O |

###### DeviceListItme

**DeviceListItme**
| name | type | description | required |
|---|:---:|:---:|:---:|
| serialNumber| String | 시리얼 번호 | O |
| model | String | 모델명 | O |
| name | String | 제품명 | O |
| deviceExplain | String | 제품 설명 | O |
| type | String | 제품 타입 | O |
| brand | String | 제품 브랜드 | O |
| price | Integer | 제품 대여 가격 | O |
| devicesImgUrl | String | 제품 이미지 URL  | O |
| place | String | 제품 현재위치  | O |

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
		  "serialNumber" : "aq2w123"
		  "model " : "모델명",
		  "name" : "파워노트북"
		  "deviceExplain " : "대답이 없다. 그냥 평범한 노트북이다."
		  "type" : "노트북",
		  "brand" : "샘숭",
		  "price" : "10000",
		  "devicesImgUrl" : "www.img.com/1324423",
		  "place" : "BUSAN"
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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
데이터 유효성 검사 실패, 존재하지 않는 기기, 로그인 토큰이 없음, 권한 없음, 데이터베이스 오류가 발생 할 수 있습니다.

- method : **DELETE**  
- URL : **/{serialNumber}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| serialNumber | String | 시리얼 번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4500/rdbg/device/{serialNumber}" \
 -H "Authorization" : "Bearer {JWT}" \
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

**응답 : 실패 (존재하지 않는 기기)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "ND",
  "message": "No Exist Device."
}
```

**응답 : 실패 (로그인 토큰 없음)**
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


<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Payment 모듈</h2>

대여, 결제와 관련된 Payment API 모듈  
결제 정보 저장, 예약정보 확인, 대여내역 불러오기 등의 API가 포함되어 있습니다.  

- url : /rdbg/payment

#### -  결제 정보 저장
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여
사용자 아이디와 대여내용을 입력 받고 요청을 보내면 해당 하는 결제정보를 저장합니다.
데이터 유효성 검사실패, 대여가 불가능한 기기 ,로그인 토큰 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

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
| rentUserId | String | 빌려간 유저 아이디 | O |
| rentSerialNumber | String[] | 빌려간 기기의 시리얼 번호 | O |
| rentPlace | String | 대여한 지점 | O |
| rentReturnPlace | String | 반납할 지점 | O |
| rentDatetime | Datetime | 대여한 날짜 | O |
| rentReturnDatetime | Datetime | 반납할 날짜 | O |
| rentTotalPrice | Int | 제품 대여에 쓴 비용 | O |
| rentStatus | String | 대여 상태(대여중, 대여 완료...) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4500/rdbg/payment/save" \
 -d "rentUserId" = "user1234",
 -d "rentSerialNumber" = ["1q2w3e4r5t6y7u8i9o0p"],
 -d "rentPlace" = "부산",
 -d "rentReturnPlace" = "서울",
 -d "rentDatetime" : "2024-05-14 11:10:00",
 -d "rentReturnDatetime" : "2024-05-16 11:10:00",
 -d "rentTotalPrice" : 30000,
 -d	"rentStatus" : "반납완료"
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

**응답 : 실패 (대여가 불가능한 기기)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NRD",
  "message": "Not Rental Device."
}
```

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 나의 예약정보 확인(사용자)
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 포함하여 요청 받으면
로그인 된 userId를 기준으로 대여한 기기의 예약 정보를 리스트를 반환합니다.. 
데이터 유효성 검사 실패, 로그인 토큰 없음, 존재 하지 않는 경로를 발생 할 수 있습니다.

- method : **GET**  
- URL : **/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Valiable

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자 ID | O

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/payment/{userId}" \
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
| userId | String | 사용자 아이디 | O |
| rentalPeriod | rentalPeriod[] | 대여기간 | O |
| rentPlace | String | 대여한 지점 | O |
| rentTotalPrice | int | 제품 대여에 쓴 비용 | O |


**rentalPeriod[]**
| name | type | description | required |
|---|:---:|:---:|:---:|
| rentalPeriod | String | 대여기간 | O |


###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
  "userId" : "user1234",
  "rentalPeriod " : [
	  {
		  "rentalPeriod : 2024-05-14 09:00 ~ 2024-05-15 10:00"
	  }
	],
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

**응답 : 실패 (존재하지않는 경로)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NF",
  "message": "Not Found."
}
```

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 나의 대여 내역 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 받아서 사용자의 정보를 불러오기에 성공하면 성공처리를 합니다.  
데이터 유효성 검사실패, 로그인 토큰 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **GET**  
- URL : **/myrentpage** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/payment/myrentpage" \
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
| rent | RentItem[] | 대여현황 | O | -> 지상 수정


###### RentItem -> 지상 수정

| name | type | description | required |
| rentNumber | int | 예약번호 | O |
| name | RentList[] | 기기명들 | O |
| rentDatetime | String | 대여 날짜 | O |
| rentReturnDatetime | String | 반납 날짜 | O |
| totalPrice | int | 총가격 | O |
| rentStatus | String | 대여 상태(대여중, 대여 완료...) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
   "rentList" : [
		{
		"rentNumber" : 1,
		"name" : ["아이패드", "오딧세이"],
	  "rentDatetime " : "2024-05-14 14:22",
	  "rentReturnDatetime " : "2024-05-15 14:22",
	  "totalPrice" : 200000,
	  "rentStatus" : false
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

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 상세 대여 내역 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 받아 사용자의 상세 대여 정보를
불러오기에 성공하면 성공처리를 합니다.  
데이터 유효성 검사실패, 로그인 토큰 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **GET**  
- URL : **/myrentpage/{rent_number}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Valiable

| name | type | description | required |
|---|:---:|:---:|:---:|
| rentNumber | int | 대여번호 | O

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/payment/myrentpage/{rentNumber}" \
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
| rentNumber | int | 대여번호 | O |
| rent | RentDetailList[] | 상세 대여 내역 | O |
| rentDatetime | String | 대여 날짜 | O |
| rentReturnDatetime | String | 반납 날짜 | O |
| rentPlace | String | 대여장소 | O |
| rentReturnPlace | String | 반납장소 | O |
| rentStatus | String | 대여 상태(대여중, 대여 완료...) | O |
| totalPrice | int | 총가격 | O |

###### RentDetailList
| name | type | description | required |
| name | String | 기기명 | O |
| price | int | 가격 | O |


###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
	"rentNumber" : 1,
  "rentdetailList" : [
		{
		"name" : "아이패드",
	  "price" : 20000
		},
		{
		"name" : "삼성 노트북",
	  "price" : 20000
		}
	],
	"rentDatetime" : "2024-05-14 14:22",
	"rentReturnDatetime" : "2024-05-15 14:22",
	"rentPlace" : "부산",
	"rentReturnPlace" : "서울",
	"rentStatus" : 대여중,
	"totalPrice" : 40000
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

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 대여내역 삭제
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 받아 사용자의 예약 내역 취소를 성공하면 성공처리를 합니다.  
데이터 유효성 검사실패, 로그인 토큰 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **DELETE**  
- URL : **{rentNumber}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| rentNumber | int | 대여번호 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4500/rdbg/payment/{rentNumber}" \
 -H "Authorization" : "Bearer {JWT}" \

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

**응답 : 실패 (존재하지 않는 예약내역)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NBD",
  "message": "No Exist Rent Detail."
}
```

**응답 : 실패 (로그인 토큰 없음)**
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


#### - 대여 상태 변경
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 받아 사용자의 예약 내역 상태 변경을 성공하면 성공처리를 합니다.  
데이터 유효성 검사실패, 로그인 토큰 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **PATCH**  
- URL : **{rentNumber}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| rentNumber | int | 대여번호 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| rentStatus | String | 대여상태 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4500/rdbg/payment/{rentNumber}" \
 -H "Authorization" : "Bearer {JWT}" \

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

**응답 : 실패 (존재하지 않는 예약내역)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8
{
  "code": "NBD",
  "message": "No Exist Rent Detail."
}
```

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 관리자용 전체 대여내역 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰을 받아서 대여내역을 불러오기에 성공하면 성공처리를 합니다.  
데이터 유효성 검사실패, 로그인 토큰 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **GET**  
- URL : **/adminrentpage** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/payment/adminrentpage" \
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
| adminRentList | AdminRentItem[] | 관리자용 대여현황 | O |


###### AdminRentItem

| name | type | description | required |
| rentNumber | int | 예약번호 | O |
| userId | String | 유저아이디 | O |
| name | RentList[] | 기기명들 | O |
| rentDatetime | String | 대여 날짜 | O |
| rentReturnDatetime | String | 반납 날짜 | O |
| totalPrice | int | 총가격 | O |
| rentStatus | String | 대여 상태(대여중, 대여 완료...) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
   "rentList" : [
		{
		"rentNumber" : 1,
		"userId" : "test1234",
		"name" : ["아이패드", "오딧세이"],
	  "rentDatetime " : "2024-05-14 14:22",
	  "rentReturnDatetime " : "2024-05-15 14:22",
	  "totalPrice" : 200000,
	  "rentStatus" : false
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

**응답 : 실패 (로그인 토큰 없음)**
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

#### - 관리자 검색 대여 내역 불러오기
  
##### 설명

클라이언트로부터 Request Header의 Authorization 필드로 Bearer 토큰과 사용자 아이디를 받아 그 사용의 대여 현황의 정보를 불러오기에 성공하면 성공처리를 합니다.  
데이터 유효성 검사실패, 로그인 토큰 없음, 권한 없음, 데이터 베이스 오류가 발생 할 수 있습니다.

- method : **GET**  
- URL : **//adminrent/search?word={word}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | 인증에 사용될 Bearer 토큰 | O |

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| word | String | 검색할 아이디 | O |


###### Example

```bash
curl -v -X GET "http://localhost:4500/rdbg/payment/adminrent/search?word={word}" \
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
| adminRent| AdminRentItem[] | 대여현황 | O |


###### rentItem

| name | type | description | required |
| rentNumber | int | 예약번호 | O |
| userId| String | 대여자 아이디 | O |
| name | RentList[] | 기기명들 | O |
| rentDatetime | String | 대여 날짜 | O |
| rentReturnDatetime | String | 반납 날짜 | O |
| totalPrice | int | 총가격 | O |
| rentStatus | String | 대여 상태(대여중, 대여 완료...) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "code": "SU",
  "message": "Success.",
   "rentList" : [
		{
		"rentNumber" : 1,
		"userId" : "user1234"
		"name" : ["아이패드", "오딧세이"],
	  "rentDatetime " : "2024-05-14 14:22",
	  "rentReturnDatetime " : "2024-05-15 14:22",
	  "totalPrice" : 200000,
	  "rentStatus" : false
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

**응답 : 실패 (로그인 토큰 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8
{
  "code": "AF",
  "message": "Authorization Failed."
}
```

**응답 : 실패 (권한 없음)**
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
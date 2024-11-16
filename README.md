# 👧 한정판 스니커즈 선착순 구매 시스템 🧑‍⚕️
MSA를 활용해 **선착순 구매 시스템**을 구현해, 대량의 트래픽을 처리할 수 있는 환경에 집중 했습니다.


## 🔎 프로젝트 개요


## 🧑‍💻 프로젝트 인원
| Back-end Developer  |
| ------------- |
| <img src="https://github.com/user-attachments/assets/f881f19f-add6-4b84-a778-8139b05ab3b0" style="width:250px"/> |
| 홍우진  |
| 서버, 기획, Spring Boot, Mysql, REDIS, RabbitMQ, MSA, Spring Cloud |


## ⭐ 기능 소개

Redis 싱글 스레드 특성을 활용한 상품 재고 관리 및 동시성 처리

Spring Cloud Bus와 RabbitMQ를 활용하여 분산 시스템의 원격 설정 파일을 자동으로 갱신하고 동기화하는 기능 구현.

Api Gateway를 통한 라우팅 및 인가 기능 구현

신뢰성이 높은 Google SMTP를 사용한 이메일 인증 도입


1. **메인 기능**
    + 전문 치료 영역, 성별, 경력 등 간단한 서베이를 통해 필터링한 전문 치료사 리스트 제공
    + 전문가에게 매칭 신청 시 아이 정보, 선호 치료 장소 등을 공유
    + 치료 전문가는 자기소개(포트폴리오) 등록

2. **회원 가입 ・ 로그인**
    + 이메일, 비밀번호, 이름, 성별(전문가), 생년월일(전문가), 주소 입력(Daum 우편번호 API)를 통해 가입 가능
    + 로그인 시 JWT 토큰 발행 및 cookie에 저장

3. **마이페이지(부모)**
    + 아이 등록 시 필요한 전문 영역, 성격 또는 성향 등을 입력
    + 매칭 중인 카드 확인 및 매칭된 전문가 리뷰 가능
    + 로그아웃 기능

4. **마이페이지(전문가)**
    + 자기소개(포트폴리오) 수정 기능
    + 매칭 신청 받은 아이 목록(이름 블라인드) 확인 가능

## 🛠️ 기술 스텍
<div align="left"> 
<p>Common</p>
<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white"/>
<img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/>
<img src="https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=Figma&logoColor=white"/>
<img src="https://img.shields.io/badge/JMeter-D22128?style=flat-square&logo=ApacheJMeter&logoColor=white"/>

<p>Server</p>
<img src="https://img.shields.io/badge/Spring-Boot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA-6DB33F?style=flat-square"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=AmazonAWS&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/>
<img src="https://img.shields.io/badge/MSA-FF7F50?style=flat-square"/>
<img src="https://img.shields.io/badge/RabbitMQ-FF6600?style=flat-square&logo=RabbitMQ&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Cloud-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>


## 🏗 시스템 아키텍쳐

## 🏗 프로세스

![image](https://github.com/user-attachments/assets/3c8b9b99-ed1e-4744-be91-3232d3d5991d)

1. **한정판 상품 입고**
    - 관리자 또는 시스템을 활용하여 상품 재 입고 구상
    - 각 상품 별 유효 기간과 한정 수량을 구성
    - 알림 목록에 등록한 각 (유저, 상품)별 알림 전송  (Rate Limiter 대역폭)
2. **한정판 상품 주문** 
    - 사용자는 **선착순**으로 상품 구매
    - 각 사용자는 동일 상품 재 구매 불가
    - 각 상품당 주문
3. **한정판 상품 결제 처리**
    - 주문 상태 Pending(주문 완료), Completed(배달 완료, 환불 불가) →  해당 상품 재 구매 불가, 재고 감소 -1
    - 주문 상태 Canceled(사용자 주문 취소) → 해당 상품 재 구매 활성화하고 재고 증가 +1
    - 한정판 상품 재고 0 → illegalException (”재고 없음”) → 재 주문 가능 상태

## 📑 ERD
![image](https://github.com/user-attachments/assets/9b9b29be-a9e9-44da-85b2-34e93151dc2e)

### 1. **Users (사용자)**

- **user_id** (PK): 사용자 고유 ID
- **email**: 사용자 이메일
- password: 사용자 비밀번호
- user_name: 사용자 이름
- **phone_number**: 연락처
- **created_at**: 계정 생성일
- 사용자의 기본 정보를 관리, , 로그인 시 사용자 인증에 사용

### 2. **LimitedProducts (한정판 상품)**

- **product_id** (PK): 상품 고유 ID
- **product_name**: 상품명 (예: “Jordan Nike 1 Limited Edition”)
- product_desc: 상품 설명
- **product_price**: 상품 가격
- **product_stock**: 재고 수량
- **created_at**: 상품 생성일
- 한정판 상품 정보를 관리, 재고 및 판매 기간, 상태 등을 포함. 한정판의 특성 고나리.

### 3. **Orders (주문)**

- **order_id** (PK): 주문 고유 ID
- **user_id** (FK): 사용자 ID (사용자 정보 참조)
- **product_id** (FK): 상품 ID (한정판 상품 정보 참조)
- **order_status**: 주문 상태 (예: "pending", "completed", "canceled")
- **order_date**: 주문 생성 일시
- 주문 상태를 관리하여 재고 및 재구매 가능 여부를 결정. 주문 취소 시 재고를 증가시키고 재구매를 활성화하며, 완료된 주문에 대해서는 재구매를 불가능하게 설정합니다.

### 4. **WishlistNotifications (위시리스트 + 알림)**

- **wishlist_id** (PK): 위시리스트 및 알림 고유 ID
- **user_id** (FK): 사용자 ID (사용자 정보 참조)
- **product_id** (FK): 상품 ID (한정판 상품 정보 참조)
- **notification_sent**: 알림 발송 여부 (boolean, default: false)
- 사용자가 상품을 위시리스트에 추가한 경우 재고 입고 및 판매 시작 전 알림을 받을 수 있습니다. 알림이 한 번 전송되면 `notification_sent`가 true로 설정되어 중복 알림을 방지합니다.


## 서비스 기대 효과
1. **아이의 발달 지원 강화**:  많은 아이들이 발달적인 어려움을 겪고 있으나, 적절한 치료 전문가를 찾기 어려운 경우가 많습니다. 이 플랫폼은 아이들의 발달을 지원하고, 부모들이 쉽게 치료 전문가를 찾을 수 있도록 돕습니다.

2. **부모의 편의성 제공**: 부모들은 바쁜 일상 속에서도 아이의 발달에 필요한 치료를 받을 수 있도록 돕기 위해 이 플랫폼을 개발하였습니다. 부모들은 플랫폼을 통해 효과적인 치료 전문가를 찾고 예약할 수 있습니다.
 
3. **전문가와 부모 간의 소통 강화**: 플랫폼을 통해 부모와 치료 전문가 간의 소통을 원활하게 하여 치료의 효과를 높이고, 아이들의 발달을 지속적으로 관리할 수 있도록 합니다.

4. **정보의 투명성과 신뢰성 확보**: 이 플랫폼은 치료 전문가의 프로필과 리뷰를 제공하여 부모들이 신뢰할 수 있는 정보를 확인할 수 있도록 합니다. 이를 통해 부모들은 치료 전문가를 선택할 때 더욱 확신을 갖을 수 있습니다.

5. **사회적 기여**: 발달 지연이나 장애를 가진 아이들과 그들의 가족들에게 효과적인 지원을 제공하여 사회적으로 기여하고자 합니다. 이 플랫폼은 아이들의 발달을 위해 노력하는 가정들을 지원하고 지속적인 발전을 돕는 역할을 수행합니다.

## 📝 Issue

### **MapStruct 매핑을 활용한 변환 성능 및 유지보수성 개선**
기존의 Builder 패턴을 활용한 DTO와 Entity 간의 변환 작업은 API와 필드가 늘어날수록 개발자 시선에서 비효율적이라는 생각이 문득 들었습니다. 추가적으로 변환할 필드를 수동으로 수정해야 하는 번거로움이 있다는 생각에 개선 방법을 모색하던 중, 자바의 매핑 라이브러리 중 하나인 MapStruct를 발견하여 사용하기로 결정했습니다.

#### **MapStruct의 장점**
첫번째로는 MapStruct는 빌드 시 자동으로 컴파일 시점에 변환 로직을 생성합니다. 결과적으로 MapStruct를 사용함으로써 이러한 번거로움을 해결할 수 있었습니다.

두번째로 성능면에서도 차이를 보였습니다. MapStruct는 컴파일 시점에 최적화된 코드를 생성하여 런타임 오버헤드를 줄이는 장점이 있습니다. 반면, Builder 패턴은 변환 시점에 Builder와 변환할 객체를 생성하고 각 필드마다 setter 메서드를 호출해야 하므로 런타임 시 추가적인 오버헤드가 발생합니다.

#### **성능 비교**
성능 측면에서 비교했을 때, Builder 패턴을 사용한 변환의 성능은 184ms였던 반면, MapStruct를 통한 변환 성능은 114ms로 향상되어 기존 API의 성능을 38.04% 개선했습니다.


### **`@ElementCollection`을 활용한 값 타입 컬렉션의 편리함, 성능 개선 및 한계점 분석**
초기 모델에서는 다중 값(Multi-Valued) 데이터를 일대다(One to Many) 관계로 설정하기 위해 별도의 엔티티 클래스를 작성했습니다. 그러나 값이 단순한 데이터가 많아진다면 그로 인해 추가적으로 관리해야할 엔티티들이 늘어나 관리가 힘들어질 수 있겠다고 생각이들어 방법을 모색하던 중, JPA 기능 중 @ElementCollection을 발견했습니다.

#### **`@ElementCollection`의 장점**
첫번째로 `@ElementCollection`는 값 타입 컬렉션으로 JPA가 자동 관리할 수 있게 해줍니다. 엔티티 클래스의 컬렉션 필드를 별도의 엔티티로 매핑하지 않고 데이터베이스에 저장할 수 있도록 도와줍니다. 이는 개발자가 별도의 엔티티 클래스를 만들 필요 없이 코드의 간결성을 유지하는데 편리함을 주는 아주 착한 기능입니다.

두번째로 성능 면에서도 우수한 효과를 보였습니다. 별도의 엔티티로 작성한 경우, 각각의 엔티티에 대해 별도의 INSERT 쿼리가 실행되고 추가적인 트랜잭션 관리가 필요했습니다. 반면에 @ElementCollection을 사용한 경우, 단일 INSERT 쿼리로 처리가 되었습니다. 

#### **성능 비교**
초기 모델에서 별도의 테이블로 1000개의 회원가입 API 삽입 요청을 처리하는 데 5,760ms가 소요되었으나, @ElementCollection을 사용하여 값 타입 컬렉션을 효율적으로 처리함으로써 삽입 요청 성능이 1,311ms로 대폭 개선되었습니다. 결과적으로 439.39% 향상되어 무려 4.4배의 성능 향상이 이루어진 셈입니다.

#### **한계점**
[**제한된 확장성**]

그러나 이 기능에도 단점은 존재합니다. 별도의 엔티티를 만드는 경우와 비교할 때, @ElementCollection을 사용하면 추가적인 필드를 작성할 수 없으며, 비즈니스 로직이나 관계를 포함하기 어렵습니다. 저희 프로젝트는 소규모로 진행되었고, 인증서(Certificate)와 치료 가능한 나이대(Age Range)에 대한 추가적인 속성이나 비즈니스 로직이 추후에도 필요하지 않을 것이라 판단하였습니다. 또한, 아동 발달 치료사라는 직업이 전 세계적으로 많지 않음을 고려하여 이 기능을 활용하게 되었습니다.

### **결론**

- **MapStruct**를 사용함으로써 변환 작업의 성능을 향상시키고 유지보수를 용이하게 할 수 있었습니다.
- **`@ElementCollection`**을 통해 값 타입 컬렉션을 간편하게 관리하고 성능을 크게 개선할 수 있었습니다.
- 각각의 방법에는 장단점이 있으므로, 프로젝트의 요구사항에 맞춰 적절한 방법을 선택하는 것이 중요한것 같습니다.

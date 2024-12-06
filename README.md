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

<details>
<summary>1. **Users (사용자)**</summary>

- **user_id** (PK): 사용자 고유 ID  
- **email**: 사용자 이메일  
- password: 사용자 비밀번호  
- user_name: 사용자 이름  
- **phone_number**: 연락처  
- **created_at**: 계정 생성일  
- 사용자의 기본 정보를 관리, , 로그인 시 사용자 인증에 사용  

</details>

<details>
<summary>2. **LimitedProducts (한정판 상품)**</summary>

- **product_id** (PK): 상품 고유 ID  
- **product_name**: 상품명 (예: “Jordan Nike 1 Limited Edition”)  
- product_desc: 상품 설명  
- **product_price**: 상품 가격  
- **product_stock**: 재고 수량  
- **created_at**: 상품 생성일  
- 한정판 상품 정보를 관리, 재고 및 판매 기간, 상태 등을 포함. 한정판의 특성 고나리.  

</details>

<details>
<summary>3. **Orders (주문)**</summary>

- **order_id** (PK): 주문 고유 ID  
- **user_id** (FK): 사용자 ID (사용자 정보 참조)  
- **product_id** (FK): 상품 ID (한정판 상품 정보 참조)  
- **order_status**: 주문 상태 (예: "pending", "completed", "canceled")  
- **order_date**: 주문 생성 일시  
- 주문 상태를 관리하여 재고 및 재구매 가능 여부를 결정. 주문 취소 시 재고를 증가시키고 재구매를 활성화하며, 완료된 주문에 대해서는 재구매를 불가능하게 설정합니다.  

</details>

<details>
<summary>4. **WishlistNotifications (위시리스트 + 알림)**</summary>

- **wishlist_id** (PK): 위시리스트 및 알림 고유 ID  
- **user_id** (FK): 사용자 ID (사용자 정보 참조)  
- **product_id** (FK): 상품 ID (한정판 상품 정보 참조)  
- **notification_sent**: 알림 발송 여부 (boolean, default: false)  
- 사용자가 상품을 위시리스트에 추가한 경우 재고 입고 및 판매 시작 전 알림을 받을 수 있습니다. 알림이 한 번 전송되면 `notification_sent`가 true로 설정되어 중복 알림을 방지합니다.  

</details>

## 서비스 기대 효과
**효율적인 구매 경험 제공**
한정판 스니커즈 구매 과정에서 발생하는 과도한 경쟁과 서버 다운 문제를 해결합니다. 플랫폼은 Redis의 원자적 연산과 안정적인 아키텍처를 활용해 빠르고 정확한 구매 경험을 제공합니다.

**공정한 구매 기회 제공:**
선착순 구매 시스템을 통해 모든 사용자가 공정하게 동일한 조건에서 상품을 구매할 수 있도록 설계되었습니다. 이는 사용자 만족도를 높이고 플랫폼에 대한 신뢰성을 강화합니다.

**트래픽 관리 최적화:**
Redis Sentinel 기반의 고가용성 아키텍처를 활용해 트래픽이 몰리는 상황에서도 안정적으로 구매 처리가 가능합니다. 서버 부하를 효과적으로 관리하며, 구매 중단 없는 서비스를 보장합니다.

**투명성과 신뢰성 강화:**
구매 성공 여부를 실시간으로 투명하게 제공하며, 재고 상태를 신속히 업데이트하여 사용자에게 신뢰할 수 있는 정보를 제공합니다. 이를 통해 구매 프로세스 전반에 걸친 신뢰성을 강화합니다.



## **📝 Issue**

### **1. Redis Cluster 도입, 과연 필요할까?**
한정판 스니커즈 구매 시스템은 **실시간 트래픽 처리**와 **재고 관리**가 핵심입니다. Redis의 **원자적 연산**을 활용해 재고 처리를 수행하는 시스템에서 초기 단계에 Redis Cluster 아키텍처를 도입할지 고민했습니다.

그러나, "만약 이 시스템을 내가 직접 운영하고 **내 돈으로 비용을 지불**해야 한다면?"이라는 현실적인 관점에서 **비용 효율성과 운영 용이성**을 고려한 최적화 방안을 찾았습니다.

---

## **📊 시스템 분석 및 개선 방향**

### **1. 시스템 요구사항**
- **재고 규모**:  
  한정판 스니커즈는 **최대 10,000개 이하**의 소규모 재고. Redis 단일 노드에서 충분히 처리 가능.
  
- **트래픽 규모**:  
  특정 상품에 트래픽이 몰리는 경우 Redis 단일 노드가 초당 **10만 ~ 100만 요청**을 처리할 수 있어 충분한 성능 보장.
  
- **확장성**:  
  초기에는 비용을 절감하고 운영 복잡성을 최소화하기 위해 단일 노드로 시작. 필요 시 Redis Cluster로 자연스럽게 확장.

---

### **2. Redis Sentinel 기반 HA 아키텍처**
- **구조**:  
  - 하나의 **마스터 노드**와 두 개의 **슬레이브 노드**로 구성.  
  - **Redis Sentinel**을 통해 장애 발생 시 자동으로 마스터를 슬레이브로 승격하여 고가용성 확보.

- **장점**:  
  - **비용 절감**: Cluster 도입 없이도 안정적인 장애 복구 제공.  
  - **운영 간소화**: 설정과 관리가 간단하며 필요 시 확장 가능.

---

### **3. Write-back 방식 개선**
- **기존 문제**:  
  - 60초 주기로 Redis 데이터를 RDB와 동기화하여 장애에 대비했지만, **Disk I/O 부담**과 성능 저하 발생.

- **개선 방안**:  
  - RDB 동기화 주기를 **자정 1회**로 줄여 Disk I/O를 최소화.  
  - Redis 복제를 활용해 장애 발생 시 데이터 손실을 방지.

---

## **⚙️ Redis 원자적 연산 활용**

### **1. 재고 감소 처리**
- **Lua 스크립트를 활용한 원자적 연산**으로 동시 요청 처리와 데이터 일관성 확보.  
- **재고가 0**이 되면 Redis 상태를 기반으로 구매 차단.  

```lua
local stock = redis.call("GET", KEYS[1])
if tonumber(stock) > 0 then
    redis.call("DECR", KEYS[1])
    return 1 -- 성공
else
    return 0 -- 실패
end

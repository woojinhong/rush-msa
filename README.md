# 👧 한정판 스니커즈 선착순 구매 시스템 🧑‍⚕️
**한정판 스니커즈 선착순 구매 시스템**은

대량 트래픽 환경에서도 안정적이고 신속하게 한정판 스니커즈를 선착순으로 구매할 수 있도록 설계 하였습니다.

이 프로젝트는 트래픽 부하를 고려해 MSA 기반으로 설계되었으며, 핵심 기술로 **Redis**와 **Kafka**를 활용해 구현 하였습니다.


## 🔎 프로젝트 메인 목표

- **MSA**를 도입하여 **서비스** 간 **결합도**를 **낮추고**, 높은 트래픽 환경을 가정하여 **Auto Scaling** 이 **용이한** **환경**을 **구축** 

- **분산 환경**에서 선착순 구매의 **신속한 처리**와 **동시성 제어** **문제**를 효과적으로 **해결**



## 🧑‍💻 프로젝트 인원
| Back-end Developer|
| ------ |
| <img src="https://github.com/user-attachments/assets/f881f19f-add6-4b84-a778-8139b05ab3b0" style="width:250px"/> |
| 홍우진  |
| 서버, 기획, Spring Boot, MySQL, Kafka
Redis, RabbitMQ, MSA, Spring Cloud|


## ⭐ 주요 기능 및 기술

**MSA 아키텍처**

- **Eureka Server:** 서비스 디스커버리 및 로드 밸런싱 지원
- **API Gateway:** 사용자 인증 처리 및 라우팅 담당
- **Config Server:** 중앙 집중식 설정 관리 및 RabbitMQ를 통한 설정 변경 자동화

**주요 서비스**

- **User Service:** 사용자 인증 및 로그인 관리
- **Product Service:** 상품 정보 조회 및 재고 관리
- **Order Service:** 선착순 주문 및 결제 처리

**동시성 제어 및 성능 최적화**

- **Redis Sentinel:** 고가용성을 보장하며 Failover를 통한 장애 복구
- **Lua Script:** 원자적 연산을 통해 재고 감소 및 데이터 일관성 확보
- **Kafka:** 주문 및 재고 처리를 비동기 이벤트 기반으로 수행하여 DB 부하 분산

**캐싱 및 트래픽 관리**

- Redis의 싱글 스레드 특성을 활용한 빠른 응답 시간 보장
- Redis Write-Back 방식으로 주기적으로 DB와 동기화



## 🛠️ 기술 스텍
<div align="left"> 
    <p>Common</p>
    <img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white"/>
    <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/> 
    <img src="https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=Figma&logoColor=white"/> 
    <img src="https://img.shields.io/badge/JMeter-D22128?style=flat-square&logo=ApacheJMeter&logoColor=white"/>
    <p>Server</p>
    <img src="https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/>
    <img src="https://img.shields.io/badge/JPA-6DB33F?style=flat-square"/>
    <img src="https://img.shields.io/badge/Java-21.0.5-007396?style=flat-square&logo=OpenJDK&logoColor=white"/> 
    <img src="https://img.shields.io/badge/MySQL-8.0.33-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> 
    <img src="https://img.shields.io/badge/Redis-3.0.504-DC382D?style=flat-square&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=AmazonAWS&logoColor=white"/> 
    <img src="https://img.shields.io/badge/MSA-FF7F50?style=flat-square"/>
    <img src="https://img.shields.io/badge/Kafka-3.9.0-231F20?style=flat-square&logo=ApacheKafka&logoColor=white"/>
    <img src="https://img.shields.io/badge/RabbitMQ-3.1.7-FF6600?style=flat-square&logo=RabbitMQ&logoColor=white"/> 
    <img src="https://img.shields.io/badge/Spring_Cloud-2023.0.3-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> </div>


## 🏗 시스템 아키텍쳐

![제목 없음-2024-11-29-1505](https://github.com/user-attachments/assets/4a58e3dd-3459-4b7d-8e6c-5035a01c7455)


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
<summary>1. Users (사용자)</summary>

- **user_id** (PK): 사용자 고유 ID  
- **email**: 사용자 이메일  
- password: 사용자 비밀번호  
- user_name: 사용자 이름  
- **phone_number**: 연락처  
- **created_at**: 계정 생성일  
- 사용자의 기본 정보를 관리, , 로그인 시 사용자 인증에 사용  

</details>

<details>
<summary>2. LimitedProducts (한정판 상품)</summary>

- **product_id** (PK): 상품 고유 ID  
- **product_name**: 상품명 (예: “Jordan Nike 1 Limited Edition”)  
- **product_desc** 상품 설명  
- **product_price**: 상품 가격  
- **product_stock**: 재고 수량  
- **created_at**: 상품 생성일  
- 한정판 상품 정보를 관리, 재고 및 판매 기간, 상태 등을 포함. 한정판의 특성 고나리.  

</details>

<details>
<summary>3. Orders (주문)</summary>

- **order_id** (PK): 주문 고유 ID  
- **user_id** (FK): 사용자 ID (사용자 정보 참조)  
- **product_id** (FK): 상품 ID (한정판 상품 정보 참조)  
- **order_status**: 주문 상태 (예: "pending", "completed", "canceled")  
- **order_date**: 주문 생성 일시  
- 주문 상태를 관리하여 재고 및 재구매 가능 여부를 결정. 주문 취소 시 재고를 증가시키고 재구매를 활성화하며, 완료된 주문에 대해서는 재구매를 불가능하게 설정합니다.  

</details>

<details>
<summary>4. WishlistNotifications (위시리스트 + 알림)</summary>

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

## **📝 개선 사항**

- **테스트 사양**: AMD Ryzen 5 5500U with Radeon Graphics 2.10 GHz   6 코어 12 멀티 스레드
- AMD64 Family 23 Model 104 Stepping 1 AuthenticAMD ~2100Mhz
- RAM: 18,294MB 18.2GB
- 사용 가능 메모리: 5,854MB  5.8GB
- **테스트 툴**: Apache JMeter
- **테스트 스크립트**: **`10000` 스레드** 동시 요청

![pessimistic_test](https://github.com/user-attachments/assets/87931262-7466-4fe1-911c-3d53a872a4dc)
![redis_test](https://github.com/user-attachments/assets/d5207117-7f9b-4d3a-bc4c-a82a00da907a)

## 테스트 결과 비교

| Metric                         | 일반 요청 값      | Redis 테스트 값   |
|---------------------------------|-------------------|-------------------|
| 요청 수                         | 10,000             | 10,000             |
| 평균 응답 시간 (ms)             | 2,268 ms          | 72 ms             |
| 최소 응답 시간 (ms)             | 31 ms             | 5 ms              |
| 최대 응답 시간 (ms)             | 3,867 ms          | 374 ms            |
| 표준 편차 (ms)                  | 965.92 ms         | 68.27 ms          |
| 처리량 (Throughput)             | 205.5/sec         | 759.3/sec         |
| 에러율 (Error %)                | 0.00%             | 0.00%             |
| Received KB/sec                 | 24.28 KB/s        | 89.72 KB/s        |
| Sent KB/sec                     | 28.50 KB/s        | 100.84 KB/s       |
| 평균 데이터 전송량 (Avg. Bytes) | 121.0 Bytes       | 121.0 Bytes       |


- **ThroughPut(TPS):  205.5/sec -> 759.3/sec 약 290% 성능 개선**



## **📝 Issue**

### **1. Redis Cluster 도입, 과연 필요할까?**
한정판 스니커즈 구매 시스템은 **실시간 트래픽 처리**와 **재고 관리**가 핵심입니다. Redis의 **원자적 연산**을 활용해 재고 처리를 수행하는 시스템에서 초기 단계에 Redis Cluster 아키텍처를 도입할지 고민했습니다.

그러나, "만약 이 시스템을 내가 직접 운영하고 **내 돈으로 비용을 지불**해야 한다면?"이라는 현실적인 관점에서 **비용 효율성과 운영 용이성**을 고려한 최적화 방안을 찾았습니다.

[⚙️Redis Sentinel 아키텍처 도입 이유](https://www.notion.so/Redis-14d2b5fb2d0880f4a8e0f49832532a0d)
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

## **⚙️ lua Script를 활용한 Redis 원자적 연산**

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


## ⚙️ 미래 개선 사항
- 마스터-슬레이브 복제 시스템을 구축하여 멀티 서버 환경을 구축하여 Sentinel 로 Failover 설정 하였지만, 물리적인 하드웨어는 단일 서버이기 때문에 레디스 서버 장애는 failover 처리로 복구가 가능하지만 하드웨어 자체에 장애 발생 시 데이터 유실 발생 가능

- 실제 운영 시 물리적인 하드웨어를 두대 이상 두어서 데이터 유실 발생 방지를 하여 좀 더 안전한 시스템을 구축하기



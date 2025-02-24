# 인덱스(index)를 이용한 쿼리 성능 개선 보고서

---

## 목차
1. [인덱스 사용 목적](#1-인덱스-사용-목적)
2. [단일 인덱스와 복합 인덱스](#2-단일-인덱스와-복합-인덱스)
3. [인덱스 적용 및 성능 비교](#3-인덱스-적용-및-성능-비교)
4. [결론](#4-결론)

---
## 1. 인덱스 사용 목적
e-commerce 시나리오에서 **최근 3일 간 인기상품 TOP5 조회** 서비스에 인덱스를 적용해보았습니다.
해당 서비스에서 사용하는 쿼리는 **다중 JOIN과 WHERE 조건, GROUP BY, ORDER BY를 포함**하고 있어, 대량의 데이터를 처리할 때 성능 저하가 예상되었습니다. 따라서, 인덱스를 활용하여 쿼리 성능을 개선할 필요가 있었습니다.

인덱스를 사용하지 않는다면 다음과 같은 문제가 발생할 수 있습니다.

1. 전체 데이터를 검색(Full Table Scan)으로 인해 응답 시간이 길어진다.
2. 불필요한 데이터까지 조회하여 시스템 리소스를 과도하게 사용하게 된다.
3. 웹 서비스 및 애플리케이션에서 응답 속도가 느려져 사용자 경험 저하로 이어진다.

따라서, 성능 개선을 위해 인덱스를 설정하여 쿼리 성능을 최적화하는 과정을 수행해보았습니다.

---

## 2. 단일 인덱스와 복합 인덱스
### 단일 인덱스
- 단일 인덱스를 생성할 때는 카디널리티가 높은 것으로 잡는다.
    - 카디널리티가 높다는 것은, 한 컬럼이 갖고 있는 값의 중복도가 낮음을 의미한다.
- 특정 컬럼에서 데이터를 검색할 때 성능을 크게 향상시킨다.
- 두 개 이상의 조건을 동시에 검색할 때 단일 인덱스는 성능이 떨어진다.

### 복합 인덱스
- 두 개 이상의 조건을 동시에 검색할 때 성능을 크게 향상시킨다.
- 정렬 및 필터링 조건이 함께 사용될 때 사용하는 것이 좋다.
- 순서에 따라 첫 번째 컬럼만 검색할 때 단일 인덱스처럼 활용할 수 있다.
    - `users(name, age)`로 복합 인덱스 설정 시 `name`만 검색할 때도 인덱스를 활용할 수 있지만, `age`만 검색하는 경우는 인덱스를 활용하지 못할 수 있다.

---

## 3. 인덱스 적용 및 성능 비교
주문 100만 건, 주문 상세 200만 건, 상품 100만 건으로 가데이터 생성 후 진행하였습니다.

### 최근 3일 간 인기상품 TOP5 조회
최근 3일 동안 주문완료(COMPLETE)된 건들 중 상품 별 주문 수량을 합산하여 상위 5개 인기 상품을 조회한다.
```sql
SELECT P.ID
     , P.NAME
     , P.PRICE
     , SUM(OP.QUANTITY) AS TOTAL_QUANTITY
  FROM ORDER_PRODUCT OP
  JOIN ORDERS O 
    ON OP.ORDER_ID = O.ID
  JOIN PRODUCT P
    ON OP.PRODUCT_ID = P.ID
 WHERE O.STATUS = 'COMPLETE'
   AND O.CREATE_AT >= NOW() - INTERVAL 3 DAY
 GROUP BY P.ID
 ORDER BY SUM(OP.QUANTITY) DESC
 LIMIT 5;
```

### 실행 계획(EXPLAIN)을 통해 쿼리 분석
#### 인덱스 추가 전: 5 row(s) fetched - 20.948s
| id | select_type | table | partitions | type   | possible_keys | key     | key_len | ref                       | rows   | filtered | Extra                                      |
|----|------------|-------|------------|--------|--------------|---------|---------|---------------------------|--------|----------|--------------------------------------------|
| 1  | SIMPLE     | OP    | [NULL]     | ALL    | [NULL]       | [NULL]  | [NULL]  | [NULL]                    | 1,991,860 | 100      | Using where; Using temporary; Using filesort |
| 2  | SIMPLE     | O     | [NULL]     | eq_ref | PRIMARY      | PRIMARY | 8       | hhplus.OP.order_id        | 1      | 16.66    | Using where                               |
| 3  | SIMPLE     | P     | [NULL]     | eq_ref | PRIMARY      | PRIMARY | 8       | hhplus.OP.product_id      | 1      | 100      | [NULL]                                    |

```shell
EXPLAIN ANALYZE 실행 결과
-> Limit: 5 row(s)  (actual time=20948..20948 rows=5 loops=1)
    -> Sort: TOTAL_QUANTITY DESC, limit input to 5 row(s) per chunk  (actual time=20948..20948 rows=5 loops=1)
        -> Table scan on <temporary>  (actual time=20755..20871 rows=743111 loops=1)
            -> Aggregate using temporary table  (actual time=20755..20755 rows=743110 loops=1)
                -> Nested loop inner join  (cost=1.86e+6 rows=331943) (actual time=3..14088 rows=1.58e+6 loops=1)
                    -> Nested loop inner join  (cost=1.56e+6 rows=331943) (actual time=2.93..7811 rows=1.58e+6 loops=1)
                        -> Filter: ((OP.order_id is not null) and (OP.product_id is not null))  (cost=207677 rows=1.99e+6) (actual time=2.8..520 rows=2e+6 loops=1)
                            -> Table scan on OP  (cost=207677 rows=1.99e+6) (actual time=2.8..404 rows=2e+6 loops=1)
                        -> Filter: ((O.`status` = 'COMPLETE') and (O.created_at >= <cache>((now() - interval 3 day))))  (cost=0.58 rows=0.167) (actual time=0.00351..0.00356 rows=0.789 loops=2e+6)
                            -> Single-row index lookup on O using PRIMARY (id = OP.order_id)  (cost=0.58 rows=1) (actual time=0.00331..0.00333 rows=1 loops=2e+6)
                    -> Single-row index lookup on P using PRIMARY (id = OP.product_id)  (cost=0.805 rows=1) (actual time=0.00386..0.00388 rows=1 loops=1.58e+6)

```

- 테이블 `OP`에 대한 Full Table Scan (ALL) 발생 (1,991,860 rows를 전부 읽음)
- `O` 테이블과 `P` 테이블을 `OP`와 조인하면서 Nested loop inner join 구조가 형성
- `Using where; Using temporary; Using filesort`가 발생하여 성능 저하
- 현재 `Sort: TOTAL_QUANTITY DESC`가 발생하면서 filesort가 동작
- 정렬 및 집계 과정에서 임시 테이블이 사용되며, 정렬 비용이 크고 시간이 오래 걸림


→ `ORDERS(status, created_at)`, `ORDER_PRODUCT(product_id, order_id, quantity)` 복합 인덱스 추가


<br>

#### 인덱스 추가 후: 5 row(s) fetched - 5.187s
| id | select_type | table | partitions | type   | possible_keys                      | key                      | key_len | ref                     | rows   | filtered | Extra                                      |
|----|------------|-------|------------|--------|----------------------------------|-------------------------|---------|-------------------------|--------|----------|--------------------------------------------|
| 1  | SIMPLE     | P     | [NULL]     | index  | PRIMARY                          | PRIMARY                 | 8       | [NULL]                  | 996,632 | 100      | Using temporary; Using filesort           |
| 2  | SIMPLE     | OP    | [NULL]     | ref    | idx_order_product_group          | idx_order_product_group | 9       | hhplus.P.id             | 2      | 100      | Using where; Using index                  |
| 3  | SIMPLE     | O     | [NULL]     | eq_ref | PRIMARY, idx_orders_status_date  | PRIMARY                 | 8       | hhplus.OP.order_id      | 1      | 50       | Using where                               |

```shell
EXPLAIN ANALYZE 실행 결과
-> Limit: 5 row(s)  (actual time=5187..5187 rows=5 loops=1)
    -> Sort: p.id, TOTAL_QUANTITY DESC, limit input to 5 row(s) per chunk  (actual time=5187..5187 rows=5 loops=1)
        -> Stream results  (cost=2.49e+6 rows=996632) (actual time=1.37..5104 rows=644454 loops=1)
            -> Group aggregate: sum(OP.quantity)  (cost=2.49e+6 rows=996632) (actual time=1.37..5004 rows=644454 loops=1)
                -> Nested loop inner join  (cost=2.21e+6 rows=1.23e+6) (actual time=1.34..4842 rows=1.16e+6 loops=1)
                    -> Nested loop inner join  (cost=1.35e+6 rows=2.46e+6) (actual time=1.31..1632 rows=2e+6 loops=1)
                        -> Index scan on P using PRIMARY  (cost=102329 rows=996632) (actual time=1.29..162 rows=1e+6 loops=1)
                        -> Filter: (OP.order_id is not null)  (cost=1.01 rows=2.47) (actual time=0.00101..0.00135 rows=2 loops=1e+6)
                            -> Covering index lookup on OP using idx_order_product_group (product_id = P.id)  (cost=1.01 rows=2.47) (actual time=936e-6..0.0012 rows=2 loops=1e+6)
                    -> Filter: ((O.`status` = 'COMPLETE') and (O.created_at >= <cache>((now() - interval 3 day))))  (cost=0.25 rows=0.5) (actual time=0.0015..0.00153 rows=0.578 loops=2e+6)
                        -> Single-row index lookup on O using PRIMARY (id = OP.order_id)  (cost=0.25 rows=1) (actual time=0.00133..0.00135 rows=1 loops=2e+6)

```

- Full Table Scan이 제거되고, Index Scan 및 Covering Index 사용
- `OP` 테이블이 `idx_order_product_group`을 통해 최적화되어 Nested Loop Join의 성능 향상.

인덱스를 적용해보았을 때, `quantity`를 복합 인덱스에 추가하는가 마는가의 차이로 쿼리 실행 성능 차이가 있었는데 (추가하면 5.x초, 안하면 7.x초),
이는 SUM 집계를 하기 때문에 인덱스에 함께 포함하면 데이터를 바로 인덱스에서 읽어오는 **커버링 인덱스** 덕분이라는 것을 새롭게 알게되었다.
커버링 인덱스는 **쿼리에 필요한 모든 컬럼이 인덱스에 포함되어 테이블 접근 없이 결과를 즉시 반환**해준다.
하지만, 인덱스 크기가 커질수록 **쓰기 성능이 저하**될 수 있는 위험이 있으니 고려해서 사용해야 한다.

---

## 4. 결론
다중 JOIN과 GROUP BY, ORDER BY를 포함한 쿼리는 대량의 데이터를 처리할 때 Full Table Scan이 발생하여 성능 저하가 심각할 수 있음을 확인했다. 이를 해결하기 위해 `ORDERS(status, created_at)`, `ORDER_PRODUCT(product_id, order_id, quantity)`에 복합 인덱스를 추가한 결과, 쿼리 실행 시간이 20.948초 → 5.187초로 **약 4배 이상 성능 개선**됨을 확인할 수 있었다.

특히, `SUM(OP.quantity)` 집계를 위해 `quantity`를 복합 인덱스에 포함했을 때, 커버링 인덱스 덕분에 불필요한 테이블 접근 없이 인덱스에서 바로 데이터를 조회할 수 있었고, 이로 인해 쿼리 실행 성능이 크게 향상되었다.

하지만, 인덱스 크기가 커지면 쓰기(INSERT, UPDATE, DELETE) 성능이 저하될 위험이 있으며, 인덱스가 많아질수록 실행 계획 최적화가 어려워질 수 있다는 점도 고려해야 한다.

따라서, 인덱스는 쿼리 성능을 개선하는 가장 좋은 방법이지만,
제공하는 서비스에 따라 읽기 성능을 높일 것인지, 쓰기 성능을 높일 것인지 고려하여 설계하는 것이 중요하다는 것을 배울 수 있었다.

---

## + 인덱스가 많으면 발생할 수 있는 문제점
- 데이터 변경 (INSERT, UPDATE, DELETE) 때마다 모든 인덱스를 갱신해야 하므로 성능 저하가 발생한다.
- DBMS가 실행 계획을 세울 때 최적의 인덱스를 선택하는 데 시간이 오래 걸린다.
- 인덱스가 많으면 잘못된 실행 계획을 선택할 수도 있다.




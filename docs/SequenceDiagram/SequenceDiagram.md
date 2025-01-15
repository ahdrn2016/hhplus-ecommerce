# 시퀀스 다이어그램

---

## 1. 잔액 충전
```mermaid
sequenceDiagram
    actor I as 유저
    participant Point as Point
    participant PointHistory as PointHistory

    I ->> Point : 잔액 충전 요청
    alt 충전 금액 <= 0
        Point -->> I : 잔액 충전 실패 
    else 충전 금액 > 0
        Point ->> Point : 보유 잔액 조회 + 요청 잔액
        Point ->> PointHistory : 잔액 충전 기록 요청
        PointHistory -->> Point : 잔액 충전 기록
        Point -->> I : 잔액 충전 성공
    end
    
```

## 2. 상품 조회
```mermaid
sequenceDiagram
    actor I as 유저
    participant Product as Product

    I ->> Product : 상품 목록 조회 요청
    Product ->> Product : 판매 상태가 SELLING인 상품 조회
    Product -->> I : 상품 목록 반환
    
```

## 3. 쿠폰 발급
```mermaid
sequenceDiagram
    actor I as 유저
    participant IssuedCoupon as IssuedCoupon
    participant Coupon as Coupon

    I ->> IssuedCoupon : 유저가 쿠폰 있는지 조회
    alt 유저가 쿠폰 있으면
        IssuedCoupon -->> I : 쿠폰 발급 실패 
    else 유저가 쿠폰 없으면
        IssuedCoupon ->> Coupon : 선착순 재고 조회
        alt 선착순 재고 없으면
            Coupon -->> I : 쿠폰 발급 실패
        else 선착순 재고 있으면
            Coupon ->> Coupon : 쿠폰 선착순 재고 차감
            Coupon -->> I : 쿠폰 발급 성공
        end
    end
```

## 4. 주문
```mermaid
sequenceDiagram
    actor I as 유저
    participant OrderFacade as OrderFacade
    participant Product as Product
    participant Order as Order
    participant Coupon as Coupon
    participant Payment as Payment
    participant Point as Point
    participant DataPlatform as DataPlatform
    
    I ->> OrderFacade : 주문 요청
    OrderFacade ->> Product : 주문 금액 계산
    Product -->> OrderFacade : 주문 금액 반환
    OrderFacade ->> Order : 주문 생성
    Order -->> OrderFacade : 주문 반환
    OrderFacade ->> Coupon : 쿠폰 확인 요청
        alt 쿠폰 사용 불가하면
            Coupon -->> OrderFacade : null 반환
        else 쿠폰 사용 가능하면
            Coupon -->> OrderFacade : 쿠폰 정보 반환
        end
    OrderFacade ->> Payment : 결제 요청
    Payment -->> OrderFacade : 결제 반환
    OrderFacade ->> Point : 포인트 사용 요청
        alt 포인트 부족하면
            Point -->> I : 주문 실패
        else 포인트 충분하면
            Point -->> OrderFacade : 포인트 사용
        end
    OrderFacade ->> Product : 재고 차감 요청
        alt 재고 부족하면
            Product -->> I : 주문 실패
        else 재고 충분하면
            Product -->> OrderFacade : 재고 차감
        end
    OrderFacade ->> Order : 주문 완료 요청
    Order -->> OrderFacade : 주문 완료 처리
    Order ->> DataPlatform : 주문 데이터 외부로 전송
    OrderFacade -->> I : 주문 완료
    
```

## 5. 인기 상품 조회
```mermaid
sequenceDiagram
    actor I as 유저
    participant OrderDetail as OrderDetail

    I ->> OrderDetail : 인기 상품 조회 요청
    OrderDetail -->> I : 인기 상품 목록 반환
```
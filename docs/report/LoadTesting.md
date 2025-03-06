# 서비스 부하 테스트 분석 보고서

---

## 목차
1. [개요](#1-개요)
2. [부하 테스트 대상 선정 및 목표](#2-부하-테스트-대상-선정-및-목표)
3. [테스트 스크립트 및 결과](#3-테스트-스크립트-및-결과)
4. [결론](#4-결론)

---
## 1. 개요
부하 테스트를 통해 현재 서비스가 높은 트래픽 환경에서도 정상적으로 부하를 처리할 수 있는지 확인하고, 시스템 성능 저하나 병목 현상이 발생하는 지점을 파악하기 위해 수행되었습니다.<br>
k6를 활용하여 부하 테스트 진행 후, 응답 속도, 오류 발생 여부 및 초당 처리량(TPS) 등을 측정하여, 서비스의 안정성을 분석하고 문제가 발견되면 개선 방향을 제시하고자 합니다.

---
## 2. 부하 테스트 대상 선정 및 목표

현재 이커머스 시나리오 중 **선착순 쿠폰 발급** 서비스에 순간 트래픽이 가장 많이 몰릴 것으로 예상하여 부하 테스트 대상으로 선정하였습니다.<br>
추가적으로 유저가 발급 받은 쿠폰을 사용하기 위해 **인기 상품 조회** 서비스를 이용할 것이라는 가정 하에 시나리오를 작성했습니다.

<br>

### 테스트 시나리오: 선착순 쿠폰 발급 + 인기 상품 조회
다수의 유저가 동시에 접근하여 선착순 쿠폰을 발급 요청한 후, 발급 받은 쿠폰을 사용하기 위해 인기 상품 조회하는 상황을 시뮬레이션합니다.
- 목표
    - 단시간에 많은 요청이 몰릴 때 서버가 정상적으로 선착순 쿠폰 발급 요청을 처리하는지 확인
    - 쿠폰 발급 후, 인기 상품 조회 요청이 원활하게 수행되는지 확인
    - 동시 접속자가 몰릴 때 API 응답 속도 및 오류 발생 여부 확인
- 방법
    - 초기부터 500 VU가 지속적으로 인기 상품을 조회하도록 `constant-vus` 설정
    - 선착순 쿠폰 발급 시작 시 유저가 급증하고, 감소하는 상황을 시뮬레이션 하기 위해 `ramping-vus` 설정
    - 유저는 선착순 쿠폰 발급 후, 발급받은 쿠폰을 사용하기 위해 인기 상품 조회 수행하도록 `ramping-vus` 설정

---
## 3. 테스트 스크립트 및 결과
### 3-1. 테스트 스크립트
```js
import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    scenarios: {
        // 1. 인기 상품 조회
        popular_products_scenario: {
            executor: 'constant-vus',
            vus: 500,  // 초기부터 500 VU 유지
            duration: '40s',  // 전체 테스트 지속 시간
            exec: 'popularProducts',
        },

        // 2. 쿠폰 발급 요청 (이벤트 시작 후 빠르게 증가)
        coupon_issue_scenario: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '10s', target: 1000 }, // 10초 동안 0 -> 1000 VU 증가
                { duration: '20s', target: 1000 }, // 20초 동안 1000 VU 유지
                { duration: '10s', target: 0 },   // 10초 동안 1000 -> 0 VU 감소
            ],
            exec: 'couponIssue',
        },

        // 3. 쿠폰 발급 후 유저들이 인기 상품 조회로 몰리는 시나리오
        coupon_to_popular_scenario: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '10s', target: 500 }, // 10초 동안 0 -> 500 VU 증가 (쿠폰 발급 후 인기 상품 조회로 이동)
                { duration: '20s', target: 1000 }, // 20초 동안 1000 VU 유지
                { duration: '10s', target: 0 },    // 10초 동안 1000 -> 0 VU 감소
            ],
            exec: 'popularProducts',
            startTime: '15s', // 쿠폰 발급 시작 후 15초 뒤에 실행 (쿠폰 발급 완료 후 유입되는 시뮬레이션)
        },
    },
};

export function couponIssue() {
    const url = 'http://localhost:8080/api/v1/coupon/issue';
    const payload = JSON.stringify({
        userId: Math.floor(Math.random() * 1000000) + 1,
        couponId: 1
    });
    const params = {
        headers: {
            'Content-Type': 'application/json'
        },
    };
    const res = http.post(url, payload, params);

    check(res, {
        'status is 200': (r) => r.status === 200
    });

    sleep(2);
}

export function popularProducts() {
    const url = 'http://localhost:8080/api/v1/product/popular';
    const params = {
        headers: {
            'Content-Type': 'application/json'
        }
    };
    const res = http.get(url, params);

    check(res, {
        'status is 200': (r) => r.status === 200
    });

    sleep(1);
}
```

<br>

### 3-2. 테스트 결과
```shell

         /\      Grafana   /‾‾/  
    /\  /  \     |\  __   /  /   
   /  \/    \    | |/ /  /   ‾‾\ 
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: script.js
        output: -

     scenarios: (100.00%) 3 scenarios, 2500 max VUs, 1m25s max duration (incl. graceful stop):
              * coupon_issue_scenario: Up to 1000 looping VUs for 40s over 3 stages (gracefulRampDown: 30s, exec: couponIssue, gracefulStop: 30s)
              * popular_products_scenario: 500 looping VUs for 40s (exec: popularProducts, gracefulStop: 30s)
              * coupon_to_popular_scenario: Up to 1000 looping VUs for 40s over 3 stages (gracefulRampDown: 30s, exec: popularProducts, startTime: 15s, gracefulStop: 30s)


     ✓ status is 200

     checks.........................: 100.00% 44342 out of 44342
     data_received..................: 17 MB   310 kB/s
     data_sent......................: 6.4 MB  116 kB/s
     http_req_blocked...............: avg=192.8µs  min=0s    med=3µs      max=36.55ms  p(90)=23µs  p(95)=177µs
     http_req_connecting............: avg=165.95µs min=0s    med=0s       max=25.93ms  p(90)=0s    p(95)=136µs
     http_req_duration..............: avg=456.88ms min=547µs med=434.7ms  max=5.43s    p(90)=1.26s p(95)=1.39s
       { expected_response:true }...: avg=456.88ms min=547µs med=434.7ms  max=5.43s    p(90)=1.26s p(95)=1.39s
     http_req_failed................: 0.00%   0 out of 44342
     http_req_receiving.............: avg=93.6µs   min=4µs   med=33µs     max=117.62ms p(90)=117µs p(95)=169µs
     http_req_sending...............: avg=114.77µs min=2µs   med=8µs      max=18.73ms  p(90)=32µs  p(95)=63µs 
     http_req_tls_handshaking.......: avg=0s       min=0s    med=0s       max=0s       p(90)=0s    p(95)=0s   
     http_req_waiting...............: avg=456.67ms min=481µs med=434.62ms max=5.43s    p(90)=1.26s p(95)=1.39s
     http_reqs......................: 44342   796.196028/s
     iteration_duration.............: avg=1.68s    min=1s    med=1.51s    max=7.43s    p(90)=3.26s p(95)=3.39s
     iterations.....................: 44342   796.196028/s
     vus............................: 69      min=69             max=2138
     vus_max........................: 2500    min=2500           max=2500


running (0m55.7s), 0000/2500 VUs, 44342 complete and 0 interrupted iterations
coupon_issue_scenario      ✓ [======================================] 0000/1000 VUs  40s
popular_products_scenario  ✓ [======================================] 500 VUs        40s
coupon_to_popular_scenario ✓ [======================================] 0000/1000 VUs  40s
```

- **http 요청 성공률 (checks, http_req_failed)**: 성공 응답 비율 100%, 요청 실패율은 0%로 현재 작성된 테스트 스크립트는 모든 요청이 정상적으로 처리되었다.
- **http 요청 응답 시간 (http_req_duration)**: 평균 응답 시간(456.88ms)은 괜찮지만, 90% 이상의 요청이 1.26초 이상 걸리고, 최대 5.43초까지 증가한 것은 주의가 필요하다.
- **초당 처리량 TPS (http_reqs)**: 서버가 초당 796건 요청 처리로 부하를 잘 처리했지만, 응답 시간이 길어진 부분이 병목이 될 가능성이 있다.
- **가상 사용자 수 (vus, vus_max)**: 설정된 최대 2500 VU 중 2138 VU까지만 실행되었지만, 이는 현재 서비스가 적은 VU만으로도 목표 부하를 충분히 감당할 수 있었기 때문이며, 정상적으로 부하를 견디고 있다.

---
## 4. 결론
테스트 결과, 모든 요청이 정상적으로 처리(성공률 100%)되었으며, 서버는 부하를 감내할 수 있는 수준이었습니다.<br>
다만, 일부 요청에서 최대 5.43초까지 응답 시간이 증가하는 현상이 발생하여, DB 쿼리 최적화 및 캐시 적용을 통한 성능 개선이 필요할 것으로 판단됩니다. 


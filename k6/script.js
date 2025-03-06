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

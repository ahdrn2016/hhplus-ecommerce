insert into product(name, price, status, stock)
values ('누텔라', 8000, 'SELLING', 5),
       ('딸기', 12000, 'SELLING', 3),
       ('샴푸', 15000, 'STOP', 3),
       ('텀블러', 15000, 'SELLING', 10),
       ('커피', 10000, 'SELLING', 10);

insert into coupon(name, amount, valid_start_date, valid_end_date, quantity)
values ('3000원 할인 쿠폰', 3000, '2025-01-01 00:00:00', '2025-01-31 23:59:59', 5),
       ('5000원 할인 쿠폰', 5000, '2025-01-01 00:00:00', '2025-01-31 23:59:59', 5);

insert into issued_coupon(user_id, coupon_id, amount, status)
values (1, 1, 3000, 'USED'),
       (1, 2, 5000, 'UNUSED'),
       (2, 2, 5000, 'UNUSED');

insert into point(user_id, point, version)
values (1, 10000, 0),
       (2, 60000, 0),
       (3, 0, 0),
       (4, 20000, 0);

insert into orders(user_id, status, total_amount, created_at)
values (1, 'COMPLETE', 8000, '2025-01-29 11:11:11'),
       (2, 'COMPLETE', 12000, '2025-01-29 11:11:11'),
       (3, 'COMPLETE', 15000, '2025-01-30 11:11:11'),
       (4, 'COMPLETE', 15000, '2025-01-30 11:11:11'),
       (5, 'COMPLETE', 10000, '2025-01-31 11:11:11'),
       (6, 'COMPLETE', 10000, '2025-01-31 11:11:11');

insert into order_product(order_id, product_id, price, quantity)
values (1, 1, 8000, 1),
       (2, 2, 12000, 1),
       (3, 3, 15000, 1),
       (4, 4, 15000, 1),
       (5, 5, 10000, 1),
       (6, 5, 10000, 1);
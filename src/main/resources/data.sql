insert into product(name, price, status, stock)
values ('누텔라', 8000, 'SELLING', 5),
       ('딸기', 12000, 'SELLING', 3),
       ('샴푸', 15000, 'STOP', 3);

insert into issued_coupon(user_id, coupon_id, amount, status)
values (1, 1, 3000, 'USED'),
       (1, 2, 5000, 'UNUSED'),
       (2, 2, 5000, 'UNUSED');

insert into point(user_id, point)
values (1, 10000),
       (2, 60000);

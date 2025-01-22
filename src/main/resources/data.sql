insert into product(name, price, status, stock)
values ('누텔라', 8000, 'SELLING', 5),
       ('딸기', 12000, 'SELLING', 3),
       ('샴푸', 15000, 'STOP', 3),
       ('텀블러', 15000, 'SELLING', 10),
       ('커피', 10000, 'SELLING', 10);

insert into issued_coupon(user_id, coupon_id, amount, status)
values (1, 1, 3000, 'USED'),
       (1, 2, 5000, 'UNUSED'),
       (2, 2, 5000, 'UNUSED');

insert into point(user_id, point, version)
values (1, 10000, 0),
       (2, 60000, 0),
       (3, 0, 0),
       (4, 20000, 0);
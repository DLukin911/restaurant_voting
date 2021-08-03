INSERT INTO USERS (ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES (GLOBAL_SEQ.nextval, 'user1@gmail.com', 'User1_Name', 'User1_LastName', 'password'),  --USER_ID 100000
       (GLOBAL_SEQ.nextval, 'user2@gmail.com', 'User2_Name', 'User2_LastName', 'password'),  --USER_ID 100001
       (GLOBAL_SEQ.nextval, 'admin@restaurant.ru', 'Admin_Name', 'Admin_LastName', 'admin'); --USER_ID 100002

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('ADMIN', 100002);

INSERT INTO RESTAURANT (ID, NAME)
VALUES (GLOBAL_SEQ.nextval, 'KFC'),       --RESTAURANT_ID 100003
       (GLOBAL_SEQ.nextval, 'McDonalds'); --RESTAURANT_ID 100004

INSERT INTO DISH (ID, NAME, DATE_MENU, PRICE, RESTAURANT_ID)
VALUES (GLOBAL_SEQ.nextval, 'Chicken Basket', CURRENT_DATE, 250, 100003), --DISH_ID 100005   RESTAURANT: KFC
       (GLOBAL_SEQ.nextval, 'Chicken Combo', CURRENT_DATE, 150, 100003),  --DISH_ID 100006   RESTAURANT: KFC
       (GLOBAL_SEQ.nextval, 'Sandwich', CURRENT_DATE, 200, 100003),       --DISH_ID 100007   RESTAURANT: KFC
       (GLOBAL_SEQ.nextval, 'Big Mac', CURRENT_DATE, 170, 100004),        --DISH_ID 100008   RESTAURANT: McDonalds
       (GLOBAL_SEQ.nextval, 'Cheeseburger', CURRENT_DATE, 80, 100004);    --DISH_ID 100009   RESTAURANT: McDonalds

INSERT INTO VOTE (ID, RESTAURANT_ID, USER_ID)
VALUES (1, 100004, 100000), -- RESTAURANT: McDonalds   USER_EMAIL: user1@gmail.com
       (2, 100004, 100001), -- RESTAURANT: McDonalds   USER_EMAIL: user2@gmail.com
       (3, 100003, 100002); -- RESTAURANT: KFC         USER_EMAIL: admin@restaurant.ru
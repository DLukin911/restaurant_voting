INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User1_Name', 'user1@gmail.com', 'password'),  --USER_ID 1
       ('User2_Name', 'user2@gmail.com', 'password'),  --USER_ID 2
       ('User3_Name', 'admin@restaurant.ru', 'admin'); --USER_ID 3

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('USER', 2),
       ('USER', 3),
       ('ADMIN', 3);

INSERT INTO RESTAURANT (NAME)
VALUES ('KFC'),       --RESTAURANT_ID 1
       ('McDonalds'); --RESTAURANT_ID 2

INSERT INTO DISH (NAME, DATE_MENU, PRICE, RESTAURANT_ID)
VALUES ('Chicken Basket', CURRENT_DATE, 250, 1), --DISH_ID 1   RESTAURANT: KFC
       ('Chicken Combo', CURRENT_DATE, 150, 1),  --DISH_ID 2   RESTAURANT: KFC
       ('Sandwich', CURRENT_DATE, 200, 1),       --DISH_ID 3   RESTAURANT: KFC
       ('Big Mac', CURRENT_DATE, 170, 2),        --DISH_ID 4   RESTAURANT: McDonalds
       ('Cheeseburger', CURRENT_DATE, 80, 2);    --DISH_ID 5   RESTAURANT: McDonalds

INSERT INTO VOTE (RESTAURANT_ID, USER_ID)
VALUES (2, 1), -- RESTAURANT: McDonalds   USER_EMAIL: user1@gmail.com
       (2, 2), -- RESTAURANT: McDonalds   USER_EMAIL: user2@gmail.com
       (1, 3); -- RESTAURANT: KFC         USER_EMAIL: admin@restaurant.ru
INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User1_Name', 'user1@gmail.com', '{noop}password'),  --USER_ID 1
       ('User2_Name', 'user2@gmail.com', '{noop}password'),  --USER_ID 2
       ('User3_Name', 'user3@gmail.com', '{noop}password'), --USER_ID 3
       ('Admin1_Name', 'admin@restaurant.com', '{noop}admin'); --USER_ID 4

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('USER', 2),
       ('USER', 3),
       ('ADMIN', 4),
       ('USER', 4);

INSERT INTO RESTAURANT (NAME)
VALUES ('KFC'),       --RESTAURANT_ID 1
       ('McDonalds'), --RESTAURANT_ID 2
       ('BurgerKing'); --RESTAURANT_ID 3

INSERT INTO DISH (NAME, DATE_VOTE, PRICE, RESTAURANT_ID)
VALUES ('Chicken Basket', CURRENT_DATE, 250, 1), --DISH_ID 1   RESTAURANT: KFC
       ('Chicken Combo', CURRENT_DATE, 150, 1),  --DISH_ID 2   RESTAURANT: KFC
       ('Sandwich', CURRENT_DATE, 200, 1),       --DISH_ID 3   RESTAURANT: KFC
       ('Big Mac', CURRENT_DATE, 170, 2),        --DISH_ID 4   RESTAURANT: McDonalds
       ('Cheeseburger', CURRENT_DATE, 80, 2),    --DISH_ID 5   RESTAURANT: McDonalds
       ('Burger', {ts '2021-09-01 18:00:00.00'}, 100, 3);  --DISH_ID 6   RESTAURANT: BurgerKing

INSERT INTO VOTE (RESTAURANT_ID, USER_ID)
VALUES (2, 1), -- RESTAURANT: McDonalds   USER_EMAIL: user1@gmail.com
       (2, 2), -- RESTAURANT: McDonalds   USER_EMAIL: user2@gmail.com
       (1, 3); -- RESTAURANT: KFC         USER_EMAIL: user3@gmail.com
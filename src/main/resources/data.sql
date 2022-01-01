DELETE
FROM user_roles;
DELETE
FROM DISH;
DELETE
FROM VOTE;
DELETE
FROM users;
DELETE
FROM restaurant;

INSERT INTO users (NAME, EMAIL, PASSWORD)
VALUES ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User', 'user@yandex.ru', '{noop}password'),
       ('Artem Vogulev', 'artem@yandex.ru', '{noop}vogulevpass'),
       ('Vasiliy Pupkin', 'pupkin@yandex.ru', '{noop}pupkinpass'),
       ('Petr Vasutin', 'vasutin@yandex.ru', '{noop}vasutinpass'),
       ('Gleb Zhiglov', 'zhiglov@yandex.ru', '{noop}zhiglovpass'),
       ('Vladimir Sharapov', 'sharapov@gmail.com', '{noop}sharapovpass');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('USER', 100003),
       ('USER', 100004),
       ('USER', 100005),
       ('USER', 100006),
       ('ADMIN', 100006);

INSERT INTO restaurant (name)
VALUES ('Premium restaurant'),
       ('Cafe on the beach'),
       ('Burger shop'),
       ('Sushi city');

INSERT INTO VOTE (USER_ID, RESTAURANT_ID, VOTE_DATE)
VALUES (100000, 100007, CURRENT_DATE),
       (100001, 100007, CURRENT_DATE),
       (100002, 100007, CURRENT_DATE),
       (100003, 100009, CURRENT_DATE),
       (100004, 100008, CURRENT_DATE),
       (100005, 100008, CURRENT_DATE);

INSERT INTO DISH (NAME, PRICE, RESTAURANT_ID)
VALUES ('Spaghetti bolognese', 50.5, 100007),
       ('Chicken salad', 23.1, 100007),
       ('Lasagne', 37.9, 100007),
       ('Pasta', 47.2, 100007),
       ('Risotto', 32.8, 100007),
       ('Omelette', 24, 100008),
       ('Chutney', 21.3, 100008),
       ('Turkey soup', 30.1, 100008),
       ('Butter chicken', 33.3, 100008),
       ('chocolate molten cakes', 19.2, 100008),
       ('Burger with chicken', 9.1, 100009),
       ('Burger with beef', 13.2, 100009),
       ('Pepperoni pizza', 16, 100009),
       ('Vegetarian hamburger', 4.9, 100009),
       ('French fries', 2.7, 100009),
       ('Sushi unagi', 10, 100010),
       ('Sushi maki', 11, 100010),
       ('Sushi nigiri', 8, 100010),
       ('Sushi tuna maguro', 7.2, 100010),
       ('Sushi vegetarian', 2.9, 100010);
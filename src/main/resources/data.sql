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
       (100000, 100008, CURRENT_DATE - 1),
       (100001, 100009, CURRENT_DATE - 1),
       (100002, 100007, CURRENT_DATE),
       (100003, 100009, CURRENT_DATE),
       (100004, 100008, CURRENT_DATE),
       (100005, 100008, CURRENT_DATE);

INSERT INTO DISH (NAME, PRICE, RESTAURANT_ID, add_date)
VALUES ('Spaghetti bolognese', 50.5, 100007, CURRENT_DATE),
       ('Chicken salad', 23.1, 100007, CURRENT_DATE),
       ('Lasagne', 37.9, 100007, CURRENT_DATE),
       ('Pasta', 47.2, 100007, CURRENT_DATE),
       ('Risotto', 32.8, 100007, CURRENT_DATE),
       ('Omelette', 24, 100008, CURRENT_DATE),
       ('Chutney', 21.3, 100008, CURRENT_DATE),
       ('Turkey soup', 30.1, 100008, CURRENT_DATE),
       ('Butter chicken', 33.3, 100008, CURRENT_DATE),
       ('chocolate molten cakes', 19.2, 100008, CURRENT_DATE),
       ('Burger with chicken', 9.1, 100009, CURRENT_DATE),
       ('Burger with beef', 13.2, 100009, CURRENT_DATE),
       ('Pepperoni pizza', 16, 100009, CURRENT_DATE),
       ('Vegetarian hamburger', 4.9, 100009, CURRENT_DATE),
       ('French fries', 2.7, 100009, CURRENT_DATE),
       ('Sushi unagi', 10, 100010, CURRENT_DATE),
       ('Sushi maki', 11, 100010, CURRENT_DATE),
       ('Sushi nigiri', 8, 100010, CURRENT_DATE),
       ('Sushi tuna maguro', 7.2, 100010, CURRENT_DATE),
       ('Sushi vegetarian', 2.9, 100010, CURRENT_DATE),
       ('Yesterday Spaghetti bolognese', 66.6, 100007, CURRENT_DATE - 1);
package ru.vogulev.voting.web.dish;

import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.vogulev.voting.model.BaseEntity.START_SEQ;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.premium_restaurant;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> Dish_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final Dish dish1 = new Dish(START_SEQ + 17, "Spaghetti bolognese", 50.5, premium_restaurant, LocalDate.now());
    public static final Dish dish2 = new Dish(START_SEQ + 18, "Chicken salad", 23.1, premium_restaurant, LocalDate.now());
    public static final Dish dish3 = new Dish(START_SEQ + 19, "Lasagne", 37.9, premium_restaurant, LocalDate.now());
    public static final Dish dish4 = new Dish(START_SEQ + 20, "Pasta", 47.2, premium_restaurant, LocalDate.now());
    public static final Dish dish5 = new Dish(START_SEQ + 21, "Risotto", 32.8, premium_restaurant, LocalDate.now());

    public static final List<Dish> premium_restaurant_menu = List.of(dish1, dish2, dish3, dish4, dish5);
}

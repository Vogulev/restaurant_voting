package ru.vogulev.voting.web.dish;

import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.vogulev.voting.model.BaseEntity.START_SEQ;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.premium_restaurant;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> Dish_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "addDate", "restaurant", "votes");

    public static final Dish premium_restaurant_dish1 = new Dish(START_SEQ + 18, "Spaghetti bolognese", 50.5, premium_restaurant, LocalDate.now());
    public static final Dish premium_restaurant_dish2 = new Dish(START_SEQ + 19, "Chicken salad", 23.1, premium_restaurant, LocalDate.now());
    public static final Dish premium_restaurant_dish3 = new Dish(START_SEQ + 20, "Lasagne", 37.9, premium_restaurant, LocalDate.now());
    public static final Dish premium_restaurant_dish4 = new Dish(START_SEQ + 21, "Pasta", 47.2, premium_restaurant, LocalDate.now());
    public static final Dish premium_restaurant_dish5 = new Dish(START_SEQ + 22, "Risotto", 32.8, premium_restaurant, LocalDate.now());
    public static final Dish premium_restaurant_yesterdayDish = new Dish(START_SEQ + 38, "Yesterday Spaghetti bolognese", 66.6, premium_restaurant, LocalDate.now().minusDays(1));

    public static final Dish premium_restaurant_updatedDish = new Dish(premium_restaurant_dish1.id(), "newPremiumRestaurantDish", 99.9, premium_restaurant, LocalDate.now());

    public static final List<Dish> premium_restaurant_today_menu = List.of(premium_restaurant_dish1, premium_restaurant_dish2, premium_restaurant_dish3, premium_restaurant_dish4, premium_restaurant_dish5);
    public static final List<Dish> premium_restaurant_history_menu = List.of(premium_restaurant_yesterdayDish, premium_restaurant_dish1, premium_restaurant_dish2, premium_restaurant_dish3, premium_restaurant_dish4, premium_restaurant_dish5);
}

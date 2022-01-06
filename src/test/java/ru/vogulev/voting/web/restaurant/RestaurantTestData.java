package ru.vogulev.voting.web.restaurant;

import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.web.MatcherFactory;

import static ru.vogulev.voting.model.BaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int PREMIUM_RESTAURANT_ID = START_SEQ + 7;

    public static final Restaurant premium_restaurant = new Restaurant(PREMIUM_RESTAURANT_ID, "Premium restaurant");
}

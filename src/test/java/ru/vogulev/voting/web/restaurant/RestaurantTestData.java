package ru.vogulev.voting.web.restaurant;

import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.to.RestaurantTo;
import ru.vogulev.voting.web.MatcherFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.vogulev.voting.model.BaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "votes", "menu");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int PREMIUM_RESTAURANT_ID = START_SEQ + 7;
    public static final int CAFE_ON_THE_BEACH_ID = START_SEQ + 8;
    public static final int BURGER_SHOP_ID = START_SEQ + 9;
    public static final int SUSHI_CITY_ID = START_SEQ + 10;

    public static final Restaurant premium_restaurant = new Restaurant(PREMIUM_RESTAURANT_ID, "Premium restaurant");
    public static final Restaurant cafe_on_the_beach = new Restaurant(CAFE_ON_THE_BEACH_ID, "Cafe on the beach");
    public static final Restaurant burger_shop = new Restaurant(BURGER_SHOP_ID, "Burger shop");
    public static final Restaurant sushi_city = new Restaurant(SUSHI_CITY_ID, "Sushi city");

    public static final Restaurant premium_restaurant_updated = new Restaurant(PREMIUM_RESTAURANT_ID, "Updated Premium restaurant");

    public static final List<Restaurant> restaurants =
            Stream.of(premium_restaurant, cafe_on_the_beach, burger_shop, sushi_city)
            .sorted(Comparator.comparing(Restaurant::getName))
            .collect(Collectors.toList());
    public static final List<Restaurant> restaurants_after_delete =
            Stream.of(cafe_on_the_beach, burger_shop, sushi_city)
                    .sorted(Comparator.comparing(Restaurant::getName))
                    .collect(Collectors.toList());
    public static final List<RestaurantTo> restaurants_with_votes_on_today =
            Stream.of(asTo(cafe_on_the_beach, 2), asTo(burger_shop, 1), asTo(premium_restaurant, 2))
                    .sorted(Comparator.comparing(RestaurantTo::getVoteCount).reversed().thenComparing(RestaurantTo::getId))
                    .collect(Collectors.toList());

    private static RestaurantTo asTo(Restaurant restaurant, long voteCount) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), voteCount);
    }

}

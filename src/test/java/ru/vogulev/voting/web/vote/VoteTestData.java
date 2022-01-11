package ru.vogulev.voting.web.vote;

import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.to.VoteTo;
import ru.vogulev.voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.vogulev.voting.model.BaseEntity.START_SEQ;
import static ru.vogulev.voting.web.restaurant.RestaurantTestData.*;
import static ru.vogulev.voting.web.user.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int ADMIN_TODAY_VOTE_ID = START_SEQ + 11;
    public static final int ADMIN_YESTERDAY_VOTE_ID = START_SEQ + 12;
    public static final int USER_YESTERDAY_VOTE_ID = START_SEQ + 13;
    public static final int ARTEM_VOGULEV_VOTE_ID = START_SEQ + 14;
    public static final int VASILIY_PUPKIN_VOTE_ID = START_SEQ + 15;
    public static final int PETR_VASUTIN_VOTE_ID = START_SEQ + 16;
    public static final int GLEB_ZHIGLOV_VOTE_ID = START_SEQ + 17;

    public static final Vote admin_today_vote = new Vote(ADMIN_TODAY_VOTE_ID, admin, premium_restaurant, LocalDate.now());
    public static final Vote admin_yesterday_vote = new Vote(ADMIN_YESTERDAY_VOTE_ID, admin, cafe_on_the_beach, LocalDate.now().minusDays(1));
    public static final Vote user_yesterday_vote = new Vote(USER_YESTERDAY_VOTE_ID, user, burger_shop, LocalDate.now().minusDays(1));
    public static final Vote Artem_Vogulev_today_vote = new Vote(ARTEM_VOGULEV_VOTE_ID, ArtemVogulev, premium_restaurant, LocalDate.now());
    public static final Vote Vasiliy_Pupkin_today_vote = new Vote(VASILIY_PUPKIN_VOTE_ID, VasiliyPupkin, burger_shop, LocalDate.now());
    public static final Vote Petr_Vasutin_today_vote = new Vote(PETR_VASUTIN_VOTE_ID, PetrVasutin, cafe_on_the_beach, LocalDate.now());
    public static final Vote Gleb_Zhiglov_today_vote = new Vote(GLEB_ZHIGLOV_VOTE_ID, GlebZhiglov, cafe_on_the_beach, LocalDate.now());

    public static final Vote admin_today_vote_updated = new Vote(ADMIN_TODAY_VOTE_ID, admin, burger_shop, LocalDate.now());

    public static final List<VoteTo> burger_shop_votes = List.of(asTo(user_yesterday_vote), asTo(Vasiliy_Pupkin_today_vote));
    public static final List<VoteTo> today_votes = List.of(asTo(admin_today_vote),
            asTo(Artem_Vogulev_today_vote), asTo(Vasiliy_Pupkin_today_vote),
            asTo(Petr_Vasutin_today_vote), asTo(Gleb_Zhiglov_today_vote));
    public static final List<VoteTo> yesterday_votes = List.of(asTo(admin_yesterday_vote), asTo(user_yesterday_vote));
    public static final List<Vote> admin_votes = List.of(admin_today_vote, admin_yesterday_vote);

    private static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVoteDate(), vote.getRestaurant().getId(),
                vote.getRestaurant().getName(), vote.getUser().getId(), vote.getUser().getName());
    }
}

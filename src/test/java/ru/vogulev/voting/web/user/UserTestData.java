package ru.vogulev.voting.web.user;

import ru.vogulev.voting.model.Role;
import ru.vogulev.voting.model.User;
import ru.vogulev.voting.util.JsonUtil;
import ru.vogulev.voting.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

import static ru.vogulev.voting.model.BaseEntity.START_SEQ;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int ADMIN_ID = START_SEQ;
    public static final int USER_ID = START_SEQ + 1;
    public static final int VASILIY_PUPKIN_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User ArtemVogulev = new User(ADMIN_ID + 2, "Artem Vogulev", "artem@yandex.ru", "vogulevpass", Role.USER);
    public static final User VasiliyPupkin = new User(VASILIY_PUPKIN_ID, "Vasiliy Pupkin", "pupkin@yandex.ru", "pupkinpass", Role.USER);
    public static final User PetrVasutin = new User(ADMIN_ID + 4, "Petr Vasutin", "vasutin@yandex.ru", "vasutinpass", Role.USER);
    public static final User GlebZhiglov = new User(ADMIN_ID + 5, "Gleb Zhiglov", "zhiglov@yandex.ru", "zhiglovpass", Role.USER);
    public static final User VladimirSharapov = new User(ADMIN_ID + 6, "Vladimir Sharapov", "sharapov@gmail.com", "sharapovpass", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}

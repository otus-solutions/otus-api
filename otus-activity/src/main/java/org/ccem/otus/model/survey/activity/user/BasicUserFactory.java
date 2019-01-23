package org.ccem.otus.model.survey.activity.user;

import br.org.otus.model.User;

public class BasicUserFactory {

    public ActivityBasicUser createRevisionUser(User user) {
        ActivityBasicUser basicUser = new ActivityBasicUser();
        basicUser.setNameAndSurname(user.getName(),user.getSurname());
        basicUser.setEmail(user.getEmail());

        return basicUser;
    }
}

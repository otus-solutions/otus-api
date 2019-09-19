package org.ccem.otus.model.survey.activity.user;

import br.org.otus.model.User;

public class ActivityBasicUserFactory {

    public ActivityBasicUser createRevisionUser(User user) {
        ActivityBasicUser basicUser = new ActivityBasicUser();
        basicUser.setName(user.getName());
        basicUser.setSurname(user.getSurname());
        basicUser.setEmail(user.getEmail());

        return basicUser;
    }
}

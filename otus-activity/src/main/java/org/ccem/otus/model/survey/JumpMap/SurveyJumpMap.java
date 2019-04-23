package org.ccem.otus.model.survey.JumpMap;

import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.navigation.route.RouteCondition;

import java.util.List;

public class SurveyJumpMap {
    private List<VariantQuestion> variantQuestions;

    public List<VariantQuestion> getVariantQuestions() {
        return this.variantQuestions;
    }

    public class VariantQuestion {
        private String questionId;
        private List<PossibleDestination> possibleDestinations;

        public String getQuestionId() {
            return questionId;
        }

        public List<PossibleDestination> getPossibleDestinations() {
            return possibleDestinations;
        }
    }

    public class PossibleDestination {
        private List<RouteCondition> when;
        private List<SkippedQuestion> shouldBeSkipped;

        public List<RouteCondition> getWhen() {
            return when;
        }

        public List<SkippedQuestion> getShouldBeSkiped() {
            return shouldBeSkipped;
        }
    }

    private class SkippedQuestion {
        private String skippedQuestionId;
        private Integer skippedQuestionIndex;
    }

    public static String serialize(SurveyJumpMap surveyGroup) {
        return getGsonBuilder().create().toJson(surveyGroup);
    }

    public static SurveyJumpMap deserialize(String surveyGroupJson) {
        return SurveyJumpMap.getGsonBuilder().create().fromJson(surveyGroupJson, SurveyJumpMap.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        return builder;
    }


}

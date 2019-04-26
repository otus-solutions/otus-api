package br.org.otus.survey;

import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class SurveyJumpMapQueryBuilderTest {

    @Test
    public void buildQuery_should_return_JumpMapCreation_query(){
        SurveyJumpMapQueryBuilder surveyJumpMapQueryBuilder = new SurveyJumpMapQueryBuilder();
        String jumpMapCreationQuery = "[{\"$match\":{\"surveyTemplate.identity.acronym\":\"TST\",\"version\":1}},{\"$project\":{\"navigationList\":\"$surveyTemplate.navigationList\",\"navigationListCopy\":\"$surveyTemplate.navigationList\"}},{\"$unwind\":\"$navigationListCopy\"},{\"$unwind\":\"$navigationListCopy.inNavigations\"},{\"$match\":{\"navigationListCopy.inNavigations\":{\"$not\":{}}}},{\"$project\":{\"navigationList\":\"$navigationList\",\"questionId\":\"$navigationListCopy.origin\",\"inNavigation\":[\"$navigationListCopy.inNavigations.origin\",false]}},{\"$group\":{\"_id\":{\"questionId\":\"$questionId\",\"navigationList\":\"$navigationList\"},\"possibleOrigins\":{\"$push\":\"$inNavigation\"}}},{\"$group\":{\"_id\":{\"navigationList\":\"$_id.navigationList\"},\"possibleOriginsList\":{\"$push\":{\"QuestionId\":\"$_id.questionId\",\"possibleOrigins\":\"$possibleOrigins\"}}}},{\"$project\":{\"_id\":0.0,\"navigationList\":\"$_id.navigationList\",\"possibleOriginsList\":1.0}},{\"$unwind\":\"$navigationList\"},{\"$unwind\":\"$navigationList.routes\"},{\"$group\":{\"_id\":{\"questionID\":\"$navigationList.origin\",\"possibleOriginsList\":\"$possibleOriginsList\"},\"possibleDestinations\":{\"$push\":{\"default\":\"$navigationList.routes.isDefault\",\"routeConditions\":\"$navigationList.routes.conditions\",\"destination\":\"$navigationList.routes.destination\"}}}},{\"$project\":{\"_id\":0.0,\"questionId\":\"$_id.questionID\",\"possibleOrigins\":{\"$arrayElemAt\":[{\"$filter\":{\"input\":\"$_id.possibleOriginsList\",\"as\":\"possibleOrigin\",\"cond\":{\"$eq\":[\"$$possibleOrigin.QuestionId\",\"$_id.questionID\"]}}},0.0]},\"defaultDestination\":{\"$arrayElemAt\":[{\"$filter\":{\"input\":\"$possibleDestinations\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item.default\",true]}}},0.0]},\"alternativeDestination\":{\"$filter\":{\"input\":\"$possibleDestinations\",\"as\":\"item\",\"cond\":{\"$eq\":[\"$$item.default\",false]}}}}},{\"$project\":{\"_id\":0.0,\"mappedQuestion\":[\"$questionId\",{\"possibleOrigins\":\"$possibleOrigins.possibleOrigins\",\"defaultDestination\":\"$defaultDestination.destination\",\"alternativeDestinations\":\"$alternativeDestination\"}]}},{\"$group\":{\"_id\":{},\"jumpMap\":{\"$push\":\"$mappedQuestion\"}}}]";
        assertEquals(jumpMapCreationQuery,new GsonBuilder().create().toJson(surveyJumpMapQueryBuilder.buildQuery("TST",1)));
    }
}

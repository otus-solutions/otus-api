package br.org.otus.survey;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class SurveyJumpMapQueryBuilder {

    private  List<Bson> pipeline;

    private  Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }

    public  List<Bson> buildQuery(String acronym, Integer version){
        pipeline = new ArrayList<>();
        addMatchStage(acronym,version)
        .addProjectEssentialFieldsStages()
        .addUnwindOnNavigationListCopyStage()
        .addUnwindOnInNavigationsStage()
        .addNotNullFilterOnInNavigationsStage()
        .addProjectInNavigationAsHashMapElementStage()
        .addGroupPossibleOriginsByQuestionIdStage()
        .addGroupAllQuestionsPossibleOriginsInOneArrayStage()
        .addProjectOfFormattedDocumentStage()
        .addUnwindOnNavigationListStage()
        .addUnwindOnRoutesStage()
        .addGroupOfPossibleDestinationsByQuestionIdStage()
        .addProjectThatBuildTheQuestionFieldsStage()
        .addProjectThatBuildTheQuestionHashMapElement()
        .addGroupThatCreateTheJumpMap();

        return pipeline;
    }

    /**
     * Filter by acronym and version of the inserted survey
     * @param acronym
     * @param version
     */
    private SurveyJumpMapQueryBuilder addMatchStage(String acronym, Integer version) {
        pipeline.add(new Document("$match",new Document("surveyTemplate.identity.acronym",acronym).append("version",version)));
        return this;
    }

    /**
     * Project only the field required for the jumpMap Build and make a copy of this field to make possibleOrigins hashMaps Array
     */
    private SurveyJumpMapQueryBuilder addProjectEssentialFieldsStages() {
        pipeline.add(parseQuery("{\n" +
                "            $project: {\n" +
                "                navigationList:\"$surveyTemplate.navigationList\", \n" +
                "                navigationListCopy:\"$surveyTemplate.navigationList\" \n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Break the navigations array into documents
     */
    private SurveyJumpMapQueryBuilder addUnwindOnNavigationListCopyStage() {
        pipeline.add(parseQuery(" {\n" +
                "            $unwind: \"$navigationListCopy\" \n" +
                "        }"));
        return this;
    }

    /**
     * break the array inNavigations into documents
     */
    private SurveyJumpMapQueryBuilder addUnwindOnInNavigationsStage() {
        pipeline.add(parseQuery("{\n" +
                "            $unwind: \"$navigationListCopy.inNavigations\" \n" +
                "        }"));
        return this;
    }

    /**
     * Exclude the null values on inNavigations
     */
    private SurveyJumpMapQueryBuilder addNotNullFilterOnInNavigationsStage() {
        pipeline.add(parseQuery("{\n" +
                "            $match: {\n" +
                "                \"navigationListCopy.inNavigations\":{\n" +
                "                    $not: {\n" +
                "                        $eq: null\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Transform the inNavigation object into a hashMap element where the origin questionId is the key
     */
    private SurveyJumpMapQueryBuilder addProjectInNavigationAsHashMapElementStage() {
        pipeline.add(parseQuery("{\n" +
                "            $project: {\n" +
                "                navigationList:\"$navigationList\",\n" +
                "                questionId:\"$navigationListCopy.origin\",\n" +
                "                inNavigation:[\n" +
                "                    \"$navigationListCopy.inNavigations.origin\",\n" +
                "                    false\n" +
                "                ]\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Build the possibleOrigins array by questionId
     */
    private SurveyJumpMapQueryBuilder addGroupPossibleOriginsByQuestionIdStage() {
        pipeline.add(parseQuery("{\n" +
                "            $group: { \n" +
                "                _id: {\n" +
                "                    questionId:\"$questionId\",\n" +
                "                    navigationList:\"$navigationList\"\n" +
                "                }, \n" +
                "                possibleOrigins:{\n" +
                "                    $push: \"$inNavigation\"\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Creates all questions possible origins array
     */
    private SurveyJumpMapQueryBuilder addGroupAllQuestionsPossibleOriginsInOneArrayStage() {
        pipeline.add(parseQuery("{\n" +
                "            $group: { \n" +
                "                _id: {\n" +
                "                    navigationList:\"$_id.navigationList\"\n" +
                "                }, \n" +
                "                possibleOriginsList:{\n" +
                "                    $push: {\n" +
                "                        QuestionId:\"$_id.questionId\",\n" +
                "                        possibleOrigins:\"$possibleOrigins\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Restructure the document for best manipulation of the data
     */
    private SurveyJumpMapQueryBuilder addProjectOfFormattedDocumentStage() {
        pipeline.add(parseQuery("{\n" +
                "            $project: {\n" +
                "                _id:0,\n" +
                "                navigationList:\"$_id.navigationList\",\n" +
                "                possibleOriginsList:1\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Break the navigations array into single navigation documents
     */
    private SurveyJumpMapQueryBuilder addUnwindOnNavigationListStage() {
        pipeline.add(parseQuery("{\n" +
                "            $unwind: \"$navigationList\"\n" +
                "        }"));
        return this;
    }

    /**
     * Break the routes array in every navigation into documents
     */
    private SurveyJumpMapQueryBuilder addUnwindOnRoutesStage() {
        pipeline.add(parseQuery("{\n" +
                "            $unwind: \"$navigationList.routes\"\n" +
                "        }"));
        return this;
    }

    /**
     * Creates an array with all the question possible destinations
     */
    private SurveyJumpMapQueryBuilder addGroupOfPossibleDestinationsByQuestionIdStage() {
        pipeline.add(parseQuery("{\n" +
                "            $group: { \n" +
                "                _id: {\n" +
                "                    questionID:\"$navigationList.origin\",\n" +
                "                    possibleOriginsList:\"$possibleOriginsList\"\n" +
                "                },\n" +
                "                possibleDestinations:{ \n" +
                "                    $push:{\n" +
                "                        default:\"$navigationList.routes.isDefault\", \n" +
                "                        routeConditions:\"$navigationList.routes.conditions\", \n" +
                "                        destination:\"$navigationList.routes.destination\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     *  Creates possible origin array by filtering possibleOriginsList array
     *  Creates the alternativeDestination array by filtering the possibleDestinations array by looking for non default routes
     *  Creates the defaultDestination field by filtering the possibleDestinations array by looking for the default route
     */
    private SurveyJumpMapQueryBuilder addProjectThatBuildTheQuestionFieldsStage() {
        pipeline.add(parseQuery("{\n" +
                "            $project: {\n" +
                "                _id:0,\n" +
                "                questionId:\"$_id.questionID\",\n" +
                "                possibleOrigins:{\n" +
                "                        $arrayElemAt: [\n" +
                "                            {\n" +
                "                                $filter: {\n" +
                "                                  input: \"$_id.possibleOriginsList\",\n" +
                "                                  as: \"possibleOrigin\",\n" +
                "                                  cond: { \n" +
                "                                      $eq: [\"$$possibleOrigin.QuestionId\",\"$_id.questionID\"]\n" +
                "                                  }\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ,0]\n" +
                "                },\n" +
                "                defaultDestination:{\n" +
                "                    $arrayElemAt: [\n" +
                "                        {\n" +
                "                            $filter: {\n" +
                "                              input: \"$possibleDestinations\",\n" +
                "                              as: \"item\",\n" +
                "                              cond: {\n" +
                "                                  $eq: [\"$$item.default\",true]\n" +
                "                              }\n" +
                "                            }\n" +
                "                        }\n" +
                "                        ,0]\n" +
                "                },\n" +
                "                alternativeDestination:{\n" +
                "                    $filter: {\n" +
                "                      input: \"$possibleDestinations\",\n" +
                "                      as: \"item\",\n" +
                "                      cond: {\n" +
                "                          $eq: [\"$$item.default\",false]\n" +
                "                      }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Transform the questions into hashMap elements where the questionId id the key
     */
    private SurveyJumpMapQueryBuilder addProjectThatBuildTheQuestionHashMapElement() {
        pipeline.add(parseQuery("{\n" +
                "            $project: { \n" +
                "                _id: 0,\n" +
                "                mappedQuestion:[\n" +
                "                    \"$questionId\",\n" +
                "                    {\n" +
                "                        possibleOrigins:\"$possibleOrigins.possibleOrigins\",\n" +
                "                        defaultDestination:\"$defaultDestination.destination\",\n" +
                "                        alternativeDestinations:\"$alternativeDestination\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        }"));
        return this;
    }

    /**
     * Creates the jumpMap(HashMap)
     */
    private SurveyJumpMapQueryBuilder addGroupThatCreateTheJumpMap() {
        pipeline.add(parseQuery("{\n" +
                "            $group: { \n" +
                "                _id: {},\n" +
                "                jumpMap:{\n" +
                "                    $push: \"$mappedQuestion\"\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return this;
    }
}

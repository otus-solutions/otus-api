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
        .addNotNullFilterOnInNavigationsStage();


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
        pipeline.add(parseQuery("{\n" +
                "            $project: {\n" +
                "                _id:0,\n" +
                "                navigationList:\"$_id.navigationList\",\n" +
                "                possibleOriginsList:1\n" +
                "            }\n" +
                "        }"));
        pipeline.add(parseQuery("{\n" +
                "            $unwind: \"$navigationList\"\n" +
                "        }"));
        pipeline.add(parseQuery("{\n" +
                "            $unwind: \"$navigationList.routes\"\n" +
                "        }"));
        pipeline.add(parseQuery("{\n" +
                "            $group: { \n" +
                "                _id: {\n" +
                "                    questionID:\"$navigationList.origin\",\n" +
                "                    possibleOriginsList:\"$possibleOriginsList\"\n" +
                "                },\n" +
                "                possibleDestinations:{ \n" +
                "                    $push:{\n" +
                "                        default:\"$navigationList.routes.isDefault\", \n" +
                "                        when:\"$navigationList.routes.conditions\", \n" +
                "                        destnation:\"$navigationList.routes.destination\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
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
        pipeline.add(parseQuery("{\n" +
                "            $project: { \n" +
                "                _id: 0,\n" +
                "                mappedQuestion:[\n" +
                "                    \"$questionId\",\n" +
                "                    {\n" +
                "                        possibleOrigins:\"$possibleOrigins.possibleOrigins\",\n" +
                "                        defaultDestination:\"$defaultDestination.destnation\",\n" +
                "                        alternativeDestinations:\"$alternativeDestination\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        }"));
        pipeline.add(parseQuery("{\n" +
                "            $group: { \n" +
                "                _id: {},\n" +
                "                jumpMap:{\n" +
                "                    $push: \"$mappedQuestion\"\n" +
                "                }\n" +
                "            }\n" +
                "        }"));
        return pipeline;
    }

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

    private SurveyJumpMapQueryBuilder addUnwindOnInNavigationsStage() {
        pipeline.add(parseQuery("{\n" +
                "            $unwind: \"$navigationListCopy.inNavigations\" \n" +
                "        }"));
        return this;
    }

    private SurveyJumpMapQueryBuilder addUnwindOnNavigationListCopyStage() {
        pipeline.add(parseQuery(" {\n" +
                "            $unwind: \"$navigationListCopy\" \n" +
                "        }"));
        return this;
    }

    private SurveyJumpMapQueryBuilder addProjectEssentialFieldsStages() {
        pipeline.add(parseQuery("{\n" +
                "            $project: {\n" +
                "                navigationList:\"$surveyTemplate.navigationList\", \n" +
                "                navigationListCopy:\"$surveyTemplate.navigationList\" \n" +
                "            }\n" +
                "        }"));
        return this;
    }

    private SurveyJumpMapQueryBuilder addMatchStage(String acronym, Integer version) {
        pipeline.add(new Document("$match",new Document("surveyTemplate.identity.acronym",acronym).append("version",version)));
        return this;
    }
}

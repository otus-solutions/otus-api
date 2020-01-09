package br.org.otus.extraction.builder;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import com.google.gson.GsonBuilder;
import com.mongodb.client.AggregateIterable;

public class ActivityProgressExtractionQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ActivityProgressExtractionQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getActivityStatusQueryToExtraction(String center, ArrayList<Long> rns, AggregateIterable<Document> participantsByFieldCenter, AggregateIterable<Document> acronyms, AggregateIterable<Document> inapplicabilities) {
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $match: {\n" + 
        "      \"participantData.recruitmentNumber\": { $in: " + rns + "},\n" + 
        "      \"isDiscarded\": false\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$participantData.recruitmentNumber\",\n" + 
        "      activities: {\n" + 
        "        $push: {\n" + 
        "          status: {\n" + 
        "            acronym: \"$surveyForm.acronym\",\n" + 
        "            lastStatus_Date: {\n" + 
        "              $arrayElemAt: [\n" + 
        "                {\n" + 
        "                  $slice: [\n" + 
        "                    \"$statusHistory.date\",\n" + 
        "                    -1\n" + 
        "                  ]\n" + 
        "                },\n" + 
        "                0\n" + 
        "              ]\n" + 
        "            },\n" + 
        "            lastStatus_Name: {\n" + 
        "              $arrayElemAt: [\n" + 
        "                {\n" + 
        "                  $slice: [\n" + 
        "                    \"$statusHistory.name\",\n" + 
        "                    -1\n" + 
        "                  ]\n" + 
        "                },\n" + 
        "                0\n" + 
        "              ]\n" + 
        "            }\n" + 
        "          }\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"\",\n" + 
        "      arr: {\n" + 
        "        $push: \"$$ROOT\"\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $addFields: {\n" + 
        "      rns: " + new GsonBuilder().create().toJson(participantsByFieldCenter.first().get("allRns")) + "\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $unwind: \"$rns\"\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $addFields: {\n" + 
        "      rnActivities: {\n" + 
        "        $arrayElemAt: [\n" + 
        "          {\n" + 
        "            $filter: {\n" + 
        "              input: \"$arr\",\n" + 
        "              as: \"activitiesfiltered\",\n" + 
        "              cond: {\n" + 
        "                $eq: [\n" + 
        "                  \"$$activitiesfiltered._id\",\n" + 
        "                  \"$rns.participantData.recruitmentNumber\"\n" + 
        "                ]\n" + 
        "              }\n" + 
        "            }\n" + 
        "          },\n" + 
        "          0\n" + 
        "        ]\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $project: {\n" + 
        "      participantData: \"$rns.participantData\",\n" + 
        "      activity: \"$rnActivities.activities\"\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $unwind: {\n" + 
        "      path: \"$activity\",\n" + 
        "      preserveNullAndEmptyArrays: true\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $sort: {\n" + 
        "      \"activity.status.lastStatus_Date\": 1\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$participantData.recruitmentNumber\",\n" + 
        "      activities: {\n" + 
        "        $push: {\n" + 
        "          status: \"$activity.status.lastStatus_Name\",\n" + 
        "          rn: \"$participantData.recruitmentNumber\",\n" + 
        "          acronym: \"$activity.status.acronym\",\n" + 
        "          statusDate: \"$activity.status.lastStatus_Date\",\n" + 
        "          observation: \"\"\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $addFields: {\n" + 
        "      activityInapplicabilities: {\n" + 
        "        $arrayElemAt: [\n" + 
        "          {\n" + 
        "            $filter: {\n" + 
        "              input: " + new GsonBuilder().create().toJson(inapplicabilities.first().get("inapplicabilities")) + ",\n" + 
        "              as: \"activityInapplicalibity\",\n" + 
        "              cond: {\n" + 
        "                $and: [\n" + 
        "                  {\n" + 
        "                    $eq: [\n" + 
        "                      \"$$activityInapplicalibity.rn\",\n" + 
        "                      \"$_id\"\n" + 
        "                    ]\n" + 
        "                  }\n" + 
        "                ]\n" + 
        "              }\n" + 
        "            }\n" + 
        "          },\n" + 
        "          0\n" + 
        "        ]\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $addFields: {\n" + 
        "      activityInapplicabilities: {\n" + 
        "        $cond: [\n" + 
        "          {\n" + 
        "            $ifNull: [\n" + 
        "              \"$activityInapplicabilities\",\n" + 
        "              false\n" + 
        "            ]\n" + 
        "          },\n" + 
        "          \"$activityInapplicabilities\",\n" + 
        "          []\n" + 
        "        ]\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $addFields: {\n" + 
        "      headers: "+ new GsonBuilder().create().toJson(acronyms.first().get("acronyms")) +"\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $unwind: \"$headers\"\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $addFields: {\n" + 
        "      activityFound: {\n" + 
        "        $filter: {\n" + 
        "          input: \"$activities\",\n" + 
        "          as: \"item\",\n" + 
        "          cond: {\n" + 
        "            $eq: [\n" + 
        "              \"$$item.acronym\",\n" + 
        "              \"$headers\"\n" + 
        "            ]\n" + 
        "          }\n" + 
        "        }\n" + 
        "      },\n" + 
        "      inapplicabilityFound: {\n" + 
        "        $arrayElemAt: [\n" + 
        "          {\n" + 
        "            $filter: {\n" + 
        "              input: \"$activityInapplicabilities.AI\",\n" + 
        "              as: \"item\",\n" + 
        "              cond: {\n" + 
        "                $eq: [\n" + 
        "                  \"$$item.acronym\",\n" + 
        "                  \"$headers\"\n" + 
        "                ]\n" + 
        "              }\n" + 
        "            }\n" + 
        "          },\n" + 
        "          0\n" + 
        "        ]\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$_id\",\n" + 
        "      filteredActivities: {\n" + 
        "        $push: {\n" + 
        "          $cond: [\n" + 
        "            \"$inapplicabilityFound\",\n" + 
        "            {\n" + 
        "              $cond: [\n" + 
        "                {\n" + 
        "                  $gt: [\n" + 
        "                    {\n" + 
        "                      \"$size\": \"$activityFound\"\n" + 
        "                    },\n" + 
        "                    0\n" + 
        "                  ]\n" + 
        "                },\n" + 
        "                {\n" + 
        "                  status: \"AMBIGUITY\",\n" + 
        "                  rn: \"$_id\",\n" + 
        "                  acronym: \"$headers\",\n" + 
        "                  statusDate: \"\",\n" + 
        "                  observation: \"$inapplicabilityFound.observation\"\n" + 
        "                },\n" + 
        "                {\n" + 
        "                  status: \"DOES NOT APPLY\",\n" + 
        "                  rn: \"$_id\",\n" + 
        "                  acronym: \"$headers\",\n" + 
        "                  statusDate: \"\",\n" + 
        "                  observation: \"$inapplicabilityFound.observation\"\n" + 
        "                }\n" + 
        "              ]\n" + 
        "            },\n" + 
        "            {\n" + 
        "              $cond: [\n" + 
        "                {\n" + 
        "                  $gt: [\n" + 
        "                    {\n" + 
        "                      \"$size\": \"$activityFound\"\n" + 
        "                    },\n" + 
        "                    0\n" + 
        "                  ]\n" + 
        "                },\n" + 
        "                {\n" + 
        "                  $arrayElemAt: [\n" + 
        "                    \"$activityFound\",\n" + 
        "                    -1\n" + 
        "                  ]\n" + 
        "                },\n" + 
        "                {\n" + 
        "                  status: \"NOT ADDED\",\n" + 
        "                  rn: \"$_id\",\n" + 
        "                  acronym: \"$headers\",\n" + 
        "                  statusDate: \"\",\n" + 
        "                  observation: \"\"\n" + 
        "                }\n" + 
        "              ]\n" + 
        "            }\n" + 
        "          ]\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $unwind: \"$filteredActivities\"\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{ $replaceRoot: { newRoot: \"$filteredActivities\" } }"));
    
    return pipeline;
  }
  
  public List<Bson> getParticipants(String center) {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $match: {\n" + 
        "      fieldCenter.acronym: " + center + "\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$fieldCenter.acronym\",\n" + 
        "      allRns: {\n" + 
        "        $push: {\n" + 
        "          participantData: {\n" + 
        "            recruitmentNumber: \"$recruitmentNumber\"\n" + 
        "          }\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    
    return pipeline;
  }
  
  public List<Bson>  getInapplicabilities() {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: {\n" + 
        "        \"acronym\": \"$acronym\",\n" + 
        "        \"recruitmentNumber\": \"$recruitmentNumber\"\n" + 
        "      },\n" + 
        "      AI: {\n" + 
        "        $push: {\n" + 
        "          \"acronym\": \"$acronym\",\n" + 
        "          \"recruitmentNumber\": \"$recruitmentNumber\",\n" + 
        "          \"observation\": \"$observation\"\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$_id.recruitmentNumber\",\n" + 
        "      AI: {\n" + 
        "        $push: {\n" + 
        "          $arrayElemAt: [\"$AI\", 0]\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: \"inapplicability\",\n" + 
        "      inapplicabilities: {\n" + 
        "        $push: {\n" + 
        "          rn: \"$_id\",\n" + 
        "          AI: \"$AI\"\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    
    return pipeline;
  }
  
  public List<Bson> getAllAcronyms() {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" + 
        "    $group: {\n" + 
        "      _id: '',\n" + 
        "      acronyms: { $addToSet: \"$surveyTemplate.identity.acronym\" }\n" + 
        "    }\n" + 
        "  }"));
    
    return pipeline;
  }

}
package br.org.otus.laboratory.extraction.builder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.GsonBuilder;

public class ParticipantLaboratoryExtractionQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ParticipantLaboratoryExtractionQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public ArrayList<Bson> getAttachedLaboratoryForExtractionQuery() {
    attachedLaboratoryQuery();
    forExtraction();
    return this.pipeline;
  }

  public void attachedLaboratoryQuery() {
    this.pipeline.add(parseQuery(" {\n" +
      "            $match:{\n" +
      "                     \"availableToAttache\" : false\n" +
      "            }\n" +
      "        }"));
    this.pipeline.add(parseQuery("{\n" +
      "            $lookup:{\n" +
      "                from:\"participant\",\n" +
      "                localField:\"participantId\",\n" +
      "                foreignField:\"_id\",\n" +
      "                as:\"participantInfo\"\n" +
      "            }\n" +
      "        }"));
    this.pipeline.add(parseQuery("{\n" +
      "            $project:{\n" +
      "                fieldCenterAcronym:1,\n" +
      "                recruitmentNumber: {$arrayElemAt:[\"$participantInfo.recruitmentNumber\",0]},\n" +
      "                unattachedLaboratoryIdentification: \"$identification\"\n" +
      "            }\n" +
      "        }"));
  }

  private void forExtraction() {
    this.pipeline.add(parseQuery("{\n" +
      "        $group:{\n" +
      "            _id:{},\n" +
      "            recruitmentNumbers:{$push:\"$recruitmentNumber\"},\n" +
      "            relations:{$push:{\n" +
      "                fieldCenterAcronym: \"$fieldCenterAcronym\",\n" +
      "                recruitmentNumber: \"$recruitmentNumber\",\n" +
      "                unattachedLaboratoryIdentification: \"$unattachedLaboratoryIdentification\"\n" +
      "            }}\n" +
      "        }\n" +
      "    }"));
  }



  public ArrayList<Bson> getTubeCodesInAliquotQuery() {
    this.pipeline.add(parseQuery("{$group:{_id:\"$tubeCode\"}}"));
    this.pipeline.add(parseQuery("{$group:{_id:{},tubeCodes:{$push:\"$_id\"}}}"));
    return this.pipeline;
  }

  public ArrayList<Bson> getNotAliquotedTubesQuery(ArrayList<String> tubeCodes, Document attachedLaboratories, boolean extractionFromUnattached) {
    List<String> recruitmentNumbers;
    List<String> relations;
    if(attachedLaboratories != null){
      recruitmentNumbers = (ArrayList) attachedLaboratories.get("recruitmentNumbers");
      relations = (ArrayList) attachedLaboratories.get("relations");
    } else {
      recruitmentNumbers = Arrays.asList();
      relations = Arrays.asList();
    }

    Document projectInitialFields = new Document("_id", 0).append("recruitmentNumber", 1).append("tubes", 1);
    Document fieldsToPush = new Document("recruitmentNumber", "$recruitmentNumber");
    if (extractionFromUnattached) {
      this.pipeline.add(new Document("$match", new Document("recruitmentNumber", new Document("$in", recruitmentNumbers))));
      this.pipeline.add(new Document("$addFields", new Document("unattachedLaboratory", new Document("$filter", new Document("input", relations).append("as","relation").append("cond",new Document("$eq", Arrays.asList("$$relation.recruitmentNumber","$recruitmentNumber")))))));
      projectInitialFields.append("unattachedLaboratory",1);
      fieldsToPush.append("unattachedLaboratoryId",new Document("$arrayElemAt",Arrays.asList("$unattachedLaboratory.unattachedLaboratoryIdentification",0)));
    } else {
      this.pipeline.add(new Document("$match", new Document("recruitmentNumber", new Document("$nin", recruitmentNumbers))));
      fieldsToPush.append("unattachedLaboratoryId", new Integer(0));
    }

    fieldsToPush.append("tubeCode", new Document("$ifNull", Arrays.asList("$tubes.code", "")))
      .append("tubeQualityControl",new Document("$cond",Arrays.asList(new Document("$eq",Arrays.asList("$tubes.groupName","DEFAULT")),0,1)))
      .append("tubeType", new Document("$ifNull", Arrays.asList("$tubes.type", "")))
      .append("tubeMoment", new Document("$ifNull", Arrays.asList("$tubes.moment", "")))
      .append("tubeCollectionDate",new Document("$ifNull", Arrays.asList("$tubes.tubeCollectionData.time", "")))
      .append("tubeResponsible", new Document("$ifNull", Arrays.asList("$tubes.tubeCollectionData.operator", "")))
      .append("aliquotCode","")
      .append("aliquotName","")
      .append("aliquotContainer","")
      .append("aliquotProcessingDate","")
      .append("aliquotRegisterDate","")
      .append("aliquotResponsible","")
      .append("aliquotRole","")
      .append("hasTransportationLotId","")
      .append("hasExamLotId","");

    this.pipeline.add(new Document("$project",projectInitialFields));
    this.pipeline.add(parseQuery("{ $unwind: \"$tubes\" }"));
    this.pipeline.add(new Document("$match", new Document("tubes.code", new Document("$nin", tubeCodes))));
    this.pipeline.add(new Document("$group", new Document("_id", "$recruitmentNumber").append("result", new Document("$push",fieldsToPush))));
    this.pipeline.add(parseQuery("{\n" +
      "        $project:{\n" +
      "            _id:0,\n" +
      "            recruitmentNumber:\"$_id\",\n" +
      "            results:\"$result\"\n" +
      "        }\n" +
      "    }"));
    return this.pipeline;
  }

  public ArrayList<Bson> getAliquotedTubesQuery(Document attachedLaboratories, boolean extractionFromUnattached) {
    List<String> recruitmentNumbers;
    List<String> relations;
    if(attachedLaboratories != null){
      recruitmentNumbers = (ArrayList) attachedLaboratories.get("recruitmentNumbers");
      relations = (ArrayList) attachedLaboratories.get("relations");
    } else {
      recruitmentNumbers = Arrays.asList();
      relations = Arrays.asList();
    }

    Document fieldsToPush = new Document("recruitmentNumber", "$recruitmentNumber");
    if (extractionFromUnattached) {
      this.pipeline.add(new Document("$match", new Document("recruitmentNumber", new Document("$in", recruitmentNumbers))));
      fieldsToPush.append("unattachedLaboratoryId",new Document("$arrayElemAt",Arrays.asList("$unattachedLaboratory.unattachedLaboratoryIdentification",0)));
    } else {
      this.pipeline.add(new Document("$match", new Document("recruitmentNumber", new Document("$nin", recruitmentNumbers))));
      fieldsToPush.append("unattachedLaboratoryId",new Integer(0));
    }

    fieldsToPush.append("tubeCode", new Document("$ifNull", Arrays.asList("$tubeData.tubeCode", "")))
      .append("tubeQualityControl", new Document("$ifNull", Arrays.asList("$tubeData.qualityControl", new Integer(0))))
      .append("tubeType", new Document("$ifNull", Arrays.asList("$tubeData.type" , "")))
      .append("tubeMoment", new Document("$ifNull", Arrays.asList("$tubeData.moment", "")))
      .append("tubeCollectionDate", new Document("$ifNull", Arrays.asList("$tubeData.collectionDate", "")))
      .append("tubeResponsible", new Document("$ifNull", Arrays.asList("$tubeData.tubeResponsible", "")))
      .append("aliquotCode", new Document("$ifNull", Arrays.asList("$code", "")))
      .append("aliquotName", new Document("$ifNull", Arrays.asList("$name", "")))
      .append("aliquotContainer", new Document("$ifNull", Arrays.asList("$container", "")))
      .append("aliquotProcessingDate", new Document("$ifNull", Arrays.asList("$aliquotCollectionData.processing", "")))
      .append("aliquotRegisterDate", new Document("$ifNull", Arrays.asList("$aliquotCollectionData.operator", "")))
      .append("aliquotResponsible", new Document("$ifNull", Arrays.asList("$aliquotCollectionData.time" , "")))
      .append("aliquotRole",new Document("$ifNull", Arrays.asList("$role", "")))
      .append("hasTransportationLotId", parseQuery("{\"$ifNull\":[{\"$toBool\":\"$transportationLotId\"},false]}"))
      .append("hasExamLotId", parseQuery("{\"$ifNull\":[{\"$toBool\":\"$examLotId\"},false]}"));
    
    this.pipeline.add(parseQuery("{\n" +
      "    $lookup: {\n" +
      "      from: \"participant_laboratory\",\n" +
      "      let: {\n" +
      "        code: \"$tubeCode\",\n" +
      "        recruitmentNumber: \"$recruitmentNumber\"\n" +
      "      },\n" +
      "      pipeline: [\n" +
      "        {\n" +
      "          $match: {\n" +
      "            $expr: {\n" +
      "              $eq: [\n" +
      "                \"$recruitmentNumber\",\n" +
      "                \"$$recruitmentNumber\"\n" +
      "              ]\n" +
      "            }\n" +
      "          }\n" +
      "        },\n" +
      "        {\n" +
      "          $unwind: \"$tubes\"\n" +
      "        },\n" +
      "        {\n" +
      "          $match: {\n" +
      "            $expr: {\n" +
      "              $eq: [\n" +
      "                \"$tubes.code\",\n" +
      "                \"$$code\"\n" +
      "              ]\n" +
      "            }\n" +
      "          }\n" +
      "        },\n" +
      "        {\n" +
      "          $project: {\n" +
      "            tubeCode: \"$tubes.code\",\n" +
      "            qualityControl: {\n" +
      "              $cond: [\n" +
      "                {\n" +
      "                  $eq: [\n" +
      "                    \"$tubes.groupName\",\n" +
      "                    \"DEFAULT\"\n" +
      "                  ]\n" +
      "                },\n" +
      "                0,\n" +
      "                1\n" +
      "              ]\n" +
      "            },\n" +
      "            type: '$tubes.type',\n" +
      "            moment: '$tubes.moment',\n" +
      "            collectionDate: \"$tubes.tubeCollectionData.time\",\n" +
      "            tubeResponsible: \"$tubes.tubeCollectionData.operator\"\n" +
      "          }\n" +
      "        }\n" +
      "      ],\n" +
      "      as: \"tube\"\n" +
      "    }\n" +
      "  }"));
    this.pipeline.add(parseQuery("{\n" +
      "    $addFields: {\n" +
      "      tubeData: {\n" +
      "        $arrayElemAt: [\n" +
      "          \"$tube\",\n" +
      "          0\n" +
      "        ]\n" +
      "      }\n" +
      "    }\n" +
      "  }"));
    if(extractionFromUnattached){
      this.pipeline.add(new Document("$addFields", new Document("unattachedLaboratory", new Document("$filter", new Document("input", relations).append("as","relation").append("cond",new Document("$eq", Arrays.asList("$$relation.recruitmentNumber","$recruitmentNumber")))))));
    }
    this.pipeline.add(new Document("$group", new Document("_id", "$recruitmentNumber").append("result", new Document("$push",fieldsToPush))));
    this.pipeline.add(parseQuery("{\n" +
      "    $project: {\n" +
      "      _id: 0,\n" +
      "      recruitmentNumber: \"$_id\",\n" +
      "      results: \"$result\"\n" +
      "    }\n" +
      "  }"));
    return this.pipeline;
  }

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }

}

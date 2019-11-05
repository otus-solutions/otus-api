package br.org.otus.laboratory.extraction.builder;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.GsonBuilder;

public class ParticipantLaboratoryExtractionQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ParticipantLaboratoryExtractionQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }
  
  public ArrayList<Bson> getTubeCodesInAliquotQuery() {
    this.pipeline.add(parseQuery("{$group:{_id:\"$tubeCode\"}}"));
    this.pipeline.add(parseQuery("{$group:{_id:{},tubeCodes:{$push:\"$_id\"}}}"));
    return this.pipeline;
  }
  
  public ArrayList<Bson> getNotAliquotedTubesQuery(ArrayList<String> tubeCodes) {
    this.pipeline.add(parseQuery("{ $project: { _id: 0, recruitmentNumber: 1, tubes: 1 } }"));
    this.pipeline.add(parseQuery("{ $unwind: \"$tubes\" }"));
    this.pipeline.add(new Document("$match",new Document("tubes.code",new Document("$nin",tubeCodes))));
    this.pipeline.add(parseQuery("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$recruitmentNumber\",\n" + 
        "      result: {\n" + 
        "        $push: {\n" + 
        "          recruitmentNumber: \"$recruitmentNumber\",\n" + 
        "          tubeCode: \"$tubes.code\",\n" + 
        "          tubeQualityControl: { $cond: [{ $eq: [\"$tubes.groupName\", \"DEFAULT\"] }, 0, 1] },\n" + 
        "          tubeType: '$tubes.type',\n" + 
        "          tubeMoment: '$tubes.moment',\n" + 
        "          tubeCollectionDate: \"$tubes.tubeCollectionData.time\",\n" + 
        "          tubeResponsible: \"$tubes.tubeCollectionData.operator\",\n" + 
        "          aliquotCode: null,\n" + 
        "          aliquotName: null,\n" + 
        "          aliquotContainer: null,\n" + 
        "          aliquotProcessingDate: null,\n" + 
        "          aliquotRegisterDate: null,\n" + 
        "          aliquotResponsible: null\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    this.pipeline.add(parseQuery("{\n" +
            "        $project:{\n" +
            "            _id:0,\n" +
            "            recruitmentNumber:\"$_id\",\n" +
            "            results:\"$result\"\n" +
            "        }\n" +
            "    }"));
    return this.pipeline;
  }
  
  public ArrayList<Bson> getAliquotedTubesQuery() {
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
    this.pipeline.add(parseQuery("{\n" +
            "    $group: {\n" +
            "      _id: \"$recruitmentNumber\",\n" +
            "      result: {\n" +
            "        $push: {\n" +
            "          recruitmentNumber: \"$recruitmentNumber\",\n" +
            "          tubeCode: \"$tubeData.tubeCode\",\n" +
            "          tubeQualityControl: \"$tubeData.qualityControl\",\n" +
            "          tubeType: '$tubeData.type',\n" + 
            "          tubeMoment: '$tubeData.moment',\n" + 
            "          tubeCollectionDate: \"$tubeData.collectionDate\",\n" +
            "          tubeResponsible: \"$tubeData.tubeResponsible\",\n" +
            "          aliquotCode: \"$code\",\n" +
            "          aliquotName: \"$name\",\n" +
            "          aliquotContainer: \"$container\",\n" +
            "          aliquotProcessingDate: \"$aliquotCollectionData.processing\",\n" +
            "          aliquotResponsible: \"$aliquotCollectionData.operator\",\n" +
            "          aliquotRegisterDate: \"$aliquotCollectionData.time\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }"));
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

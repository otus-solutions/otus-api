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
    this.pipeline.add(parseQuery("{ $lookup: { from: \"aliquot\", localField: \"tubes.code\", foreignField: \"tubeCode\", as: \"aliquot\" } }"));
    this.pipeline.add(parseQuery("{\n" + 
        "    $group: {\n" + 
        "      _id: \"$recruitmentNumber\", result: {\n" + 
        "        $push: {\n" + 
        "          recruitmentNumber: \"$recruitmentNumber\", tubeCode: \"$tubes.code\", qualityControl: { $cond: [{ $eq: [\"$tubes.groupName\", \"DEFAULT\"] }, 0, 1] },\n" + 
        "          collectionDate: \"$tubes.tubeCollectionData.time\",\n" + 
        "          tubeResponsible: \"$tubes.tubeCollectionData.operator\",\n" + 
        "          aliquotCode: null,\n" + 
        "          aliquotName: null,\n" + 
        "          processingDate: null,\n" + 
        "          aliquotResponsible: null,\n" + 
        "          registerDate: null\n" + 
        "        }\n" + 
        "      }\n" + 
        "    }\n" + 
        "  }"));
    return this.pipeline;
  }
  
  public ArrayList<Bson> getAliquotedTubesQuery() {
    this.pipeline.add(parseQuery("{\n" + 
        "        $lookup:{\n" + 
        "            from:\"participant_laboratory\",\n" + 
        "            let:{code:\"$tubeCode\",recruitmentNumber:\"$recruitmentNumber\"},\n" + 
        "            pipeline:[\n" + 
        "                {\n" + 
        "                    $match:{\n" + 
        "                        $expr:{\n" + 
        "                            $eq:[\"$recruitmentNumber\",\"$$recruitmentNumber\"]\n" + 
        "                        }\n" + 
        "                    }\n" + 
        "                },\n" + 
        "                {$unwind:\"$tubes\"},\n" + 
        "                {\n" + 
        "                    $match:{\n" + 
        "                        $expr:{\n" + 
        "                            $eq:[\"$tubes.code\",\"$$code\"]\n" + 
        "                        }\n" + 
        "                    }\n" + 
        "                },\n" + 
        "                {\n" + 
        "                    $project:{\n" + 
        "                        tubeCode:\"$tubes.code\",\n" + 
        "                        qualityControl: { $cond: [ {$eq:[\"$tubes.groupName\",\"DEFAULT\"]}, 0, 1 ] },\n" + 
        "                        collectionDate: \"$tubes.tubeCollectionData.time\",\n" + 
        "                        tubeResponsible:\"$tubes.tubeCollectionData.operator\",\n" + 
        "                    }\n" + 
        "                }\n" + 
        "            ],\n" + 
        "            as:\"tube\"    \n" + 
        "        }\n" + 
        "    }"));
    this.pipeline.add(parseQuery("{ $addFields: { tubeData: { $arrayElemAt: [ \"$tube\", 0 ] } } }"));
    this.pipeline.add(parseQuery("{\n" + 
        "        $group:{\n" + 
        "            _id:\"$recruitmentNumber\",\n" + 
        "            result:{$push:{\n" + 
        "                recruitmentNumber:\"$recruitmentNumber\",\n" + 
        "                tubeCode:\"$tubeData.tubeCode\",\n" + 
        "                qualityControl: \"$tubeData.qualityControl\",\n" + 
        "                collectionDate: \"$tubeData.collectionDate\",\n" + 
        "                tubeResponsible:\"$tubeData.tubeResponsible\",\n" + 
        "                aliquotCode:\"$code\",\n" + 
        "                aliquotName:\"$name\",\n" + 
        "                processingDate:\"$aliquotCollectionData.processing\",\n" + 
        "                aliquotResponsible:\"$aliquotCollectionData.operator\",\n" + 
        "                registerDate:\"$aliquotCollectionData.time\"\n" + 
        "            }}\n" + 
        "        }\n" + 
        "    }"));
    this.pipeline.add(parseQuery("{\n" + 
        "        $project:{\n" + 
        "            _id:0,\n" + 
        "            recruitmentNumber:\"$_id\",\n" + 
        "            result:\"$result\"\n" + 
        "        }\n" + 
        "    }"));
    return this.pipeline;
  }
  
  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }
}

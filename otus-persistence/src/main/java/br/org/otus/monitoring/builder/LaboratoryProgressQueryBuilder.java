package br.org.otus.monitoring.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class LaboratoryProgressQueryBuilder {
    private ArrayList<Bson> pipeline;

    public LaboratoryProgressQueryBuilder() {
        this.pipeline = new ArrayList<>();
    }

    public List<Bson> getOrphansQuery() {
        pipeline.add(parseQuery("{\n" +
                "        $match:{\"aliquotValid\":false}\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $group:\n" +
                "        {\n" +
                "            _id:{examId:\"$examId\",examName:\"$examName\"}\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $group:\n" +
                "        {\n" +
                "            _id:\"$_id.examName\",\n" +
                "            examIds:{$push:\"$_id.examId\"},\n" +
                "            count:{$sum:1}\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $group:\n" +
                "        {\n" +
                "            _id:{},\n" +
                "            orphanExamsProgress:\n" +
                "            {\n" +
                "                $push:\n" +
                "                {\n" +
                "                    title:\"$_id\",\n" +
                "                    orphans:\"$count\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$match\": {\n" +
                "      \"orphanExamsProgress\": {\n" +
                "        \"$exists\": true\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;
    }

    public List<Bson> getQuantitativeQuery(String center) {
        pipeline.add(parseQuery("{\n" +
                "    $match: {\n" +
                "      \"role\": \"EXAM\",\n" +
                "      \"fieldCenter.acronym\":" + center +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$group\": {\n" +
                "      \"_id\": \"$name\",\n" +
                "      \"aliquots\": {\n" +
                "        \"$push\": {\n" +
                "          \"code\": \"$code\",\n" +
                "          \"transported\": {\n" +
                "            \"$cond\": {\n" +
                "              \"if\": {\n" +
                "                \"$ne\": [\n" +
                "                  \"$transportationLotId\",\n" +
                "                  null\n" +
                "                ]\n" +
                "              },\n" +
                "              \"then\": 1,\n" +
                "              \"else\": 0\n" +
                "            }\n" +
                "          },\n" +
                "          \"prepared\": {\n" +
                "            \"$cond\": {\n" +
                "              \"if\": {\n" +
                "                \"$ne\": [\n" +
                "                  \"$examLotId\",\n" +
                "                  null\n" +
                "                ]\n" +
                "              },\n" +
                "              \"then\": 1,\n" +
                "              \"else\": 0\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$unwind\": \"$aliquots\"\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$group\": {\n" +
                "      \"_id\": \"$_id\",\n" +
                "      \"transported\": {\n" +
                "        \"$sum\": \"$aliquots.transported\"\n" +
                "      },\n" +
                "      \"prepared\": {\n" +
                "        \"$sum\": \"$aliquots.prepared\"\n" +
                "      },\n" +
                "      \"aliquots\": {\n" +
                "        \"$push\": \"$aliquots.code\"\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$lookup\": {\n" +
                "      \"from\": \"exam_result\",\n" +
                "      \"let\": {\n" +
                "        \"aliquotCode\": \"$aliquots\"\n" +
                "      },\n" +
                "      \"pipeline\": [\n" +
                "        {\n" +
                "          \"$match\": {\n" +
                "            \"$expr\": {\n" +
                "              \"$and\": [\n" +
                "                {\n" +
                "                  \"$in\": [\n" +
                "                    \"$aliquotCode\",\n" +
                "                    \"$$aliquotCode\"\n" +
                "                  ]\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"$group\": {\n" +
                "            \"_id\": \"$examId\",\n" +
                "            \"received\": {\n" +
                "              \"$push\": \"$aliquotCode\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"$count\": \"count\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"as\": \"receivedCount\"\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$group\": {\n" +
                "      \"_id\": {},\n" +
                "      \"quantitativeByTypeOfAliquots\": {\n" +
                "        \"$push\": {\n" +
                "          \"title\": \"$_id\",\n" +
                "          \"transported\": \"$transported\",\n" +
                "          \"prepared\": \"$prepared\",\n" +
                "          \"received\": {\n" +
                "            \"$cond\": {\n" +
                "              \"if\": {\n" +
                "                \"$gte\": [\n" +
                "                  {\n" +
                "                    \"$size\": \"$receivedCount\"\n" +
                "                  },\n" +
                "                  1\n" +
                "                ]\n" +
                "              },\n" +
                "              \"then\": {\n" +
                "                \"$arrayElemAt\": [\n" +
                "                  \"$receivedCount.count\",\n" +
                "                  0\n" +
                "                ]\n" +
                "              },\n" +
                "              \"else\": 0\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;
    }

    public List<Bson> getPendingResultsQuery(String center) {
        pipeline.add(parseQuery("{\n" +
                "    $match: {\n" +
                "      \"fieldCenter.acronym\":"+ center +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: \"$name\",\n" +
                "      aliquotsInDb: {\n" +
                "        $push: \"$code\"\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $lookup: {\n" +
                "      from: \"exam_result\",\n" +
                "      let: {\n" +
                "        \"aliquotCodes\": \"$aliquotsInDb\"\n" +
                "      },\n" +
                "      pipeline: [\n" +
                "        {\n" +
                "          $match: {\n" +
                "            $expr: {\n" +
                "              $and: [\n" +
                "                {\n" +
                "                  $in: [\n" +
                "                    \"$aliquotCode\",\n" +
                "                    \"$$aliquotCodes\"\n" +
                "                  ]\n" +
                "                },\n" +
                "                {\n" +
                "                  $eq: [\n" +
                "                    \"$aliquotValid\",\n" +
                "                    true\n" +
                "                  ]\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: {\n" +
                "              examId: \"$examId\",\n" +
                "              examName: \"$examName\",\n" +
                "              aliquotCode: \"$aliquotCode\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: \"$_id.aliquotCode\",\n" +
                "            aliquots: {\n" +
                "              $push: \"$_id.aliquotCode\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: {},\n" +
                "            aliquots: {\n" +
                "              $push: \"$_id\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      as: \"aliquotsWithResults\"\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$group\": {\n" +
                "      \"_id\": {},\n" +
                "      \"pendingResultsByAliquot\": {\n" +
                "        \"$push\": {\n" +
                "          \"title\": \"$_id\",\n" +
                "          \"waiting\": {\n" +
                "            \"$cond\": {\n" +
                "              \"if\": {\n" +
                "                \"$gte\": [\n" +
                "                  {\n" +
                "                    \"$size\": \"$aliquotsWithResults\"\n" +
                "                  },\n" +
                "                  1\n" +
                "                ]\n" +
                "              },\n" +
                "              \"then\": {\n" +
                "                \"$subtract\": [\n" +
                "                  {\n" +
                "                    \"$size\": \"$aliquotsInDb\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                    \"$size\": {\n" +
                "                      \"$arrayElemAt\": [\n" +
                "                        \"$aliquotsWithResults.aliquots\",\n" +
                "                        0\n" +
                "                      ]\n" +
                "                    }\n" +
                "                  }\n" +
                "                ]\n" +
                "              },\n" +
                "              \"else\": {\n" +
                "                \"$size\": \"$aliquotsInDb\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"received\": {\n" +
                "            \"$cond\": {\n" +
                "              \"if\": {\n" +
                "                \"$gte\": [\n" +
                "                  {\n" +
                "                    \"$size\": \"$aliquotsWithResults\"\n" +
                "                  },\n" +
                "                  1\n" +
                "                ]\n" +
                "              },\n" +
                "              \"then\": {\n" +
                "                \"$size\": {\n" +
                "                  \"$arrayElemAt\": [\n" +
                "                    \"$aliquotsWithResults.aliquots\",\n" +
                "                    0\n" +
                "                  ]\n" +
                "                }\n" +
                "              },\n" +
                "              \"else\": 0\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;
    }

    public List<Bson> getStorageByAliquotQuery(String center) {
        pipeline.add(parseQuery(" {\n" +
                "    $match: {\n" +
                "      \"role\": \"STORAGE\",\n" +
                "      \"fieldCenter.acronym\":" + center +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: \"$name\",\n" +
                "      count: {\n" +
                "        $sum: 1\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: {},\n" +
                "      storageByAliquot: {\n" +
                "        $push: {\n" +
                "          title: \"$_id\",\n" +
                "          storage: \"$count\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;
    }

    public List<Bson> getDataByExamQuery(String center) {
        pipeline.add(parseQuery("{\n" +
                "    $match: {\n" +
                "      \"role\": \"EXAM\",\n" +
                "      \"fieldCenter.acronym\":" + center +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: {},\n" +
                "      aliquotCodes: {\n" +
                "        $push: \"$code\"\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $lookup: {\n" +
                "      from: \"exam_result\",\n" +
                "      let: {\n" +
                "        \"aliquotCodes\": \"$aliquotCodes\"\n" +
                "      },\n" +
                "      pipeline: [\n" +
                "        {\n" +
                "          $match: {\n" +
                "            $expr: {\n" +
                "              $and: [\n" +
                "                {\n" +
                "                  $in: [\n" +
                "                    \"$aliquotCode\",\n" +
                "                    \"$$aliquotCodes\"\n" +
                "                  ]\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: {\n" +
                "              examId: \"$examId\",\n" +
                "              examName: \"$examName\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: \"$_id.examName\",\n" +
                "            received: {\n" +
                "              $sum: 1\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          $group: {\n" +
                "            _id: {},\n" +
                "            examsQuantitative: {\n" +
                "              $push: {\n" +
                "                title: \"$_id\",\n" +
                "                exams: \"$received\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      as: \"receivedCount\"\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    \"$match\": {\n" +
                "      \"receivedCount.examsQuantitative\": {\n" +
                "        \"$exists\": true,\n" +
                "        \"$not\": {\n" +
                "          \"$size\": 0.0\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $project: {\n" +
                "      examsQuantitative: {\n" +
                "        $arrayElemAt: [\n" +
                "          \"$receivedCount.examsQuantitative\",\n" +
                "          0\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;
    }

    public List<Bson> getCSVOfPendingResultsQuery(String center){
        pipeline.add(parseQuery("{\n" +
                "    $match: {\n" +
                "      \"role\": \"EXAM\",\n" +
                "      \"fieldCenter.acronym\":" + center +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $project: {\n" +
                "      code: \"$code\",\n" +
                "      transported: {\n" +
                "        $cond: {\n" +
                "          if: {\n" +
                "            $ne: [\n" +
                "              \"$transportationLotId\",\n" +
                "              null\n" +
                "            ]\n" +
                "          },\n" +
                "          then: 1,\n" +
                "          else: 0\n" +
                "        }\n" +
                "      },\n" +
                "      prepared: {\n" +
                "        $cond: {\n" +
                "          if: {\n" +
                "            $ne: [\n" +
                "              \"$examLotId\",\n" +
                "              null\n" +
                "            ]\n" +
                "          },\n" +
                "          then: 1,\n" +
                "          else: 0\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $lookup: {\n" +
                "      from: \"exam_result\",\n" +
                "      localField: \"code\",\n" +
                "      foreignField: \"aliquotCode\",\n" +
                "      as: \"results\"\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $match: {\n" +
                "      results: {\n" +
                "        $exists: true,\n" +
                "        $size: 0\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: {},\n" +
                "      pendingAliquotsCsvData: {\n" +
                "        $push: {\n" +
                "          aliquot: \"$code\",\n" +
                "          transported: \"$transported\",\n" +
                "          prepared: \"$prepared\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;

    }

    public List<Bson> getCSVOfOrphansByExamQuery() {
        pipeline.add(parseQuery("{\n" +
                "    $match: {\n" +
                "      \"aliquotValid\": false\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: {\n" +
                "        examId: \"$examId\",\n" +
                "        aliquotCode: \"$aliquotCode\",\n" +
                "        examName: \"$examName\"\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        pipeline.add(parseQuery("{\n" +
                "    $group: {\n" +
                "      _id: {},\n" +
                "      orphanExamsCsvData: {\n" +
                "        $push: {\n" +
                "          aliquotCode: \"$_id.aliquotCode\",\n" +
                "          examName: \"$_id.examName\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }"));
        return this.pipeline;
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }
}

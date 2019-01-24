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

    public List<Bson> getQuantitativeByTypeOfAliquotsFirstPartialResultQuery(String center){
        pipeline.add(parseQuery("{$match:{\"role\":\"EXAM\",\"fieldCenter.acronym\":" + center + "}}"));
        pipeline.add(parseQuery("{\"$group\":{\"_id\":\"$name\",\"aliquots\":{\"$push\":{\"code\":\"$code\",\"transported\":{\"$cond\":{\"if\":{\"$ne\":[\"$transportationLotId\",null]},\"then\":1.0,\"else\":0.0}},\"prepared\":{\"$cond\":{\"if\":{\"$ne\":[\"$examLotId\",null]},\"then\":1.0,\"else\":{\"$cond\":{\"if\":{\"$ne\":[\"$examLotData.id\",null]},\"then\":1.0,\"else\":0.0}}}}}}}}"));
        pipeline.add(parseQuery("{$unwind:\"$aliquots\"}"));
        pipeline.add(parseQuery("{$group:{_id:\"$_id\",transported:{$sum:\"$aliquots.transported\"},prepared:{$sum: \"$aliquots.prepared\"}}}"));
        pipeline.add(parseQuery("{$group:{_id:{},quantitativeByTypeOfAliquots:{$push:{title:\"$_id\",transported:\"$transported\",prepared:\"$prepared\"}}}}"));
        return this.pipeline;
    }

    public List<Bson> getPendingResultsByAliquotFirstPartialResultQuery(ArrayList<String> allAliquotCodesinExams, String center){
        pipeline.add(new Document("$match", new Document("code", new Document("$nin", allAliquotCodesinExams)).append("fieldCenter.acronym", center)));
        pipeline.add(parseQuery("{$group:{_id:\"$name\",waiting:{$sum:1}}}"));
        pipeline.add(parseQuery("{$group:{_id:{},pendingResultsByAliquot:{$push:{\"title\": \"$_id\", \"waiting\":\"$waiting\"}}}}"));
        return this.pipeline;
    }

    public List<Bson> getPendingResultsByAliquotSecondPartialResultQuery(ArrayList<String> allAliquotCodesinExams, String center){
        pipeline.add(new Document("$match", new Document("code", new Document("$in", allAliquotCodesinExams)).append("fieldCenter.acronym", center)));
        pipeline.add(parseQuery("{$group:{_id:\"$name\",received:{$sum:1}}}"));
        pipeline.add(parseQuery("{$group:{_id:{},pendingResultsByAliquot:{$push:{\"title\": \"$_id\", \"received\":\"$received\"}}}}"));
        return this.pipeline;
    }

    public List<Bson> getQuantitativeByTypeOfAliquotsSecondPartialResultQuery(ArrayList<String> aliquotCodes){
        pipeline.add(new Document("$match", new Document("code", new Document("$in", aliquotCodes))));
        pipeline.add(parseQuery("{$group:{_id:\"$name\",received:{$sum:1}}}"));
        pipeline.add(parseQuery("{$group:{_id:{},quantitativeByTypeOfAliquots:{$push:{title:\"$_id\",received:\"$received\"}}}}"));
        return this.pipeline;
    }

    public List<Bson> getAliquotCodesInExamLotQuery(ArrayList<String> aliquotCodes){
        pipeline.add(new Document("$match", new Document("aliquotCode", new Document("$in", aliquotCodes))));
        pipeline.add(parseQuery("{$group:{_id:\"$examId\",aliquotCodes:{$addToSet:\"$aliquotCode\"}}}"));
        pipeline.add(parseQuery("{$unwind:\"$aliquotCodes\"}"));
        pipeline.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$aliquotCodes\"}}}"));
        return this.pipeline;
    }

    public List<Bson> getPendingAliquotsCsvDataQuery(ArrayList<String> aliquotCodes, String center){
        pipeline.add(new Document("$match", new Document("code", new Document("$nin", aliquotCodes)).append("fieldCenter.acronym", center).append("role", "EXAM")));
        pipeline.add(parseQuery("{\"$project\":{\"code\":\"$code\",\"transported\":{\"$cond\":{\"if\":{\"$ne\":[\"$transportationLotId\",null]},\"then\":1.0,\"else\":0.0}},\"prepared\":{\"$cond\":{\"if\":{\"$ne\":[\"$examLotId\",null]},\"then\":1.0,\"else\":{\"$cond\":{\"if\":{\"$ne\":[\"$examLotData.id\",null]},\"then\":1.0,\"else\":0.0}}}}}}"));
        pipeline.add(parseQuery("{$group:{_id:{},pendingAliquotsCsvData:{$push:{aliquot:\"$code\",transported:\"$transported\",prepared:\"$prepared\"}}}}"));
        return this.pipeline;
    }

    public List<Bson> getDataByExamQuery(ArrayList<String> aliquotCodes){
        pipeline.add(new Document("$match", new Document("aliquotCode", new Document("$in", aliquotCodes))));
        pipeline.add(parseQuery("{$group:{_id:{examId:\"$examId\",examName:\"$examName\"}}}"));
        pipeline.add(parseQuery("{$group:{_id:\"$_id.examName\",received:{$sum:1}}}"));
        pipeline.add(parseQuery("{$group:{_id:{},examsQuantitative:{$push:{title:\"$_id\",exams:\"$received\"}}}}"));
        return this.pipeline;
    }
    public List<Bson> fetchAllAliquotCodesQuery(String center){
        pipeline.add(parseQuery("{$match:{\"fieldCenter.acronym\":" + center + "}}"));
        pipeline.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$code\"}}}"));
        return this.pipeline;
    }

    public List<Bson> fetchAllAliquotCodesInExamsQuery(){
        pipeline.add(parseQuery("{$match:{\"aliquotValid\":true}}"));
        pipeline.add(parseQuery("{$group:{_id:\"$examId\",aliquotCodes:{$addToSet:\"$aliquotCode\"}}}"));
        pipeline.add(parseQuery("{$unwind:\"$aliquotCodes\"}"));
        pipeline.add(parseQuery("{$group:{_id:{},aliquotCodes:{$addToSet:\"$aliquotCodes\"}}}"));
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

package br.org.otus.monitoring.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class ExamStatusQueryBuilder {

    private ArrayList<Bson> pipeline;

    public ExamStatusQueryBuilder() {
        this.pipeline = new ArrayList<>();
    }

    public List<Bson> getExamQuery(Long rn, List<String> examName) {
        addMatchRecruitmentNumber(rn);
        addGroupDescriptorsNameAndId();
        addGroupDescriptorsNameAndQuantity();
        addGroupDescriptorsExams();
        addAddFieldsAllExams(examName);
        addUnwindAllExams();
        addAddFieldsFilter();
        addProjectAllExams();
        addLookupExamInapplicability(rn);
        addGroupExamInapplicability();

        return pipeline;
    }

    public List<Bson> getExamInapplicabilityQuery(Long rn, List<String> examName){
        addMatchRecruitmentNumber(rn);
        addAddFieldsAllExams(examName);
        addUnwindAllExams();
        addProjectObservation();
        addGroupExamInapplicability();

        return pipeline;
    }

    private void addMatchRecruitmentNumber(Long rn){
        pipeline.add(parseQuery("{$match:{\"recruitmentNumber\":"+rn+"}}"));
    }

    private void addGroupDescriptorsNameAndId(){
        pipeline.add(parseQuery("{\n" +
                "      $group:{\n" +
                "          _id:{examName:\"$examName\",examId:\"$examId\"}\n" +
                "      }  \n" +
                "    }"));
    }

    private void addGroupDescriptorsNameAndQuantity(){
        pipeline.add(parseQuery(" {\n" +
                "        $group:{\n" +
                "            _id:{examName:\"$_id.examName\"},\n" +
                "            quantity:{$sum:1}\n" +
                "        }\n" +
                "    }"));
    }

    private void addGroupDescriptorsExams(){
        pipeline.add(parseQuery("  {\n" +
                "        $group:{\n" +
                "            _id:{},\n" +
                "            exams:{$push:{name:\"$_id.examName\",quantity:\"$quantity\"}}\n" +
                "        }\n" +
                "    }"));
    }

    private void addGroupExamInapplicability(){
        pipeline.add(parseQuery(" {\n" +
                "        $group:{\n" +
                "            _id:{},\n" +
                "            participantExams:{\n" +
                "                $push:{\n" +
                "                    \"name\":\"$name\",\n" +
                "                    \"quantity\":\"$quantity\",\n" +
                "                    \"doesNotApply\":{$arrayElemAt: [\"$examInapplicability\",0]}\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }"));
    }

    private void addAddFieldsAllExams(List<String> examName){
        pipeline.add(new Document("$addFields", new Document("allExams", examName)));
    }

    private void addUnwindAllExams(){
        pipeline.add(parseQuery("{$unwind:\"$allExams\"}"));
    }

    private void addAddFieldsFilter(){
        pipeline.add(parseQuery("{\n" +
                "        $addFields:{\n" +
                "            \n" +
                "            examFound:{\n" +
                "                $arrayElemAt:[\n" +
                "                    {$filter:{\n" +
                "                        input:\"$exams\",\n" +
                "                        as:\"exam\",\n" +
                "                        cond:{\n" +
                "                            $eq: [\n" +
                "                                    \"$$exam.name\",\n" +
                "                                    \"$allExams\"\n" +
                "                                ]\n" +
                "                        }\n" +
                "                    }},0\n" +
                "                ]\n" +
                "            }\n" +
                "        }\n" +
                "    }"));
    }

    private void addProjectAllExams(){
        pipeline.add(parseQuery("{\n" +
                "        $project:{\n" +
                "            name: \"$allExams\",\n" +
                "            quantity:{$cond:[{$ifNull:[\"$examFound\",false]},\"$examFound.quantity\",0]}\n" +
                "        }\n" +
                "    }"));
    }

    private void addLookupExamInapplicability(Long rn){
        pipeline.add(parseQuery("{\n" +
                "        $lookup:{\n" +
                "            from:\"exam_inapplicability\",\n" +
                "            let:{name:\"$name\",rn:\"$recruitmentNumber\"},\n" +
                "            pipeline:[\n" +
                "                {\n" +
                "                    $match:{\n" +
                "                        $expr:{\n" +
                "                            $and:[\n" +
                "                                {$eq:[\"$name\",\"$$name\"]},\n" +
                "                                {$eq:[\"$recruitmentNumber\","+rn+"]}\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    $project:{\n" +
                "                            \"_id\":0,\n" +
                "                            \"name\":0,\n"+
                "                            \"recruitmentNumber\":0\n"+
                "                        }  \n" +
                "                }\n" +
                "                ],\n" +
                "            as:\"examInapplicability\"\n" +
                "        }\n" +
                "    }"));
    }

    private void addProjectObservation(){
        pipeline.add(parseQuery("{\n" +
                "        $project:{\n" +
                "            name: \"$allExams\",\n" +
                "            quantity:{$toInt:\"0\"},\n" +
                "            examInapplicability:[{$cond:[{$eq:[\"$name\",\"$allExams\"]},{observation:\"$observation\"},null]}]\n" +
                "        }\n" +
                "    }"));
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }
}
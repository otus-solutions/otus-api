package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ParticipantExamReportDto;
import org.ccem.otus.persistence.ExamMonitoringDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class ExamMonitoringDaoBean extends MongoGenericDao<Document> implements ExamMonitoringDao {

    @Inject
    private LaboratoryConfigurationDao laboratoryConfigurationDao;

    private static final String COLLECTION_NAME = "exam_result";

    public ExamMonitoringDaoBean() {
        super(COLLECTION_NAME, Document.class);}

    @Override
    public ArrayList<ParticipantExamReportDto> getParticipantExams(Long rn) {

        ArrayList<Bson> pipeline = new ArrayList<Bson>();
        pipeline.add(parseQuery("{$match:{\"recruitmentNumber\":"+rn+"}}"));
        pipeline.add(parseQuery("{\n" +
                "      $group:{\n" +
                "          _id:{examName:\"$examName\",examId:\"$examId\"}\n" +
                "      }  \n" +
                "    }"));
        pipeline.add(parseQuery(" {\n" +
                "        $group:{\n" +
                "            _id:{examName:\"$_id.examName\"},\n" +
                "            quantity:{$sum:1}\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("  {\n" +
                "        $group:{\n" +
                "            _id:{},\n" +
                "            exams:{$push:{name:\"$_id.examName\",quantity:\"$quantity\"}}\n" +
                "        }\n" +
                "    }"));
        pipeline.add(new Document("$addFields", new Document("allExams", laboratoryConfigurationDao.getExamName())));
        pipeline.add(parseQuery("{$unwind:\"$allExams\"}"));
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
        pipeline.add(parseQuery("{\n" +
                "        $project:{\n" +
                "            name: \"$allExams\",\n" +
                "            quantity:{$cond:[{$ifNull:[\"$examFound\",false]},\"$examFound.quantity\",0]}\n" +
                "        }\n" +
                "    }"));
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
        pipeline.add(parseQuery("{\n" +
                "        $project:{\n" +
                "            \"name\":1,\n" +
                "            \"quantity\":1,\n" +
                "            \"doesNotApply\":{$arrayElemAt: [\"$examInapplicability\",0]}\n" +
                "        }\n" +
                "    }"));

        MongoCursor<Document> resultsDocument = super.aggregate(pipeline).iterator();

        ArrayList<ParticipantExamReportDto> dtos = new ArrayList<>();

        try {
            while (resultsDocument.hasNext()) {
                dtos.add(ParticipantExamReportDto.deserialize(resultsDocument.next().toJson()));
            }
        } finally {
            resultsDocument.close();
        }

        return dtos;
    }

    private Bson parseQuery(String stage) {
        return new GsonBuilder().create().fromJson(stage, Document.class);
    }

}


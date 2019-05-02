package br.org.otus.laboratory.configuration.aliquot;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class AliquotConfigurationQueryBuilder {

    private ArrayList<Bson> pipeline;

    public ArrayList<Bson> getCenterAliquotsByCQQuery(String Center, String CQ){
        getCenterFilter(Center);
        pipeline.add(parseQuery("{\n" +
                "        $match:{\n" +
                "            \"aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.name\":"+CQ+"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $group:{\n" +
                "            _id:{},\n" +
                "            centerAliquots:{$addToSet:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots.name\"}\n" +
                "        }\n" +
                "    }"));
        return null;
    }

    public ArrayList<Bson> getCenterAliquotsQuery(String Center){
        getCenterFilter(Center);
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $group:{\n" +
                "            _id:{},\n" +
                "            centerAliquots:{$addToSet:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots.name\"}\n" +
                "        }\n" +
                "    }"));
        return pipeline;
    }

    private void getCenterFilter(String Center) {
        ArrayList<Bson> pipeline = new ArrayList<>();
        pipeline.add(parseQuery("{\n" +
                "        $match:{\n" +
                "            \"objectType\" : \"LaboratoryConfiguration\"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $project:{\n" +
                "            \"aliquotConfiguration.aliquotCenterDescriptors\":1\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors\"\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $match:{\n" +
                "            \"aliquotConfiguration.aliquotCenterDescriptors.name\":"+Center+"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(parseQuery("{\n" +
                "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors\"\n" +
                "    }"));
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }
}

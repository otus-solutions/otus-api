package br.org.otus.laboratory.configuration.aliquot;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class AliquotConfigurationQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ArrayList<Bson> getCenterAliquotsByCQQuery(String center, String CQ) {
    pipeline = new ArrayList<>();
    buildMatchCenterStages(center);
    addUnwindOnAliquotGroupDescriptorsStage();
    addMatchOnCQStage(CQ);
    addUnwindOnAliquotMomentDescriptorsStage();
    addUnwindOnAliquotTypesDescriptorsStage();
    addUnwindOnAliquotsStage();
    addBuildCenterAliquotsStage();
    return pipeline;
  }

  public ArrayList<Bson> getCenterAliquotsQuery(String center) {
    pipeline = new ArrayList<>();
    buildMatchCenterStages(center);
    addUnwindOnAliquotGroupDescriptorsStage();
    addUnwindOnAliquotMomentDescriptorsStage();
    addUnwindOnAliquotTypesDescriptorsStage();
    addUnwindOnAliquotsStage();
    addBuildCenterAliquotsStage();
    return pipeline;
  }

  private void buildMatchCenterStages(String center) {
    addMatchLaboratoryConfigurationStage();
    addAliquotCenterDescriptorsProjectStage();
    addUnwindOnAliquotCenterDescriptorsProjectStage();
    addMatchOnCenterStage(center);
  }

  private void addMatchLaboratoryConfigurationStage() {
    pipeline.add(parseQuery("{\n" +
      "        $match:{\n" +
      "            \"objectType\" : \"LaboratoryConfiguration\"\n" +
      "        }\n" +
      "    }"));
  }

  private void addAliquotCenterDescriptorsProjectStage() {
    pipeline.add(parseQuery("{\n" +
      "        $project:{\n" +
      "            \"aliquotConfiguration.aliquotCenterDescriptors\":1\n" +
      "        }\n" +
      "    }"));
  }

  private void addUnwindOnAliquotCenterDescriptorsProjectStage() {
    pipeline.add(parseQuery("{\n" +
      "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors\"\n" +
      "    }"));
  }

  private void addMatchOnCenterStage(String center) {
    pipeline.add(parseQuery("{\n" +
      "        $match:{\n" +
      "            \"aliquotConfiguration.aliquotCenterDescriptors.name\":" + center + "\n" +
      "        }\n" +
      "    }"));
  }

  private void addUnwindOnAliquotGroupDescriptorsStage() {
    pipeline.add(parseQuery("{\n" +
      "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors\"\n" +
      "    }"));
  }

  private void addMatchOnCQStage(String CQ) {
    pipeline.add(parseQuery("{\n" +
      "        $match:{\n" +
      "            \"aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.name\":" + CQ + "\n" +
      "        }\n" +
      "    }"));
  }

  private void addUnwindOnAliquotMomentDescriptorsStage() {
    pipeline.add(parseQuery("{\n" +
      "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors\"\n" +
      "    }"));
  }

  private void addUnwindOnAliquotTypesDescriptorsStage() {
    pipeline.add(parseQuery("{\n" +
      "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors\"\n" +
      "    }"));
  }

  private void addUnwindOnAliquotsStage() {
    pipeline.add(parseQuery("{\n" +
      "        $unwind:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots\"\n" +
      "    }"));
  }

  private void addBuildCenterAliquotsStage() {
    pipeline.add(parseQuery("{\n" +
      "        $group:{\n" +
      "            _id:{},\n" +
      "            centerAliquots:{$addToSet:\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots.name\"}\n" +
      "        }\n" +
      "    }"));
  }

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }
}

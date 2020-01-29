package br.org.otus.laboratory.configuration.aliquot;

import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class AliquotConfigurationQueryBuilderTest {

  @Test
  public void method_getCenterAliquotsByCQQuery_should_return_query() {
    AliquotConfigurationQueryBuilder aliquotConfigurationQueryBuilder = new AliquotConfigurationQueryBuilder();
    String centerAliquotsByCQQuery = "[{\"$match\":{\"objectType\":\"LaboratoryConfiguration\"}},{\"$project\":{\"aliquotConfiguration.aliquotCenterDescriptors\":1.0}},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors\"},{\"$match\":{\"aliquotConfiguration.aliquotCenterDescriptors.name\":\"RS\"}},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors\"},{\"$match\":{\"aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.name\":\"DEFAULT\"}},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors\"},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors\"},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots\"},{\"$group\":{\"_id\":{},\"centerAliquots\":{\"$addToSet\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots.name\"}}}]";
    assertEquals(centerAliquotsByCQQuery, new GsonBuilder().create().toJson(aliquotConfigurationQueryBuilder.getCenterAliquotsByCQQuery("RS", "DEFAULT")));
  }

  @Test
  public void method_getCenterAliquotsQuery_should_return_query() {
    AliquotConfigurationQueryBuilder aliquotConfigurationQueryBuilder = new AliquotConfigurationQueryBuilder();
    String centerAliquotsByCQQuery = "[{\"$match\":{\"objectType\":\"LaboratoryConfiguration\"}},{\"$project\":{\"aliquotConfiguration.aliquotCenterDescriptors\":1.0}},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors\"},{\"$match\":{\"aliquotConfiguration.aliquotCenterDescriptors.name\":\"RS\"}},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors\"},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors\"},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors\"},{\"$unwind\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots\"},{\"$group\":{\"_id\":{},\"centerAliquots\":{\"$addToSet\":\"$aliquotConfiguration.aliquotCenterDescriptors.aliquotGroupDescriptors.aliquotMomentDescriptors.aliquotTypesDescriptors.aliquots.name\"}}}]";
    assertEquals(centerAliquotsByCQQuery, new GsonBuilder().create().toJson(aliquotConfigurationQueryBuilder.getCenterAliquotsQuery("RS")));
  }
}

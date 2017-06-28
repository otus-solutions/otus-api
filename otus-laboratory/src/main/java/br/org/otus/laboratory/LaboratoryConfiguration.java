package br.org.otus.laboratory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import br.org.otus.laboratory.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.collect.group.CollectGroupConfiguration;
import br.org.otus.laboratory.collect.group.CollectGroupConfigurationAdapter;
import br.org.otus.laboratory.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.collect.group.CollectGroupDescriptorAdapter;
import br.org.otus.laboratory.collect.moment.CollectMomentConfiguration;
import br.org.otus.laboratory.collect.moment.CollectMomentConfigurationAdapter;
import br.org.otus.laboratory.collect.tube.TubeConfiguration;
import br.org.otus.laboratory.collect.tube.TubeConfigurationAdapter;
import br.org.otus.laboratory.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.label.LabelPrintConfiguration;
import br.org.otus.laboratory.label.LabelPrintConfigurationAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class LaboratoryConfiguration {

	@SerializedName("_id")
	private ObjectId id;

	private CodeConfiguration codeConfiguration;
	private TubeConfiguration tubeConfiguration;
	private AliquotConfiguration aliquotConfiguration;
	private CollectMomentConfiguration collectMomentConfiguration;
	private CollectGroupConfiguration collectGroupConfiguration;
	private LabelPrintConfiguration labelPrintConfiguration;
	private List<MetadataConfiguration> metadataConfiguration;

	public LaboratoryConfiguration() {
		codeConfiguration = new CodeConfiguration();
		tubeConfiguration = new TubeConfiguration(new HashSet<>());
		collectMomentConfiguration = new CollectMomentConfiguration(new HashSet<>());
		collectGroupConfiguration = new CollectGroupConfiguration(new HashSet<>());
		aliquotConfiguration = new AliquotConfiguration(new ArrayList<>());
		labelPrintConfiguration = new LabelPrintConfiguration(new HashMap<>());
	}

	public ObjectId getId() {
		return this.id;
	}

	public CodeConfiguration getCodeConfiguration() {
		return this.codeConfiguration;
	}

	public TubeConfiguration getTubeConfiguration() {
		return this.tubeConfiguration;
	}

	public AliquotConfiguration getAliquotConfiguration() {
		return this.aliquotConfiguration;
	}

	public CollectMomentConfiguration getCollectMomentConfiguration() {
		return this.collectMomentConfiguration;
	}

	public CollectGroupConfiguration getCollectGroupConfiguration() {
		return this.collectGroupConfiguration;
	}

	public LabelPrintConfiguration getLabelPrintConfiguration() {
		return this.labelPrintConfiguration;
	}

	public Integer allocNextCodeList(TubeSeed seed) {
		return codeConfiguration.allocCodeAndGetStartingPoint(seed.getTubeCount());
	}

	public List<String> generateNewCodeList(TubeSeed seed, Integer startingPoint) {
		return codeConfiguration.generateCodeList(seed, startingPoint);
	}

	public List<MetadataConfiguration> getMetadataConfiguration() {
		return metadataConfiguration;
	}

	public static String serialize(LaboratoryConfiguration laboratory) {
		Gson builder = LaboratoryConfiguration.getGsonBuilder();
		return builder.toJson(laboratory);
	}

	public static LaboratoryConfiguration deserialize(String laboratoryJson) {
		Gson builder = LaboratoryConfiguration.getGsonBuilder();
		return builder.fromJson(laboratoryJson, LaboratoryConfiguration.class);
	}

	public static Gson getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
		builder.registerTypeAdapter(TubeConfiguration.class, new TubeConfigurationAdapter());
		builder.registerTypeAdapter(CollectMomentConfiguration.class, new CollectMomentConfigurationAdapter());
		builder.registerTypeAdapter(CollectGroupConfiguration.class, new CollectGroupConfigurationAdapter());
		builder.registerTypeAdapter(CollectGroupDescriptor.class, new CollectGroupDescriptorAdapter());
		builder.registerTypeAdapter(LabelPrintConfiguration.class, new LabelPrintConfigurationAdapter());
		return builder.create();
	}

}

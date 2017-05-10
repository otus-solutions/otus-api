package br.org.otus.laboratory.collect.group;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.collect.tube.TubeDefinition;

public class CollectGroupConfigurationAdapterTest {
	
	private CollectGroupConfigurationAdapter adapter;
	private CollectGroupConfiguration groupConfiguration;
	private HashSet<TubeDefinition> tubeSets;
	private HashSet<CollectGroupDescriptor> groupDescriptors;
	private Gson gson;

	@Before
	public void setUp() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(CollectGroupConfiguration.class, new CollectGroupConfigurationAdapter());
		gson = builder.create();
		
		tubeSets = new HashSet<>();
		tubeSets.add(new TubeDefinition(1, "FLOURIDE", "POST_OVERLOAD"));

		groupDescriptors = new HashSet<>();
		groupDescriptors.add(new CollectGroupDescriptor("QC1", "QUALITY_CONTROL", tubeSets));

		groupConfiguration = (new CollectGroupConfiguration(groupDescriptors));

	}

	@Test
	public void method_should_return_JsonElement_serializable() {
		//System.out.println(gson.toJson(groupConfiguration));
		//System.out.println(new Gson().toJson(groupConfiguration));
		

	}

}

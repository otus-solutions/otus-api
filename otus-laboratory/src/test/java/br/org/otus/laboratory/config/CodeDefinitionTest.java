package br.org.otus.laboratory.config;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.org.otus.laboratory.CodeConfiguration;

@Ignore
public class CodeDefinitionTest {

	private CodeConfiguration codeDefinitions;
	private String json = "";
	
	@Before
	public void setup(){
		String json = "" 
				+ "{\"tube\": 1,"
				+ "\"pallet\": 2," 
				+ "\"cryotube\": 3,"
				+ "\"lastInsertion\":0}" ;

		codeDefinitions = new Gson().fromJson(json, CodeConfiguration.class);

	}
	
	@Test
	public void test_me(){
		System.out.println(codeDefinitions.getLastInsertion());
	}
}

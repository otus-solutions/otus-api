package org.ccem.otus.utils;

import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class LongAdapterTest {

	private static final String NUMBER_LONG = "$numberLong";
	private static final Long VALUE1 = 10L;
	private static final Long VALUE2 = 123456789101112L;

	private LongAdapter longAdapter;

	@Mock
	private Type type;
	@Mock
	private JsonDeserializationContext context;

	@Before
	public void setup() {
		this.longAdapter = new LongAdapter();
	}

	@Test
	public void method_deserialize_should_return_type_long() {
		JsonElement json = new JsonPrimitive(VALUE1);

		Assert.assertTrue(this.longAdapter.deserialize(json, type, context) instanceof Long);
	}

	@Test
	public void method_deserialize_should_return_expected_values() {
		JsonElement json = new JsonPrimitive(VALUE1);

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(NUMBER_LONG, VALUE2);

		Assert.assertEquals(VALUE1, this.longAdapter.deserialize(json, type, context));
		Assert.assertEquals(VALUE2, this.longAdapter.deserialize(jsonObject, type, context));
	}

}

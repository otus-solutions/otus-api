package org.ccem.otus.service;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParseQueryTest {

    private static ParseQuery builder = null;
    private static String query = "{\"$match\":{\"recruitmentNumber\":{\"$in\":\"[123456]\"}}}";

    @Before
    public void setUp() {
        builder = new  ParseQuery();
    }

    @Test
    public void toDocumentMethod_should_check_return() {
        assertEquals(builder.toDocument(query), new GsonBuilder().create().fromJson(query, Document.class));
    }
}
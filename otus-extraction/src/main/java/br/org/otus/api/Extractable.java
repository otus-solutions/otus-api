package br.org.otus.api;

import java.util.LinkedHashSet;
import java.util.List;

public interface Extractable {

	LinkedHashSet<String> getHeaders();

	List<List<Object>> getValues();
}

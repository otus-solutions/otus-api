package br.org.otus.api;

import java.util.List;
import java.util.Set;

public interface Extractable {

	Set<String> getHeaders();

	List<List<Object>> getValues();
}

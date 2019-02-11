package br.org.otus.api;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface Extractable {

  List<String> getHeaders();

  List<List<Object>> getValues() throws DataNotFoundException;

}

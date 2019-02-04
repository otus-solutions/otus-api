package br.org.otus.laboratory.extraction;

import java.util.LinkedHashSet;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.api.Extractable;

public class LaboratoryExtraction implements Extractable {

  @Override
  public LinkedHashSet<String> getHeaders() {
    return null;
  }

  @Override
  public List<List<Object>> getValues() throws DataNotFoundException {
    return null;
  }

}

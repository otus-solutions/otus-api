package org.ccem.otus.service.extraction;

import java.util.List;

public interface Extractable {

	List<String> getHeaders();
	List<Object> getValues();
}

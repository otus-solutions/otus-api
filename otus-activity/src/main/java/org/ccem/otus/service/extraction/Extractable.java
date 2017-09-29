package org.ccem.otus.service.extraction;

import java.util.List;
import java.util.Set;

public interface Extractable {

	Set<String> getHeaders();
	List<Object> getValues();
}

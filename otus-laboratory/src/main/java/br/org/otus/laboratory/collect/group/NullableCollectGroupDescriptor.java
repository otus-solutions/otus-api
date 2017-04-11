package br.org.otus.laboratory.collect.group;

import java.util.HashSet;

public class NullableCollectGroupDescriptor extends CollectGroupDescriptor {

	private static final String DEFAULT = "DEFAULT";

	public NullableCollectGroupDescriptor() {
		super(DEFAULT, DEFAULT, new HashSet<>());
	}

	@Override
	public String getName() {
		return DEFAULT;
	}

	@Override
	public String getType() {
		return DEFAULT;
	}

}

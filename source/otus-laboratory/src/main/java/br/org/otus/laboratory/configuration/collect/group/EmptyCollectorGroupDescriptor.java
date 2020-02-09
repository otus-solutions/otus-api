package br.org.otus.laboratory.configuration.collect.group;

import java.util.HashSet;

public class EmptyCollectorGroupDescriptor extends CollectGroupDescriptor {
  public EmptyCollectorGroupDescriptor(String name) {
    super(name, "QUALITY_CONTROL", new HashSet<>());
  }
}

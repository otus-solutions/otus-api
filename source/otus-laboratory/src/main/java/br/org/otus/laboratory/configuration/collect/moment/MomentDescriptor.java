package br.org.otus.laboratory.configuration.collect.moment;

public class MomentDescriptor {

  private String name;
  private String label;

  public MomentDescriptor(String name, String label) {
    this.name = name;
    this.label = label;
  }

  public String getName() {
    return name;
  }

  public String getLabel() {
    return label;
  }

}

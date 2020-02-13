package br.org.otus.laboratory.unattached.model;

import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.unattached.enums.UnattachedLaboratoryActions;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UnattachedLaboratory {
  private String objectType;
  private String collectGroupName;
  private String fieldCenterAcronym;
  private List<Tube> tubes;
  private Integer identification;
  private ArrayList<UserAction> actionHistory;
  private Boolean availableToAttache;

  public UnattachedLaboratory(Integer unattachedLaboratoryLastInsertion, String fieldCenterAcronym, String collectGroupName, List<Tube> tubes) {
    this.objectType = "UnattachedLaboratory";
    this.availableToAttache = true;
    this.actionHistory = new ArrayList<>();
    this.fieldCenterAcronym = fieldCenterAcronym;
    this.collectGroupName = collectGroupName;
    this.tubes = tubes;
    this.identification = unattachedLaboratoryLastInsertion;
  }

  public void addUserHistory(String userEmail, UnattachedLaboratoryActions action) {
    this.actionHistory.add(new UserAction(action, userEmail));
  }

  public void disable() {
    this.availableToAttache = false;
    this.tubes = null;
  }

  public String getObjectType() {
    return objectType;
  }

  public UserAction getLastHistory() {
    return this.actionHistory.get(this.actionHistory.size() - 1);
  }

  public String getCollectGroupName() {
    return collectGroupName;
  }

  public List<Tube> getTubes() {
    return tubes;
  }

  public static String serialize(UnattachedLaboratory laboratory) {
    GsonBuilder builder = UnattachedLaboratory.getGsonBuilder();
    return builder.create().toJson(laboratory);
  }

  public static UnattachedLaboratory deserialize(String laboratoryJson) {
    GsonBuilder builder = UnattachedLaboratory.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(laboratoryJson, UnattachedLaboratory.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());

    return builder;
  }

  public String getFieldCenterAcronym() {
    return fieldCenterAcronym;
  }

  public Integer getIdentification() {
    return identification;
  }

  public Boolean getAvailableToAttache() {
    return availableToAttache;
  }
}

package unattached;


import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.exam.Exam;
import br.org.otus.laboratory.participant.tube.Tube;
import com.google.gson.GsonBuilder;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UnattachedLaboratory{
  private String objectType;
  private String collectGroupName;
  private String fieldCenterAcronym;
  private List<Tube> tubes;
  private Integer identification;

  public UnattachedLaboratory(Integer unattachedLaboratoryLastInsertion, String fieldCenterAcronym, String collectGroupName, List<Tube> tubes) {
    this.objectType = "UnattachedLaboratory";
    this.fieldCenterAcronym = fieldCenterAcronym;
    this.collectGroupName = collectGroupName;
    this.tubes = tubes;
    this.identification = unattachedLaboratoryLastInsertion;
  }

  public String getObjectType() {
    return objectType;
  }

  public String getCollectGroupName() {
    return collectGroupName;
  }

  public List<Tube> getTubes() {
    return tubes;
  }

  public List<SimpleAliquot> getAliquotsList() {
    ArrayList<SimpleAliquot> aliquotsList = new ArrayList<SimpleAliquot>();
    for (Tube tube : tubes) {
      aliquotsList.addAll(tube.getAliquots());
    }

    return aliquotsList;
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
    builder.serializeNulls();

    return builder;
  }
}

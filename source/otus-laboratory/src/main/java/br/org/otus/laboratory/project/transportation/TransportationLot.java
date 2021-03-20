package br.org.otus.laboratory.project.transportation;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportationLot {

  private ObjectId _id;
  private String objectType;
  private String code;
  private LocalDateTime shipmentDate;
  private LocalDateTime processingDate;
  private String operator;
  private ArrayList<Aliquot> aliquotList;
  private ArrayList<Tube> tubeList;
  private ArrayList<AliquotInfo> aliquotsInfo;
  private ArrayList<TubeInfo> tubesInfo;
  private ObjectId originLocationPoint;
  private ObjectId destinationLocationPoint;
  private FieldCenter fieldCenter;

  public TransportationLot() {
    objectType = "TransportationLot";
  }

  public String getObjectType() {
    return objectType;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public List<Aliquot> getAliquotList() {
    return aliquotList;
  }

  public LocalDateTime getShipmentDate() {
    return shipmentDate;
  }

  public LocalDateTime getProcessingDate() {
    return processingDate;
  }

  public String getOperator() {
    return operator;
  }

  public ArrayList<String> getAliquotCodeList() {
    ArrayList<String> codeList = new ArrayList<>();
    if (aliquotList != null) {
      aliquotList.forEach(aliquot -> {
        codeList.add(aliquot.getCode());
      });
    }
    return codeList;
  }

  public ArrayList<String> getTubeCodeList() {
    ArrayList<String> codeList = new ArrayList<>();
    if (tubeList != null) {
      tubeList.forEach(tube -> {
        codeList.add(tube.getCode());
      });
    }
    return codeList;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public static String serialize(TransportationLot transportationLot) {
    Gson builder = getGsonBuilder().create();
    return builder.toJson(transportationLot);
  }

  public static TransportationLot deserialize(String transportationLot) {
    return getGsonBuilder().create().fromJson(transportationLot, TransportationLot.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.serializeNulls();

    return builder;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    TransportationLot that = (TransportationLot) obj;

    return code.equals(that.code);
  }

  @Override
  public int hashCode() {
    return code.hashCode();
  }

  public FieldCenter getCenter() {
    return fieldCenter;
  }

  public void setCenter(FieldCenter center) {
    this.fieldCenter = center;
  }

  public ObjectId getLotId() {
    return _id;
  }

  public ObjectId getOriginLocationPoint() {
    return originLocationPoint;
  }

  public ObjectId getDestinationLocationPoint() {
    return destinationLocationPoint;
  }

  public void setLotId(ObjectId _id) {
    this._id = _id;
  }

  public void setAliquotList(ArrayList<Aliquot> aliquotList) {
    this.aliquotList = aliquotList;
  }

  public void setTubeList(ArrayList<Tube> tubeList) {
    this.tubeList = tubeList;
  }
}

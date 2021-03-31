package org.ccem.otus.participant.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class ParticipantQueryBuilder {

  private ArrayList<Bson> pipeline;

  public ParticipantQueryBuilder() {
    this.pipeline = new ArrayList<>();
  }

  public List<Bson> build() {
    return this.pipeline;
  }

  public ParticipantQueryBuilder lookupParticipantContact() {
    pipeline.add(this.parseQuery("{" +
      "    $lookup:{" +
      "      from:\"participant_contact\"," +
      "      localField:\"recruitmentNumber\"," +
      "      foreignField:\"recruitmentNumber\"," +
      "      as:\"CT\"" +
      "    }" +
      "  }"));
    return this;
  }

  public ParticipantQueryBuilder projectionStructureParticipantContact() {
    pipeline.add(this.parseQuery("{" +
      "        $project:{" +
      "            numero_de_recrutamento:{ $ifNull: [ \"$recruitmentNumber\", \"\" ] }," +
      "            nome_completo:{ $ifNull: [ \"$name\", \"\" ] }," +
      "            sexo:{ $ifNull: [ \"$sex\", \"\" ] }," +
      "            centro:{ $ifNull: [ \"$fieldCenter.acronym\", \"\" ] }," +
      "            data_de_nascimento:{ $ifNull: [ \"$birthdate.value\", \"\" ] }," +
      "            email_de_login:{ $ifNull: [ \"$email\", \"\" ] }," +
      "            registrado_por:\"$registeredBy\"," +
      "            telefone_principal:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.main.value.content\",0]}, \"\" ] }," +
      "            telefone_principal_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.main.observation\",0]}, \"\" ] }," +
      "            segundo_telefone:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.second.value.content\",0]}, \"\" ] }," +
      "            segundo_telefone_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.second.value.content\",0]}, \"\" ] }," +
      "            terceiro_telefone:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.third.value.content\",0]}, \"\" ] }," +
      "            terceiro_telefone_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.third.value.content\",0]}, \"\" ] }," +
      "            quarto_telefone:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.fourth.value.content\",0]}, \"\" ] }," +
      "            quarto_telefone_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.fourth.value.content\",0]}, \"\" ] }," +
      "            quinto_telefone:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.fifth.value.content\",0]}, \"\" ] }," +
      "            quinto_telefone_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.phoneNumber.fifth.value.content\",0]}, \"\" ] }," +
      "            email_principal:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.main.value.content\",0]}, \"\" ] }," +
      "            email_principal_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.main.value.content\",0]}, \"\" ] }," +
      "            segundo_email:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.second.value.content\",0]}, \"\" ] }," +
      "            segundo_email_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.second.value.content\",0]}, \"\" ] }," +
      "            terceiro_email:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.third.value.content\",0]}, \"\" ] }," +
      "            terceiro_email_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.third.value.content\",0]}, \"\" ] }," +
      "            quarto_email:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.fourth.value.content\",0]}, \"\" ] }," +
      "            quarto_email_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.fourth.value.content\",0]}, \"\" ] }," +
      "            quinto_email:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.fifth.value.content\",0]}, \"\" ] }," +
      "            quinto_email_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.email.fifth.value.content\",0]}, \"\" ] }," +
      "            endereco_principal_cep:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.postalCode\",0]}, \"\" ] }," +
      "            endereco_principal_rua:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.street\",0]}, \"\" ] }," +
      "            endereco_principal_numero:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.streetNumber\",0]}, 0 ] }," +
      "            endereco_principal_complemento:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.complements\",0]}, \"\" ] }," +
      "            endereco_principal_bairro:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.neighbourhood\",0]}, \"\" ] }," +
      "            endereco_principal_cidade:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.city\",0]}, \"\" ] }," +
      "            endereco_principal_uf:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.state\",0]}, \"\" ] }," +
      "            endereco_principal_pais:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.country\",0]}, \"\" ] }," +
      "            endereco_principal_setor_censitario:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.value.census\",0]}, \"\" ] }," +
      "            endereco_principal_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.main.observation\",0]}, \"\" ] }," +
      "            segundo_endereco_cep:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.postalCode\",0]}, \"\" ] }," +
      "            segundo_endereco_rua:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.street\",0]}, \"\" ] }," +
      "            segundo_endereco_numero:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.streetNumber\",0]}, 0 ] }," +
      "            segundo_endereco_complemento:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.complements\",0]}, \"\" ] }," +
      "            segundo_endereco_bairro:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.neighbourhood\",0]}, \"\" ] }," +
      "            segundo_endereco_cidade:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.city\",0]}, \"\" ] }," +
      "            segundo_endereco_uf:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.state\",0]}, \"\" ] }," +
      "            segundo_endereco_pais:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.country\",0]}, \"\" ] }," +
      "            segundo_endereco_setor_censitario:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.value.census\",0]}, \"\" ] }," +
      "            segundo_endereco_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.second.observation\",0]}, \"\" ] }," +
      "            terceiro_endereco_cep:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.postalCode\",0]}, \"\" ] }," +
      "            terceiro_endereco_rua:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.street\",0]}, \"\" ] }," +
      "            terceiro_endereco_numero:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.streetNumber\",0]}, 0 ] }," +
      "            terceiro_endereco_complemento:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.complements\",0]}, \"\" ] }," +
      "            terceiro_endereco_bairro:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.neighbourhood\",0]}, \"\" ] }," +
      "            terceiro_endereco_cidade:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.city\",0]}, \"\" ] }," +
      "            terceiro_endereco_uf:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.state\",0]}, \"\" ] }," +
      "            terceiro_endereco_pais:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.country\",0]}, \"\" ] }," +
      "            terceiro_endereco_setor_censitario:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.value.census\",0]}, \"\" ] }," +
      "            terceiro_endereco_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.third.observation\",0]}, \"\" ] }," +
      "            quarto_endereco_cep:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.postalCode\",0]}, \"\" ] }," +
      "            quarto_endereco_rua:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.street\",0]}, \"\" ] }," +
      "            quarto_endereco_numero:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.streetNumber\",0]}, 0 ] }," +
      "            quarto_endereco_complemento:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.complements\",0]}, \"\" ] }," +
      "            quarto_endereco_bairro:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.neighbourhood\",0]}, \"\" ] }," +
      "            quarto_endereco_cidade:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.city\",0]}, \"\" ] }," +
      "            quarto_endereco_uf:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.state\",0]}, \"\" ] }," +
      "            quarto_endereco_pais:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.country\",0]}, \"\" ] }," +
      "            quarto_endereco_setor_censitario:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.value.census\",0]}, \"\" ] }," +
      "            quarto_endereco_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fourth.observation\",0]}, \"\" ] }," +
      "            quinto_endereco_cep:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.postalCode\",0]}, \"\" ] }," +
      "            quinto_endereco_rua:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.street\",0]}, \"\" ] }," +
      "            quinto_endereco_numero:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.streetNumber\",0]}, 0 ] }," +
      "            quinto_endereco_complemento:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.complements\",0]}, \"\" ] }," +
      "            quinto_endereco_bairro:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.neighbourhood\",0]}, \"\" ] }," +
      "            quinto_endereco_cidade:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.city\",0]}, \"\" ] }," +
      "            quinto_endereco_uf:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.state\",0]}, \"\" ] }," +
      "            quinto_endereco_pais:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.country\",0]}, \"\" ] }," +
      "            quinto_endereco_setor_censitario:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.value.census\",0]}, \"\" ] }," +
      "            quinto_endereco_observacao:{ $ifNull: [ {$arrayElemAt:[\"$CT.address.fifth.observation\",0]}, \"\" ] }" +
      "        }" +
      "    }"));

    return this;
  }

  public ParticipantQueryBuilder groupParticipantContactInLines() {
    pipeline.add(this.parseQuery("{\n" +
            "  \"$group\": {\n" +
            "    \"_id\": \"$numero_de_recrutamento\",\n" +
            "    \"results\":{\n" +
            "      \"$push\":{\n" +
            "        \"recruitmentNumber\":\"$numero_de_recrutamento\",\n" +
            "        \"name\":\"$nome_completo\",\n" +
            "        \"sex\":\"$sexo\",\n" +
            "        \"center\":\"$centro\",\n" +
            "        \"birthdate\":\"$data_de_nascimento\",\n" +
            "        \"email\":\"$email_de_login\",\n" +
            "        \"registeredBy\":\"$registrado_por\",\n" +
            "        \"mainPhone\":\"$telefone_principal\",\n" +
            "        \"mainPhoneObservation\":\"$telefone_principal_observacao\",\n" +
            "        \"secondPhone\":\"$segundo_telefone\",\n" +
            "        \"secondPhoneObservation\":\"$segundo_telefone_observacao\",\n" +
            "        \"thirdPhone\":\"$terceiro_telefone\",\n" +
            "        \"thirdPhoneObservation\":\"$terceiro_telefone_observacao\",\n" +
            "        \"fourthPhone\":\"$quarto_telefone\",\n" +
            "        \"fourthPhoneObservation\":\"$quarto_telefone_observacao\",\n" +
            "        \"fifthPhone\":\"$quinto_telefone\",\n" +
            "        \"fifthPhoneObservation\":\"$quinto_telefone_observacao\",\n" +
            "        \"mainEmail\":\"$email_principal\",\n" +
            "        \"mainEmailObservation\":\"$email_principal_observacao\",\n" +
            "        \"secondEmail\":\"$segundo_email\",\n" +
            "        \"secondEmailObservation\":\"$segundo_email_observacao\",\n" +
            "        \"thirdEmail\":\"$terceiro_email\",\n" +
            "        \"thirdEmailObservation\":\"$terceiro_email_observacao\",\n" +
            "        \"fourthEmail\":\"$quarto_email\",\n" +
            "        \"fourthEmailObservation\":\"$quarto_email_observacao\",\n" +
            "        \"fifthEmail\":\"$quinto_email\",\n" +
            "        \"fifthEmailObservation\":\"$quinto_email_observacao\",\n" +
            "        \"mainAddressCep\":\"$endereco_principal_cep\",\n" +
            "        \"mainAddressStreet\":\"$endereco_principal_rua\",\n" +
            "        \"mainAddressNumber\":\"$endereco_principal_numero\",\n" +
            "        \"mainAddressComplements\":\"$endereco_principal_complemento\",\n" +
            "        \"mainAddressNeighbourhood\": \"$endereco_principal_bairro\",\n" +
            "        \"mainAddressCity\": \"$endereco_principal_cidade\",\n" +
            "        \"mainAddressUf\": \"$endereco_principal_uf\",\n" +
            "        \"mainAddressCountry\": \"$endereco_principal_pais\",\n" +
            "        \"mainAddressCensus\": \"$endereco_principal_setor_censitario\",\n" +
            "        \"mainAddressObservation\": \"$endereco_principal_observacao\",\n" +
            "        \"secondAddressCep\": \"$segundo_endereco_cep\",\n" +
            "        \"secondAddressStreet\": \"$segundo_endereco_rua\",\n" +
            "        \"secondAddressNumber\": \"$segundo_endereco_numero\",\n" +
            "        \"secondAddressComplements\": \"$segundo_endereco_complemento\",\n" +
            "        \"secondAddressNeighbourhood\": \"$segundo_endereco_bairro\",\n" +
            "        \"secondAddressCity\": \"$segundo_endereco_cidade\",\n" +
            "        \"secondAddressUf\": \"$segundo_endereco_uf\",\n" +
            "        \"secondAddressCountry\": \"$segundo_endereco_pais\",\n" +
            "        \"secondAddressCensus\": \"$segundo_endereco_setor_censitario\",\n" +
            "        \"secondAddressObservation\": \"$segundo_endereco_observacao\",\n" +
            "        \"thirdAddressCep\": \"$terceiro_endereco_cep\",\n" +
            "        \"thirdAddressStreet\": \"$terceiro_endereco_rua\",\n" +
            "        \"thirdAddressNumber\": \"$terceiro_endereco_numero\",\n" +
            "        \"thirdAddressComplements\": \"$terceiro_endereco_complemento\",\n" +
            "        \"thirdAddressNeighbourhood\": \"$terceiro_endereco_bairro\",\n" +
            "        \"thirdAddressCity\": \"$terceiro_endereco_cidade\",\n" +
            "        \"thirdAddressUf\": \"$terceiro_endereco_uf\",\n" +
            "        \"thirdAddressCountry\":\"$terceiro_endereco_pais\",\n" +
            "        \"thirdAddressCensus\":\"$terceiro_endereco_setor_censitario\",\n" +
            "        \"thirdAddressObservation\":\"$terceiro_endereco_observacao\",\n" +
            "        \"fourthAddressCep\":\"$quarto_endereco_cep\",\n" +
            "        \"fourthAddressStreet\":\"$quarto_endereco_rua\",\n" +
            "        \"fourthAddressNumber\":\"$quarto_endereco_numero\",\n" +
            "        \"fourthAddressComplements\":\"$quarto_endereco_complemento\",\n" +
            "        \"fourthAddressNeighbourhood\":\"$quarto_endereco_bairro\",\n" +
            "        \"fourthAddressCity\":\"$quarto_endereco_cidade\",\n" +
            "        \"fourthAddressUf\":\"$quarto_endereco_uf\",\n" +
            "        \"fourthAddressCountry\":\"$quarto_endereco_pais\",\n" +
            "        \"fourthAddressCensus\":\"$quarto_endereco_setor_censitario\",\n" +
            "        \"fourthAddressObservation\":\"$quarto_endereco_observacao\",\n" +
            "        \"fifthAddressCep\": \"$quinto_endereco_cep\",\n" +
            "        \"fifthAddressStreet\": \"$quinto_endereco_rua\",\n" +
            "        \"fifthAddressNumber\": \"$quinto_endereco_numero\",\n" +
            "        \"fifthAddressComplements\": \"$quinto_endereco_complemento\",\n" +
            "        \"fifthAddressNeighbourhood\": \"$quinto_endereco_bairro\",\n" +
            "        \"fifthAddressCity\": \"$quinto_endereco_cidade\",\n" +
            "        \"fifthAddressUf\": \"$quinto_endereco_uf\",\n" +
            "        \"fifthAddressCountry\": \"$quinto_endereco_pais\",\n" +
            "        \"fifthAddressCensus\": \"$quinto_endereco_setor_censitario\",\n" +
            "        \"fifthAddressObservation\": \"$quinto_endereco_observacao\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}"));

    return this;
  }

  public ParticipantQueryBuilder getProjectionOfParticipantToExtraction() {
    Document project = this.parseQuery("{\n" +
            "    $project: {\n" +
            "      recruitmentNumber: \"$_id\",\n" +
            "      _id: 0,\n" +
            "      results:\"$results\"\n" +
            "    }\n" +
            "  }");
    pipeline.add(project);
    return this;
  }

  private Document parseQuery(String query) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder.create().fromJson(query, Document.class);
  }
}
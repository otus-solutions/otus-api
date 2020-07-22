package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.resource.ReportMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.net.URL;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReportGatewayService.class})
public class ReportGatewayTest {
  private static String BODY = "{\"_id\":\"5be30a2616da480067523dfd\",\"objectType\":\"ParticipantReport\",\"template\":\"\\n\\u003cotus-script\\u003e\\n  {{data.date \\u003d helper.formatDate(ds.atividade[0].getInterviewDate())}}\\n  {{data.participant \\u003d ds.participant[0]}}\\n  {{data.sexo \\u003d data.participant.sex.toUpperCase() \\u003d\\u003d\\u003d \\u0027F\\u0027 ? \\u0027Feminino\\u0027 : \\u0027Masculino\\u0027}}\\n  {{data.nascimento \\u003d helper.formatDate(ds.participant[0].birthdate.value)}}\\n  {{data.exame \\u003d ds.exame[0]}}\\n  {{data.exameObservation \\u003d data.exame.observations[0] ? data.exame.observations[0].value : \\\"\\\"}}\\n  {{data.result \\u003d helper.getObjectByArray(ds.exame[0].examResults, \\u0027resultName\\u0027, \\u0027HEMOGLOBINA GLICADA-HBA1C...............:\\u0027)}}\\n\\u003c/otus-script\\u003e\\n\\u003cdiv\\u003e\\n  \\u003cstyle type\\u003d\\\"text/css\\\"\\u003e\\n    img {\\n      display: block;\\n      margin-left: auto;\\n      margin-right: auto;\\n      margin-bottom: 0.5cm;\\n      width: 80mm;\\n    }\\n\\n    hr {\\n      border-top: 1.5pt solid #000000;\\n    }\\n\\n    .footer{\\n      width: 100%;\\n      border-top: 2.0pt solid #000000;\\n      font-family: \\\"Arial\\\", \\\"serif\\\";\\n      font-size: 12px;\\n      text-align: center;\\n      position: absolute; \\n    }\\n\\n    .footer-1{\\n      top: 260mm;\\n    }\\n\\n    .participantInfo {\\n      display: flex;\\n      border-bottom: 2.0pt solid #000000;\\n      margin-bottom: 0.3cm;\\n    }\\n\\n    .column {\\n      flex: 20%;\\n      font-family: \\\"Verdana\\\", \\\"serif\\\";\\n      font-size: 12px;\\n      font-weight: bold;\\n      margin-bottom: 0.1cm;\\n    }\\n\\n    .contextValues {\\n      font-family: \\\"Verdana\\\", \\\"serif\\\";\\n      font-size: 12px;\\n      font-weight: bold;\\n      margin-bottom: 1cm;\\n    }\\n    .contextValues p:first-of-type {\\n      font-size: 14px;\\n    }\\n    .contextObs {\\n      font-family: \\\"Verdana\\\", \\\"serif\\\";\\n      font-size: 12px;\\n      line-height: 150%;\\n      height: 17cm;\\n    }\\n    p{\\n      margin: 0.5em 0;\\n    }\\n  \\u003c/style\\u003e\\n  \\u003cheader\\u003e\\n    \\u003c!-- TODO: Substituir imagem --\\u003e\\n    \\u003cimg src\\u003d\\\"https://bitbucket.org/otus-solutions/assets/raw/e6983f91d1b4d5b14d8ffd022cac5581f6b37670/images/elsa-logo.png\\\"\\u003e\\n  \\u003c/header\\u003e\\n\\n  \\u003csection class\\u003d\\\"participantInfo\\\"\\u003e\\n    \\u003csection class\\u003d\\\"column\\\"\\u003e\\n      Nome: {{data.participant.name}}\\n      \\u003cbr\\u003e Sexo: {{data.sexo}}\\n      \\u003cbr\\u003e Data de Nascimento: {{data.nascimento}}\\n      \\u003cbr\\u003e\\n    \\u003c/section\\u003e\\n    \\u003csection class\\u003d\\\"column\\\"\\u003e\\n      Número de Recrutamento: {{data.participant.recruitmentNumber}}\\n      \\u003cbr\\u003e Data da coleta: {{data.date}}\\n    \\u003c/section\\u003e\\n  \\u003c/section\\u003e\\n\\n\\n  \\u003csection class\\u003d\\\"contextValues\\\"\\u003e\\n    \\u003cp\\u003eHemoglobina glicada (HbA1c) – Sangue total\\u003c/p\\u003e\\n    \\u003cbr/\\u003e\\n    \\u003cp\\u003eHbA1C: {{data.result.value}}%\\u003c/p\\u003e\\n  \\u003c/section\\u003e\\n\\n  \\u003csection class\\u003d\\\"contextObs\\\"\\u003e\\n    \\u003cspan ng-if\\u003d\\\"data.exameObservation\\\"\\u003e\\n      \\u003cp\\u003eObs: {{data.exameObservation}}\\u003c/p\\u003e\\n      \\u003cbr\\u003e\\n    \\u003c/span\\u003e\\n    \\u003cp\\u003eMetodologia: HPLC\\u003c/p\\u003e\\n    \\u003cbr\\u003e\\n    \\u003cp\\u003eValores de Referência:\\u003c/p\\u003e\\n    \\u003cp\\u003e \\u003c 5,7 %: tolerância normal a glicose\\u003c/p\\u003e\\n    \\u003cp\\u003e de 5,7 a 6,4%: risco aumentado para diabetes (pré-diabetes)\\u003c/p\\u003e\\n    \\u003cp\\u003e ≥ 6,5%: diabetes\\u003c/p\\u003e\\n    \\u003cbr\\u003e\\n    \\u003cp\\u003eObs.: Valores de referência segundo critérios diagnósticos da Associação Americana de Diabetes (2017).\\u003c/p\\u003e\\n    \\u003chr\\u003e\\n  \\u003c/section\\u003e\\n\\n  \\u003cfooter class\\u003d\\\"footer footer-1\\\"\\u003e\\n    \\u003cp\\u003eResponsável técnico: Ligia Maria Giongo Fedeli - CRF SP 10491\\u003c/p\\u003e\\n  \\u003c/footer\\u003e\\n\\u003c/div\\u003e\\n\",\"label\":\"HbA1c - Hemoglobina Glicada (Lab. Central)\",\"sender\":\"diogo.rosas.ferreira@gmail.com\",\"sendingDate\":\"2018-11-07T15:52:06.040Z\",\"fieldCenter\":[\"SP\",\"RS\",\"RJ\",\"MG\",\"ES\",\"BA\"],\"dataSources\":[{\"key\":\"participant\",\"dataSource\":\"Participant\",\"label\":\"Informações do participante\",\"result\":[{\"recruitmentNumber\":5555563,\"name\":\"Fulano Detal\",\"sex\":\"M\",\"birthdate\":{\"objectType\":\"ImmutableDate\",\"value\":\"1949-04-22 00:00:00.000\"},\"fieldCenter\":{\"name\":null,\"code\":5,\"acronym\":\"RS\",\"country\":null,\"state\":null,\"address\":null,\"complement\":null,\"zip\":null,\"phone\":null,\"backgroundColor\":null,\"borderColor\":null,\"locationPoint\":null}}],\"optional\":false},{\"filters\":{\"acronym\":\"CSJ\",\"category\":\"C0\",\"statusHistory\":{\"name\":\"FINALIZED\",\"position\":-1}},\"key\":\"atividade\",\"dataSource\":\"Activity\",\"label\":\"Formulario CSJ com status igual a finalizado\",\"result\":[{\"statusHistory\":[{\"objectType\":\"ActivityStatus\",\"name\":\"CREATED\",\"date\":\"2017-09-26T15:34:23.205Z\",\"user\":{\"name\":\"Andreza\",\"surname\":\"Moreira Oliveira dos Santos\",\"phone\":\"21982447739\",\"email\":\"andrezasantosjc@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"OPENED\",\"date\":\"2017-09-26T15:39:29.808Z\",\"user\":{\"name\":\"Andreza\",\"surname\":\"Moreira Oliveira dos Santos\",\"phone\":\"21982447739\",\"email\":\"andrezasantosjc@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_ONLINE\",\"date\":\"2017-09-26T15:39:32.582Z\",\"user\":{\"name\":\"Andreza\",\"surname\":\"Moreira Oliveira dos Santos\",\"phone\":\"21982447739\",\"email\":\"andrezasantosjc@gmail.com\"}},{\"objectType\":\"ActivityStatus\",\"name\":\"FINALIZED\",\"date\":\"2017-09-26T15:41:06.522Z\",\"user\":{\"name\":\"Andreza\",\"surname\":\"Moreira Oliveira dos Santos\",\"phone\":\"21982447739\",\"email\":\"andrezasantosjc@gmail.com\"}}],\"mode\":\"ONLINE\"}],\"optional\":false},{\"filters\":{\"examName\":\"HEMOGLOBINA GLICADA - SANGUE\",\"fieldCenter\":\"SP\"},\"key\":\"exame\",\"dataSource\":\"Exam\",\"label\":\"Resultados HbA1C\",\"result\":[{\"_id\":\"5b0475ea40181911a4d2eda9\",\"examSendingLotId\":\"5ebd9da7b9bfaf5fd3372134\",\"objectType\":\"Exam\",\"name\":\"HEMOGLOBINA GLICADA - SANGUE\",\"examResults\":[{\"examSendingLotId\":\"5b0475ea40181911a4d2eda8\",\"examId\":\"5b0475ea40181911a4d2eda9\",\"_id\":\"5b0475eb40181911a4d2f07c\",\"objectType\":\"ExamResults\",\"code\":null,\"examName\":\"HEMOGLOBINA GLICADA - SANGUE\",\"resultName\":\"HEMOGLOBINA GLICADA-HBA1C...............:\",\"value\":\"6.0\",\"isValid\":true,\"releaseDate\":\"2018-03-29T11:14:00.000Z\",\"observations\":[],\"fieldCenter\":null,\"recruitmentNumber\":5555563,\"sex\":\"F\",\"birthdate\":{\"objectType\":\"ImmutableDate\",\"value\":\"1930-06-01 00:00:00.000\"}}],\"observations\":[]}],\"optional\":false}],\"isInApp\":true}";
  private static String RESPONSE_TO_REPORT = "\"{\"data\":{\n\u003cdiv\u003e\n  \u003cstyle type\u003d\"text/css\"\u003e\n    img {\n      display: block;\n      margin-left: auto;\n      margin-right: auto;\n      margin-bottom: 0.5cm;\n      width: 80mm;\n    }\n\n    hr {\n      border-top: 1.5pt solid #000000;\n    }\n\n    .footer{\n      width: 100%;\n      border-top: 2.0pt solid #000000;\n      font-family: \"Arial\", \"serif\";\n      font-size: 12px;\n      text-align: center;\n      position: absolute; \n    }\n\n    .footer-1{\n      top: 260mm;\n    }\n\n    .participantInfo {\n      display: flex;\n      border-bottom: 2.0pt solid #000000;\n      margin-bottom: 0.3cm;\n    }\n\n    .column {\n      flex: 20%;\n      font-family: \"Verdana\", \"serif\";\n      font-size: 12px;\n      font-weight: bold;\n      margin-bottom: 0.1cm;\n    }\n\n    .contextValues {\n      font-family: \"Verdana\", \"serif\";\n      font-size: 12px;\n      font-weight: bold;\n      margin-bottom: 1cm;\n    }\n    .contextValues p:first-of-type {\n      font-size: 14px;\n    }\n    .contextObs {\n      font-family: \"Verdana\", \"serif\";\n      font-size: 12px;\n      line-height: 150%;\n      height: 17cm;\n    }\n    p{\n      margin: 0.5em 0;\n    }\n  \u003c/style\u003e\n  \u003cheader\u003e\n    \u003c!-- TODO: Substituir imagem --\u003e\n    \u003cimg src\u003d\"https://bitbucket.org/otus-solutions/assets/raw/e6983f91d1b4d5b14d8ffd022cac5581f6b37670/images/elsa-logo.png\"\u003e\n  \u003c/header\u003e\n\n  \u003csection class\u003d\"participantInfo\"\u003e\n    \u003csection class\u003d\"column\"\u003e\n      Nome: Fulano Detal\n      \u003cbr\u003e Sexo: Masculino\n      \u003cbr\u003e Data de Nascimento: 22-4-1949\n      \u003cbr\u003e\n    \u003c/section\u003e\n    \u003csection class\u003d\"column\"\u003e\n      Número de Recrutamento: 5555563\n      \u003cbr\u003e Data da coleta: 26-9-2017\n    \u003c/section\u003e\n  \u003c/section\u003e\n\n\n  \u003csection class\u003d\"contextValues\"\u003e\n    \u003cp\u003eHemoglobina glicada (HbA1c) – Sangue total\u003c/p\u003e\n    \u003cbr/\u003e\n    \u003cp\u003eHbA1C: %\u003c/p\u003e\n  \u003c/section\u003e\n\n  \u003csection class\u003d\"contextObs\"\u003e\n    \u003cspan ng-if\u003d\"data.exameObservation\"\u003e\n      \u003cp\u003eObs: \u003c/p\u003e\n      \u003cbr\u003e\n    \u003c/span\u003e\n    \u003cp\u003eMetodologia: HPLC\u003c/p\u003e\n    \u003cbr\u003e\n    \u003cp\u003eValores de Referência:\u003c/p\u003e\n    \u003cp\u003e \u003c 5,7 %: tolerância normal a glicose\u003c/p\u003e\n    \u003cp\u003e de 5,7 a 6,4%: risco aumentado para diabetes (pré-diabetes)\u003c/p\u003e\n    \u003cp\u003e ≥ 6,5%: diabetes\u003c/p\u003e\n    \u003cbr\u003e\n    \u003cp\u003eObs.: Valores de referência segundo critérios diagnósticos da Associação Americana de Diabetes (2017).\u003c/p\u003e\n    \u003chr\u003e\n  \u003c/section\u003e\n\n  \u003cfooter class\u003d\"footer footer-1\"\u003e\n    \u003cp\u003eResponsável técnico: Ligia Maria Giongo Fedeli - CRF SP 10491\u003c/p\u003e\n  \u003c/footer\u003e\n\u003c/div\u003e\n}";
  private static String HOST = "http://localhost:";
  private static String PORT = "8081";

  @InjectMocks
  private ReportGatewayService gateway;
  @Mock
  private URL requestURL;
  @Mock
  private JsonPOSTUtility jsonPOSTUtility;
  @Mock
  private GatewayResponse gatewayResponse;
  private ReportMicroServiceResources resources = PowerMockito.spy(new ReportMicroServiceResources());

  @Test
  public void getReportTemplate_method_should_call_expected_methods() throws Exception {
    PowerMockito.whenNew(ReportMicroServiceResources.class).withNoArguments().thenReturn(resources);
    Whitebox.setInternalState(resources, "HOST", HOST);
    Whitebox.setInternalState(resources, "PORT", PORT);

    PowerMockito.when(resources.getReportTemplate()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(RESPONSE_TO_REPORT);

    this.gateway.getReportTemplate(BODY);

    Mockito.verify(resources, Mockito.times(1)).getReportTemplate();
    Mockito.verify(jsonPOSTUtility, Mockito.times(1)).finish();
  }


}
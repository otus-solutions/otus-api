package br.org.otus.laboratory.project.builder;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MaterialTrackingQueryBuilder {
    private ArrayList<Bson> pipeline;

    public MaterialTrackingQueryBuilder() {
        this.pipeline = new ArrayList<>();
    }

    public List<Bson> build() {
        return this.pipeline;
    }

    public MaterialTrackingQueryBuilder getMaterialTrackingListQuery(String materialCode) {
        pipeline.add(this.parseQuery("{\n" +
                "      $match:{\n" +
                "          \"materialCode\": \""+ materialCode + "\""+
                "      }  \n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "        $lookup:{\n" +
                "            from: \"transportation_lot\",\n" +
                "            localField: \"transportationLotId\",\n" +
                "            foreignField: \"_id\",\n" +
                "            as:\"lot\"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "        $lookup:{\n" +
                "            from: \"transport_material_correlation\",\n" +
                "            let: {materialCode:\""+materialCode+"\",transportationLotId:\"$transportationLotId\"},\n" +
                "            pipeline: [\n" +
                "                {\n" +
                "                    $match: {\n" +
                "                        $expr:{\n" +
                "                            $eq:[\"$_id\",\"$$transportationLotId\"]\n" +
                "                        }\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    $unwind:\"$receivedMaterials\"\n" +
                "                },\n" +
                "                {\n" +
                "                    $match: {\n" +
                "                        $expr:{\n" +
                "                            $eq:[\"$receivedMaterials.materialCode\",\"$$materialCode\"]\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "                ],\n" +
                "            as:\"correlation\"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "        $project:{\n" +
                "            \"transportationLotId\": 1,\n" +
                "            \"origin\": {$arrayElemAt:[\"$lot.originLocationPoint\",0]},\n" +
                "            \"destination\": {$arrayElemAt:[\"$lot.destinationLocationPoint\",0]},\n" +
                "            \"sendingDate\": {$arrayElemAt:[\"$lot.shipmentDate\",0]},\n" +
                "            \"receiptData\": {$arrayElemAt:[\"$correlation\",0]}\n" +
                "        }\n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "        $project:{\n" +
                "            \"lotId\": \"$transportationLotId\",\n" +
                "            \"_id\":0,\n" +
                "            \"origin\": 1,\n" +
                "            \"destination\": 1,\n" +
                "            \"sendingDate\": 1,\n" +
                "            \"receipted\": {\"$cond\":[{$eq:[{$ifNull:[\"$receiptData\",false]},false]},false,true]},\n" +
                "            \"receiveResponsible\": \"$receiptData.receivedMaterials.receiveResponsible\",\n" +
                "            \"receiptMetadata\": \"$receiptData.receivedMaterials.receiptMetadata\",\n" +
                "            \"otherMetadata\": \"$receiptData.receivedMaterials.otherMetadata\",\n" +
                "            \"receiptDate\": \"$receiptData.receivedMaterials.receiptDate\"\n" +
                "        }\n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "         $lookup:{\n" +
                "            from: \"transport_location_point\",\n" +
                "            localField: \"origin\",\n" +
                "            foreignField: \"_id\",\n" +
                "            as:\"origin\"\n" +
                "         }\n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "         $lookup:{\n" +
                "            from: \"transport_location_point\",\n" +
                "            localField: \"destination\",\n" +
                "            foreignField: \"_id\",\n" +
                "            as:\"destination\"\n" +
                "         }\n" +
                "    }"));
        pipeline.add(this.parseQuery("{\n" +
                "         $lookup:{\n" +
                "            from: \"user\",\n" +
                "            localField: \"receiveResponsible\",\n" +
                "            foreignField: \"_id\",\n" +
                "            as:\"receiveResponsible\"\n" +
                "         }\n" +
                "    }"));

        pipeline.add(this.parseQuery("{\n" +
                "        $project:{\n" +
                "            \"lotId\": 1,\n" +
                "            \"receipted\": 1,\n" +
                "            \"otherMetadata\": 1,\n" +
                "            \"receiptDate\": 1,\n" +
                "            \"origin\": {$arrayElemAt:[\"$origin.name\",0]},\n" +
                "            \"destination\": {$arrayElemAt:[\"$destination.name\",0]},\n" +
                "            \"sendingDate\": 1,\n" +
                "            \"receiveResponsible\": {$arrayElemAt:[\"$receiveResponsible.email\",0]},\n" +
                "            \"receiptMetadata\": 1\n" +
                "            \n" +
                "        }\n" +
                "    }"));

        return this;
    }

    private Document parseQuery(String query) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(query, Document.class);
    }
}

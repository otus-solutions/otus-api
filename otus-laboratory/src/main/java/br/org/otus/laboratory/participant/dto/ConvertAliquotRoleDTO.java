package br.org.otus.laboratory.participant.dto;

import br.org.otus.laboratory.participant.aliquot.AliquotHistory;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ConvertAliquotRoleDTO {

    private String aliquotCode;
    private String role;
    private List<AliquotHistory> aliquotHistories;

    public static ConvertAliquotRoleDTO deserialize(String convertAliquotRoleJson) {
        return ConvertAliquotRoleDTO.getGsonBuilder().create().fromJson(convertAliquotRoleJson, ConvertAliquotRoleDTO.class);
    }

    public static String serialize(ConvertAliquotRoleDTO convertAliquotRoleDTO) {
        return ConvertAliquotRoleDTO.getGsonBuilder().create().toJson(convertAliquotRoleDTO);
    }

    public static GsonBuilder getGsonBuilder() {
		return new GsonBuilder();
	}


    public String getAliquotCode() {
        return aliquotCode;
    }

    public List<AliquotHistory> getAliquotHistories() {
        return aliquotHistories;
    }

    public String getRole() {
        return role;
    }
}

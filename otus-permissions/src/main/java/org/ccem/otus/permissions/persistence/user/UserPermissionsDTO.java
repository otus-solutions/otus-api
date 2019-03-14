package org.ccem.otus.permissions.persistence.user;

import com.google.gson.GsonBuilder;
import org.ccem.otus.permissions.model.Permission;
import org.ccem.otus.permissions.utils.PermissionAdapter;

import java.util.ArrayList;

public class UserPermissionsDTO {
    private ArrayList<Permission> permissions;


    public static UserPermissionsDTO deserialize(String reportTemplateJson)  {
        UserPermissionsDTO userPermissionsDTO = UserPermissionsDTO.getGsonBuilder().create().fromJson(reportTemplateJson, UserPermissionsDTO.class);
        return userPermissionsDTO;
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Permission.class, new PermissionAdapter());
        builder.disableHtmlEscaping();
        return builder;
    }
}

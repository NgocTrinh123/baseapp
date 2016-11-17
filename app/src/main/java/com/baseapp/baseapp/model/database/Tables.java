package com.baseapp.baseapp.model.database;

/**
 * Created by mb on 8/19/16.
 */

public interface Tables {

    interface CurrentUser {
        String id = "id";
        String apiToken = "apiToken";
        String role = "role";
        String email = "email";
        String name = "name";
        String branchName = "branchName";
        String regionName = "regionName";
        String countryName = "countryName";
        String firstLogin = "firstLogin";
        String avatarUrl = "avatarUrl";
    }

}

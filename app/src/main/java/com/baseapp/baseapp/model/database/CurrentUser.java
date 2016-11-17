package com.baseapp.baseapp.model.database;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mb on 8/29/16.
 */

public class CurrentUser extends RealmObject {
    @Expose
    @SerializedName("apiToken")
    private String apiToken;

    @Expose
    @SerializedName("id")
    @PrimaryKey
    private String id;

    @Expose
    @SerializedName("firstLogin")
    private boolean firstLogin;

    @Expose
    @SerializedName("role")
    private String role;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatarUrl;

    @SerializedName("branch_name")
    private String branchName;

    @SerializedName("region_name")
    private String regionName;

    @SerializedName("country_name")
    private String countryName;

    public CurrentUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getRole() {
        if (TextUtils.isEmpty(role)) {
            return "";
        }
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}

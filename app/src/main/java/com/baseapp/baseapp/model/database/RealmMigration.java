package com.baseapp.baseapp.model.database;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmSchema;

/**
 * Created by mb on 7/27/16.
 */

public class RealmMigration implements io.realm.RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {

            schema.create(Tables.CurrentUser.class.getSimpleName())
                    .addField(Tables.CurrentUser.id, String.class, FieldAttribute.PRIMARY_KEY)
                    .addField(Tables.CurrentUser.apiToken, String.class)
                    .addField(Tables.CurrentUser.role, String.class)
                    .addField(Tables.CurrentUser.email, String.class)
                    .addField(Tables.CurrentUser.name, String.class)
                    .addField(Tables.CurrentUser.branchName, String.class)
                    .addField(Tables.CurrentUser.regionName, String.class)
                    .addField(Tables.CurrentUser.countryName, String.class)
                    .addField(Tables.CurrentUser.firstLogin, boolean.class)
                    .addField(Tables.CurrentUser.avatarUrl, String.class);

        }
    }
}

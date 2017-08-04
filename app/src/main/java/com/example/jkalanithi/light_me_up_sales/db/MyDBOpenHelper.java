package com.example.jkalanithi.light_me_up_sales.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jkalanithi on 24/07/2017.
 */

public class MyDBOpenHelper  extends SQLiteOpenHelper {
    public static final String DB_NAME = "LIGHTMEUP";
    public static final int DB_VERSION = 1;
    public static final String MY_TABLE_NAME = "CONNEXION";
    public static final String FIELD1 = "LOGIN";
    public static final String FIELD2 = "PASS";
    public static final String FIELD3 = "MAIL";
    public static final String FIELD4 = "TEL";

    private static final String MY_TABLE_CREATE = "CREATE TABLE " + MY_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD3 + " VARCHAR," + FIELD4 + " VARCHAR," + FIELD1 + " VARCHAR," + FIELD2 + " VARCHAR)";

    /**
     * My constructor
     * @param context
     */
    public MyDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Méthode nécessaire pour la création du modèle de la base de données
     * @param db
     *            La base de données
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MY_TABLE_CREATE);
    }

    /**
     * Méthode nécessaire à la mise à jour du modèle de la base de données
     * @param db
     *            La base de données
     * @param oldVersion
     *            Numéro de l'ancienne version du modèle de données
     * @param newVersion
     *            Numéro de la nouvelle version du modèle de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

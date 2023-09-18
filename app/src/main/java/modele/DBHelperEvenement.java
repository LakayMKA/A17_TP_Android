package modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperEvenement extends SQLiteOpenHelper {

    public static final String BD_NAME = "EcoEventsDB";
    public static final int VERSION = 1;
    public static final String TABLE_EVENEMENTS ="Evenements";
    public static final String COL_ID = "_id";
    public static final String COL_NOM = "nom";
    public static final String COL_ADRESSE = "adresse";
    public static final String COL_DATE = "date";
    public static final String COL_TYPE = "type";
    public static final String COL_NOMBRE_PARTICIPANTS = "nombreParticipant";

    public static final String DDL_EVENEMENT = "create table "+TABLE_EVENEMENTS+"("+ COL_ID+"" +
            " integer primary key autoincrement, "+COL_NOM+" TEXT,"+COL_ADRESSE+"TEXT,"+COL_DATE+"TEXT,"+
            COL_TYPE+"TEXT,"+COL_NOMBRE_PARTICIPANTS+"INT)";



    public DBHelperEvenement(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DDL_EVENEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

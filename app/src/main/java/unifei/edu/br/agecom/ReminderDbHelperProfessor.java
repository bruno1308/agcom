package unifei.edu.br.agecom;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ReminderDbHelperProfessor extends SQLiteOpenHelper {
    static final String DB_NAME = "treinaweb_professor.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "professores";
    static final String L_ID = "_id";
    static final String L_NOMEP = "nomeP";
    static final String L_EMAILP = "emailP";
    static final String L_SITE = "site";

    public ReminderDbHelperProfessor(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try{
            String sql = "create table " + TABLE + " (" + L_ID + " integer primary key autoincrement, "
                    + L_NOMEP + " text, " + L_EMAILP + " text, " + L_SITE + " text)";
            db.execSQL(sql);
        }catch(Exception e){
            Log.e("Error DbHelper", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        try{
            db.execSQL("drop table if exists " + TABLE);
            onCreate(db);
        }catch(Exception e){
            Log.e("Error DbHelper", e.getMessage());
        }
    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

}

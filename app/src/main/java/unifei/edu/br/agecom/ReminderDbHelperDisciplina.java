package unifei.edu.br.agecom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReminderDbHelperDisciplina extends SQLiteOpenHelper {
    static final String DB_NAME = "treinaweb_disciplina.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "disciplina";
    static final String L_ID = "_id";
    static final String L_SIGLA = "sigla";
    static final String L_NOME = "nome";
    static final String L_PROFESSOR = "professor";

    public ReminderDbHelperDisciplina(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try{
            String sql = "create table " + TABLE + " (" + L_ID + " integer primary key autoincrement, "
                    + L_SIGLA + " text, " + L_NOME + " text, " + L_PROFESSOR + " text)";
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
}
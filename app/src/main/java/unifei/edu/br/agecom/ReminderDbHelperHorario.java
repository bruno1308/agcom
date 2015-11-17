package unifei.edu.br.agecom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReminderDbHelperHorario extends SQLiteOpenHelper {
    static final String DB_NAME = "treinaweb_horario.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "horario";
    static final String L_ID = "_id";
    static final String L_DISCIPLINA = "disciplina";
    static final String L_ATIVIDADE = "atividade";
    static final String L_DIA = "dia";
    static final String L_INICIO = "inicio";
    static final String L_FIM = "fim";

    public ReminderDbHelperHorario(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try{
            String sql = "create table " + TABLE + " (" + L_ID + " integer primary key autoincrement, "
                    + L_DISCIPLINA + " text, " + L_ATIVIDADE + " text, " + L_DIA + " text, " + L_INICIO + " text," +
                    " " + L_FIM + " text)";
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
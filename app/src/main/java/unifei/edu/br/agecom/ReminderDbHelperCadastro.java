package unifei.edu.br.agecom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Natalia on 16/11/2015.
 */
public class ReminderDbHelperCadastro extends SQLiteOpenHelper {
    static final String DB_NAME = "treinaweb_cadastro.db";
    static final int DB_VERSION = 1;
    static final String TABLE = "Cadastro";
    static final String L_ID = "_id";
    static final String L_NOME = "nome";
    static final String L_CURSO = "curso";
    static final String L_LOGIN = "login";
    static final String L_SENHA = "senha";


    public ReminderDbHelperCadastro(Context context){

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try{
            String sql = "create table " + TABLE + " (" + L_ID + " integer primary key autoincrement, "
                    + L_NOME + " text, " + L_CURSO + " text " + L_LOGIN + " text " + L_SENHA + " text )";
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
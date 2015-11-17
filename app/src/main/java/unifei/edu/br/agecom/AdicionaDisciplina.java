package unifei.edu.br.agecom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;

public class AdicionaDisciplina extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_disciplina);

        if(getIntent().hasExtra("id"))
            LoadData();
    }

    public void salvarDisciplina(View view){
        EditText edtSigla = (EditText) findViewById(R.id.editSigla);
        EditText edtNome = (EditText) findViewById(R.id.editNome);
        EditText edtProfessor = (EditText) findViewById(R.id.editProfessor);
        String sigla = edtSigla.getText().toString();
        String nome = edtNome.getText().toString();
        String professor = edtProfessor.getText().toString();

        SaveDatabaseDisciplina(sigla, nome, professor);
    }

    public boolean SaveDatabaseDisciplina(String sigla, String nome, String professor){
        try{
            ReminderDbHelperDisciplina dbHelper = new ReminderDbHelperDisciplina(this);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReminderDbHelperDisciplina.L_SIGLA, sigla);
            values.put(ReminderDbHelperDisciplina.L_NOME, nome);
            values.put(ReminderDbHelperDisciplina.L_PROFESSOR, professor);

            try{
                if(getIntent().hasExtra("id")){
                    String id = getIntent().getExtras().getString("id");
                    String where = ReminderDbHelperDisciplina.L_ID + "=?";
                    String[] whereArgs = { id };
                    if(db.update(ReminderDbHelperDisciplina.TABLE, values, where, whereArgs) > 0){
                        Toast.makeText(getBaseContext(), "Disciplina alterada!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), DisciplinaActivity.class));
                    }
                }else{
                    long returnID = db.insertOrThrow(ReminderDbHelperDisciplina.TABLE, null, values);
                    if(returnID != -1){
                        Toast.makeText(getBaseContext(), "Disciplina salva!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }finally
            {
                db.close();
            }
            return true;
        }catch(Exception e)
        {
            Log.e("error sqlite", e.getMessage());
            return false;
        }
    }

    private void LoadData(){
        EditText eTextSigla = (EditText) findViewById(R.id.editSigla);
        EditText eTextNome = (EditText) findViewById(R.id.editNome);
        EditText eTextProfessor = (EditText) findViewById(R.id.editProfessor);

        String[] columns = {ReminderDbHelperDisciplina.L_ID, ReminderDbHelperDisciplina.L_SIGLA, ReminderDbHelperDisciplina.L_NOME,
        ReminderDbHelperDisciplina.L_PROFESSOR};
        String id = getIntent().getExtras().getString("id");
        String selection = ReminderDbHelperDisciplina.L_ID + "=?";
        String[] selectionArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperDisciplina dbHelper = new ReminderDbHelperDisciplina(this);

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperDisciplina.TABLE, columns, selection, selectionArgs, null, null, null);
                if(cursor.moveToNext()){
                    eTextSigla.setText(cursor.getString(1));
                    eTextNome.setText(cursor.getString(2));
                    eTextProfessor.setText(cursor.getString(3));
                }
            }catch(Exception e){
                Log.e("error sqlite", e.getMessage());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("error sqlite", e.getMessage());
        }
        finally{
            db.close();
        }
    }
}
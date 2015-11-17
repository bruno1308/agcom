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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;

public class AdicionaHorario extends AppCompatActivity {

    private String RBVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_horario);

        if(getIntent().hasExtra("id"))
            LoadData();
    }

    public void salvarHorario(View view){
        Spinner spnDisciplina = (Spinner) findViewById(R.id.spinnerDisciplina);
        Spinner spnAtividade = (Spinner) findViewById(R.id.spinnerAtividades);
        EditText edtInicio = (EditText) findViewById(R.id.editTextFrom);
        EditText edtFim = (EditText) findViewById(R.id.editTextTo);
        String disciplina = String.valueOf(spnDisciplina.getSelectedItem());
        String atividade = String.valueOf(spnAtividade.getSelectedItem());
        String inicio = edtInicio.getText().toString();
        String fim = edtFim.getText().toString();

        SaveDatabaseHorario(disciplina, atividade, RBVar, inicio, fim);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButtonSeg:
                if (checked)
                    RBVar = "Seg";
                    break;
            case R.id.radioButtonTer:
                if (checked)
                    RBVar = "Ter";
                    break;
            case R.id.radioButtonQua:
                if (checked)
                    RBVar = "Qua";
                break;
            case R.id.radioButtonQui:
                if (checked)
                    RBVar = "Qui";
                break;
            case R.id.radioButtonSex:
                if (checked)
                    RBVar = "Sex";
                break;
            case R.id.radioButtonSab:
                if (checked)
                    RBVar = "Sab";
                break;
        }
    }

    public boolean SaveDatabaseHorario(String disciplina, String atividade, String dia, String inicio, String fim){
        try{
            ReminderDbHelperHorario dbHelper = new ReminderDbHelperHorario(this);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReminderDbHelperHorario.L_DISCIPLINA, disciplina);
            values.put(ReminderDbHelperHorario.L_ATIVIDADE, atividade);
            values.put(ReminderDbHelperHorario.L_DIA, dia);
            values.put(ReminderDbHelperHorario.L_INICIO, inicio);
            values.put(ReminderDbHelperHorario.L_FIM, fim);

            try{
                if(getIntent().hasExtra("id")){
                    String id = getIntent().getExtras().getString("id");
                    String where = ReminderDbHelperHorario.L_ID + "=?";
                    String[] whereArgs = { id };
                    if(db.update(ReminderDbHelperHorario.TABLE, values, where, whereArgs) > 0){
                        Toast.makeText(getBaseContext(), "Horario alterado!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), HorarioActivity.class));
                    }
                }else{
                    long returnID = db.insertOrThrow(ReminderDbHelperHorario.TABLE, null, values);
                    if(returnID != -1){
                        Toast.makeText(getBaseContext(), "Horario salvo!", Toast.LENGTH_LONG).show();
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
        EditText edtInicio = (EditText) findViewById(R.id.editTextFrom);
        EditText edtFim = (EditText) findViewById(R.id.editTextTo);

        String[] columns = {ReminderDbHelperHorario.L_ID, ReminderDbHelperHorario.L_DISCIPLINA, ReminderDbHelperHorario.L_ATIVIDADE,
                ReminderDbHelperHorario.L_DIA, ReminderDbHelperHorario.L_INICIO, ReminderDbHelperHorario.L_FIM};
        String id = getIntent().getExtras().getString("id");
        String selection = ReminderDbHelperHorario.L_ID + "=?";
        String[] selectionArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperHorario dbHelper = new ReminderDbHelperHorario(this);

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperHorario.TABLE, columns, selection, selectionArgs, null, null, null);
                if(cursor.moveToNext()){
                    edtInicio.setText(cursor.getString(4));
                    edtFim.setText(cursor.getString(5));
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
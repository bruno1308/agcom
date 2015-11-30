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

public class AdicionaProfessor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_professor);

        if(getIntent().hasExtra("id"))
            LoadData();
    }

    public void salvarProfessor(View view){
        EditText edtNomeP = (EditText) findViewById(R.id.editNomeP);
        EditText edtEmailP = (EditText) findViewById(R.id.editEmailP);
        EditText edtSite = (EditText) findViewById(R.id.editSite);
        String nomeP = edtNomeP.getText().toString();
        String emailP = edtEmailP.getText().toString();
        String site = edtSite.getText().toString();

        SaveDatabaseProfessor(nomeP, emailP, site);
    }

    public boolean SaveDatabaseProfessor(String nomeP, String emailP, String site){
        try{
            ReminderDbHelperProfessor dbHelper = new ReminderDbHelperProfessor(this);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReminderDbHelperProfessor.L_NOMEP, nomeP);
            values.put(ReminderDbHelperProfessor.L_EMAILP, emailP);
            values.put(ReminderDbHelperProfessor.L_SITE, site);

            try{
                if(getIntent().hasExtra("id")){
                    String id = getIntent().getExtras().getString("id");
                    String where = ReminderDbHelperProfessor.L_ID + "=?";
                    String[] whereArgs = { id };
                    if(db.update(ReminderDbHelperProfessor.TABLE, values, where, whereArgs) > 0){
                        Toast.makeText(getBaseContext(), "Professor alterado!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), ProfessorActivity.class));
                    }
                }else{
                    long returnID = db.insertOrThrow(ReminderDbHelperProfessor.TABLE, null, values);
                    if(returnID != -1){
                        Toast.makeText(getBaseContext(), "Professor salvo!", Toast.LENGTH_LONG).show();
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
        EditText eTextNomeP = (EditText) findViewById(R.id.editNomeP);
        EditText eTextEmailP = (EditText) findViewById(R.id.editEmailP);
        EditText eTextSite = (EditText) findViewById(R.id.editSite);

        String[] columns = {ReminderDbHelperProfessor.L_ID, ReminderDbHelperProfessor.L_NOMEP, ReminderDbHelperProfessor.L_EMAILP,
                ReminderDbHelperProfessor.L_SITE};
        String id = getIntent().getExtras().getString("id");
        String selection = ReminderDbHelperProfessor.L_ID + "=?";
        String[] selectionArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperProfessor dbHelper = new ReminderDbHelperProfessor(this);

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperProfessor.TABLE, columns, selection, selectionArgs, null, null, null);
                if(cursor.moveToNext()){
                    eTextNomeP.setText(cursor.getString(1));
                    eTextEmailP.setText(cursor.getString(2));
                    eTextSite.setText(cursor.getString(3));
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
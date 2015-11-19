package unifei.edu.br.agecom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AdicionaLembrete extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_lembrete);

        if(getIntent().hasExtra("id"))
            LoadData();

        Button btnData = (Button) findViewById(R.id.buttonData);
        btnData.setOnClickListener(mClickButtonData);
    }

    private View.OnClickListener mClickButtonData = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "Data");
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO Auto-generated method stub
        TextView txtData = (TextView) findViewById(R.id.textViewData);
        txtData.setText(new StringBuilder().append(dayOfMonth)
                .append("/").append(monthOfYear + 1).append("/").append(year)
                .append(" "));
    }

    public void salvarLembrete(View view){
        TextView txtData = (TextView) findViewById(R.id.textViewData);
        EditText edtLembrete = (EditText) findViewById(R.id.editTextLembrete);
        String data = txtData.getText().toString();
        String lembrete = edtLembrete.getText().toString();

        SaveDatabase(data, lembrete);
    }

    public static class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (AdicionaLembrete)getActivity(), year, month, day);
        }
    }

    public boolean SaveDatabase(String data, String lembrete){
        try{
            ReminderDbHelperLembrete dbHelper = new ReminderDbHelperLembrete(this);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReminderDbHelperLembrete.L_DATA, data);
            values.put(ReminderDbHelperLembrete.L_ANOTACAO, lembrete);

            try{
                if(getIntent().hasExtra("id")){
                    String id = getIntent().getExtras().getString("id");
                    String where = ReminderDbHelperLembrete.L_ID + "=?";
                    String[] whereArgs = { id };
                    if(db.update(ReminderDbHelperLembrete.TABLE, values, where, whereArgs) > 0){
                        Toast.makeText(getBaseContext(), "Lembrente alterado!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), LembreteActivity.class));
                    }
                }else{
                    long returnID = db.insertOrThrow(ReminderDbHelperLembrete.TABLE, null, values);
                    if(returnID != -1){
                        Toast.makeText(getBaseContext(), "Lembrente salvo!", Toast.LENGTH_LONG).show();
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
        TextView tViewData = (TextView) findViewById(R.id.textViewData);
        EditText eTextLembrete = (EditText) findViewById(R.id.editTextLembrete);

        String[] columns = {ReminderDbHelperLembrete.L_ID, ReminderDbHelperLembrete.L_DATA, ReminderDbHelperLembrete.L_ANOTACAO};
        String id = getIntent().getExtras().getString("id");
        String selection = ReminderDbHelperLembrete.L_ID + "=?";
        String[] selectionArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperLembrete dbHelper = new ReminderDbHelperLembrete(this);

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperLembrete.TABLE, columns, selection, selectionArgs, null, null, null);
                if(cursor.moveToNext()){
                    tViewData.setText(cursor.getString(1));
                    eTextLembrete.setText(cursor.getString(2));
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
package unifei.edu.br.agecom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AtualizaLembrete extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_lembrete);

        if(getIntent().hasExtra("id")){
            TextView tViewData = (TextView) findViewById(R.id.textViewData);
            TextView tViewLembrete = (TextView) findViewById(R.id.textViewLembrete);

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
                        tViewLembrete.setText(cursor.getString(2));
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
        else
            finish();
    }

    public void atualizarLembrete(View view){
        Intent intent = new Intent(getBaseContext(), AdicionaLembrete.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }

    public void deletarLembrete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir!");
        builder.setMessage("Realmente deseja excluir o lembrete?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DeleteDatabase();
            }
        });
        builder.setNegativeButton("Não", null);
        builder.show();
    }

    private void DeleteDatabase(){
        String id = getIntent().getExtras().getString("id");
        String where = ReminderDbHelperLembrete.L_ID + "=?";
        String[] whereArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperLembrete dbHelper = new ReminderDbHelperLembrete(this);

            db = dbHelper.getReadableDatabase();
            try{
                if(db.delete(ReminderDbHelperLembrete.TABLE, where, whereArgs) > 0)
                {
                    Toast.makeText(getBaseContext(), "Lembrete exclu�do com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
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
package unifei.edu.br.agecom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import unifei.edu.br.agecom.R;

public class AtualizaProfessor extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_professor);

        if(getIntent().hasExtra("id")){
            TextView tViewNomeP = (TextView) findViewById(R.id.textViewNomeP);
            TextView tViewEmailP = (TextView) findViewById(R.id.textViewEmailP);
            TextView tViewSite = (TextView) findViewById(R.id.textViewSite);

            String[] columns = {ReminderDbHelperProfessor.L_ID, ReminderDbHelperProfessor.L_NOMEP,
                    ReminderDbHelperProfessor.L_EMAILP, ReminderDbHelperProfessor.L_SITE};
            String id = getIntent().getExtras().getString("id");
            String selection = ReminderDbHelperProfessor.L_ID + "=?";
            String[] selectionArgs = { id };

            SQLiteDatabase db = null;
            try {
                ReminderDbHelperProfessor dbHelper = new ReminderDbHelperProfessor(this);

                db = dbHelper.getReadableDatabase();
                try{
                    Cursor cursor = db.query(ReminderDbHelperDisciplina.TABLE, columns, selection, selectionArgs, null, null, null);
                    if(cursor.moveToNext()){
                        tViewNomeP.setText(cursor.getString(1));
                        tViewEmailP.setText(cursor.getString(2));
                        tViewSite.setText(cursor.getString(3));
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

    public void atualizarProfessor(View view){
        Intent intent = new Intent(getBaseContext(), AdicionaDisciplina.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }

    public void deletarProfessor(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir!");
        builder.setMessage("Realmente deseja excluir o professor?");
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
        String where = ReminderDbHelperProfessor.L_ID + "=?";
        String[] whereArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperProfessor dbHelper = new ReminderDbHelperProfessor(this);

            db = dbHelper.getReadableDatabase();
            try{
                if(db.delete(ReminderDbHelperDisciplina.TABLE, where, whereArgs) > 0)
                {
                    Toast.makeText(getBaseContext(), "Professor excluido com sucesso!", Toast.LENGTH_LONG).show();
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
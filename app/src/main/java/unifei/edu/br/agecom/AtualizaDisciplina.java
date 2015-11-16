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
import android.widget.TextView;
import android.widget.Toast;

import unifei.edu.br.agecom.R;

public class AtualizaDisciplina extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_disciplina);

        if(getIntent().hasExtra("id")){
            TextView tViewSigla = (TextView) findViewById(R.id.textViewSigla);
            TextView tViewNomeDisciplina = (TextView) findViewById(R.id.textViewNomeDisciplina);
            TextView tViewProfessor = (TextView) findViewById(R.id.textViewProfessor);

            String[] columns = {ReminderDbHelperDisciplina.L_ID, ReminderDbHelperDisciplina.L_SIGLA,
                    ReminderDbHelperDisciplina.L_NOME, ReminderDbHelperDisciplina.L_PROFESSOR};
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
                        tViewSigla.setText(cursor.getString(1));
                        tViewNomeDisciplina.setText(cursor.getString(2));
                        tViewProfessor.setText(cursor.getString(3));
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

    public void atualizarDisciplina(View view){
        Intent intent = new Intent(getBaseContext(), AdicionaDisciplina.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }

    public void deletarDisciplina(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir!");
        builder.setMessage("Realmente deseja excluir a disciplina?");
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
        String where = ReminderDbHelperDisciplina.L_ID + "=?";
        String[] whereArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperDisciplina dbHelper = new ReminderDbHelperDisciplina(this);

            db = dbHelper.getReadableDatabase();
            try{
                if(db.delete(ReminderDbHelperDisciplina.TABLE, where, whereArgs) > 0)
                {
                    Toast.makeText(getBaseContext(), "Disciplina excluida com sucesso!", Toast.LENGTH_LONG).show();
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
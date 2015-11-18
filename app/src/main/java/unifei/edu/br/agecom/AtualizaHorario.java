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

public class AtualizaHorario extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_horario);

        if(getIntent().hasExtra("id")){
            TextView tViewDisciplina = (TextView) findViewById(R.id.textViewDisciplina);
            TextView tViewAtividade = (TextView) findViewById(R.id.textViewAtividade);
            TextView tViewDiaSemana = (TextView) findViewById(R.id.textViewDiaSemana);
            TextView tViewInicio = (TextView) findViewById(R.id.textViewInicio);
            TextView tViewFim = (TextView) findViewById(R.id.textViewFim);

            String[] columns = {ReminderDbHelperHorario.L_ID, ReminderDbHelperHorario.L_DISCIPLINA,
                    ReminderDbHelperHorario.L_ATIVIDADE, ReminderDbHelperHorario.L_DIA,
                    ReminderDbHelperHorario.L_INICIO, ReminderDbHelperHorario.L_FIM};
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
                        tViewDisciplina.setText(cursor.getString(1));
                        tViewAtividade.setText(cursor.getString(2));
                        tViewDiaSemana.setText(cursor.getString(3));
                        tViewInicio.setText(cursor.getString(4));
                        tViewFim.setText(cursor.getString(5));
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

    public void atualizarHorario(View view){
        Intent intent = new Intent(getBaseContext(), AdicionaHorario.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }

    public void deletarHorario(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir!");
        builder.setMessage("Realmente deseja excluir o Horario?");
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
            ReminderDbHelperHorario dbHelper = new ReminderDbHelperHorario(this);

            db = dbHelper.getReadableDatabase();
            try{
                if(db.delete(ReminderDbHelperHorario.TABLE, where, whereArgs) > 0)
                {
                    Toast.makeText(getBaseContext(), "Horario excluido com sucesso!", Toast.LENGTH_LONG).show();
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
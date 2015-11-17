package unifei.edu.br.agecom;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HorarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
    }

    protected void onStart(){
        super.onStart();
        SQLiteDatabase db = null;
        try {
            ReminderDbHelperHorario dbHelper = new ReminderDbHelperHorario(this);

            String[] uiBindFrom = { ReminderDbHelperHorario.L_ID, ReminderDbHelperHorario.L_DISCIPLINA,
                    ReminderDbHelperHorario.L_ATIVIDADE, ReminderDbHelperHorario.L_DIA,
                    ReminderDbHelperHorario.L_INICIO, ReminderDbHelperHorario.L_FIM};
            int[] uiBindTo = { R.id.textViewIdHorario, R.id.textViewDisciplina, R.id.textViewAtividade,
                    R.id.textViewDiaSemana, R.id.textViewInicio, R.id.textViewFim };

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperHorario.TABLE, null, null, null, null, null, ReminderDbHelperHorario.L_ID + " DESC");
                Log.i("teste", String.valueOf(cursor.getCount()));
                SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.itens_listview_horario, cursor, uiBindFrom, uiBindTo, 0);

                ListView list = (ListView) findViewById(R.id.listViewHorario);
                list.setAdapter(mAdapter);
                list.setOnItemClickListener(mClickItemList);

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

    public void adicionarHorario(View v){
        Intent i = new Intent(this, AdicionaDisciplina.class);
        startActivity(i);
    }

    private AdapterView.OnItemClickListener mClickItemList = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(getBaseContext(), AtualizaHorario.class);
            intent.putExtra("id", String.valueOf(id));
            startActivity(intent);
        }
    };

}
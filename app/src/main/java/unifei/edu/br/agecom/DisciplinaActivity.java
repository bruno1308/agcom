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

public class DisciplinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);
    }

    protected void onStart(){
        super.onStart();
        SQLiteDatabase db = null;
        try {
            ReminderDbHelperDisciplina dbHelper = new ReminderDbHelperDisciplina(this);

            String[] uiBindFrom = { ReminderDbHelperDisciplina.L_ID, ReminderDbHelperDisciplina.L_SIGLA,
                    ReminderDbHelperDisciplina.L_NOME, ReminderDbHelperDisciplina.L_PROFESSOR };
            int[] uiBindTo = { R.id.textViewIdDisciplina, R.id.textViewSigla, R.id.textViewNomeDisciplina, R.id.textViewProfessor };

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperDisciplina.TABLE, null, null, null, null, null, ReminderDbHelperDisciplina.L_ID + " DESC");
                Log.i("teste", String.valueOf(cursor.getCount()));
                SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.itens_listview_disciplina, cursor, uiBindFrom, uiBindTo, 0);

                ListView list = (ListView) findViewById(R.id.listViewDisciplina);
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

    public void adicionarDisciplina(View v){
        Intent i = new Intent(this, AdicionaDisciplina.class);
        startActivity(i);
    }

    private AdapterView.OnItemClickListener mClickItemList = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(getBaseContext(), AtualizaDisciplina.class);
            intent.putExtra("id", String.valueOf(id));
            startActivity(intent);
        }
    };

}

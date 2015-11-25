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

public class ProfessorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
    }

    protected void onStart(){
        super.onStart();
        SQLiteDatabase db = null;
        try {
            ReminderDbHelperProfessor dbHelper = new ReminderDbHelperProfessor(this);

            String[] uiBindFrom = { ReminderDbHelperProfessor.L_ID, ReminderDbHelperProfessor.L_NOMEP,
                    ReminderDbHelperProfessor.L_EMAILP, ReminderDbHelperProfessor.L_SITE };
            int[] uiBindTo = { R.id.textViewIdProfessor, R.id.textViewNomeP, R.id.textViewEmailP, R.id.textViewSite };

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperProfessor.TABLE, null, null, null, null, null, ReminderDbHelperProfessor.L_ID + " DESC");
                Log.i("teste", String.valueOf(cursor.getCount()));
                SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.itens_listview_professor, cursor, uiBindFrom, uiBindTo, 0);

                ListView list = (ListView) findViewById(R.id.listViewProfessor);
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

    public void adicionarProfessor(View v){
        Intent i = new Intent(this, AdicionaProfessor.class);
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

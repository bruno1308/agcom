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

public class LembreteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);
    }

    protected void onStart(){
        super.onStart();
        SQLiteDatabase db = null;
        try {
            ReminderDbHelperLembrete dbHelper = new ReminderDbHelperLembrete(this);

            String[] uiBindFrom = { ReminderDbHelperLembrete.L_ID, ReminderDbHelperLembrete.L_ANOTACAO, ReminderDbHelperLembrete.L_DATA };
            int[] uiBindTo = { R.id.textViewId, R.id.textViewAnotacao, R.id.textViewData };

            db = dbHelper.getReadableDatabase();
            try{
                Cursor cursor = db.query(ReminderDbHelperLembrete.TABLE, null, null, null, null, null, ReminderDbHelperLembrete.L_ID + " DESC");
                Log.i("teste", String.valueOf(cursor.getCount()));
                SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.items_listview_lembrete, cursor, uiBindFrom, uiBindTo, 0);

                ListView list = (ListView) findViewById(R.id.listViewLembrete);
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

    public void adicionarLembrete(View v){
        Intent i = new Intent(this, AdicionaLembrete.class);
        startActivity(i);
    }

    private AdapterView.OnItemClickListener mClickItemList = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(getBaseContext(), AtualizaLembrete.class);
            intent.putExtra("id", String.valueOf(id));
            startActivity(intent);
        }
    };

}

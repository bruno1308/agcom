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

/**
 * Created by Ana on 19/11/2015.
 */
public class CadastroActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cadastro);
        }

        protected void onStart(){
            super.onStart();
            SQLiteDatabase db = null;
            try {
                ReminderDbHelperCadastro dbHelper = new ReminderDbHelperCadastro(this);

                String[] uiBindFrom = { ReminderDbHelperCadastro.L_ID, ReminderDbHelperCadastro.L_NOME,
                        ReminderDbHelperCadastro.L_CURSO, ReminderDbHelperCadastro.L_LOGIN, ReminderDbHelperCadastro.L_SENHA  };
                int[] uiBindTo = { R.id.textViewIdCadastro, R.id.textViewNome, R.id.textViewCurso, R.id.textViewLogin, R.idtextViewSenha };

                db = dbHelper.getReadableDatabase();
                try{
                    Cursor cursor = db.query(ReminderDbHelperCadastro.TABLE, null, null, null, null, null, ReminderDbHelperCadastro.L_ID + " DESC");
                    Log.i("teste", String.valueOf(cursor.getCount()));
                    SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.itens_listview_cadastro, cursor, uiBindFrom, uiBindTo, 0);

                    ListView list = (ListView) findViewById(R.id.listViewCadastro);
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

        public void adicionarCadastro(View v){
            Intent i = new Intent(this, AdicionaCadastro.class);
            startActivity(i);
        }

        private AdapterView.OnItemClickListener mClickItemList = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getBaseContext(), AtualizaCadastro.class);
                intent.putExtra("id", String.valueOf(id));
                startActivity(intent);
            }
        };

    }

}

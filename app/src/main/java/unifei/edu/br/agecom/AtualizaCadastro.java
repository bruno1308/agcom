package unifei.edu.br.agecom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AtualizaCadastro extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_cadastro);

        if(getIntent().hasExtra("id")){
            TextView tViewNome = (TextView) findViewById(R.id.textViewNome);
            TextView tViewCurso = (TextView) findViewById(R.id.textViewCurso);
            TextView tViewLogin = (TextView) findViewById(R.id.textViewLogin);
            TextView tViewSenha = (TextView) findViewById(R.id.textViewSenha);

            String[] columns = {ReminderDbHelperCadastro.L_ID, ReminderDbHelperCadastro.L_NOME,
                    ReminderDbHelperCadastro.L_CURSO, ReminderDbHelperCadastro.L_LOGIN, ReminderDbHelperCadastro.L_SENHA};
            String id = getIntent().getExtras().getString("id");
            String selection = ReminderDbHelperCadastro.L_ID + "=?";
            String[] selectionArgs = { id };

            SQLiteDatabase db = null;
            try {
                ReminderDbHelperCadastro dbHelper = new ReminderDbHelperCadastro(this);

                db = dbHelper.getReadableDatabase();
                try{
                    Cursor cursor = db.query(ReminderDbHelperCadastro.TABLE, columns, selection, selectionArgs, null, null, null, null);
                    if(cursor.moveToNext()){
                        tViewNome.setText(cursor.getString(1));
                        tViewCurso.setText(cursor.getString(2));
                        tViewLogin.setText(cursor.getString(3));
                        tViewSenha.setText(cursor.getString(4));
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

    public void atualizarCadastro(View view){
        Intent intent = new Intent(getBaseContext(), AdicionaCadastro.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }

    public void deletarCadastro(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir!");
        builder.setMessage("Realmente deseja excluir o cadastro?");
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
        String where = ReminderDbHelperCadastro.L_ID + "=?";
        String[] whereArgs = { id };

        SQLiteDatabase db = null;
        try {
            ReminderDbHelperCadastro dbHelper = new ReminderDbHelperCadastro(this);

            db = dbHelper.getReadableDatabase();
            try{
                if(db.delete(ReminderDbHelperCadastro.TABLE, where, whereArgs) > 0)
                {
                    Toast.makeText(getBaseContext(), "Cadastro excluido com sucesso!", Toast.LENGTH_LONG).show();
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
package unifei.edu.br.agecom;

import android.content.ContentValues;
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
import android.widget.Toast;

import unifei.edu.br.agecom.R;

public class AdicionaCadastro extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_cadastro);

        if(getIntent().hasExtra("id"))
            LoadData();
    }

    public void salvarCadastro(View view){
        EditText edtNome = (EditText) findViewById(R.id.editNome);
        EditText edtCurso = (EditText) findViewById(R.id.editCurso);
        EditText edtLogin = (EditText) findViewById(R.id.editLogin);
        EditText edtSenha = (EditText) findViewById(R.id.editSenha);
        String nome = edtNome.getText().toString();
        String curso = edtCurso.getText().toString();
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        SaveDatabaseCadastro(nome, curso, login, senha);
    }

    public boolean SaveDatabaseCadastro(String nome, String curso, String login, String senha){
        try{
            ReminderDbHelperCadastro dbHelper = new ReminderDbHelperCadastro(this);

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ReminderDbHelperCadastro.L_NOME, nome);
            values.put(ReminderDbHelperCadastro.L_CURSO, curso);
            values.put(ReminderDbHelperCadastro.L_LOGIN, login);
            values.put(ReminderDbHelperCadastro.L_SENHA, senha);

            try{
                if(getIntent().hasExtra("id")){
                    String id = getIntent().getExtras().getString("id");
                    String where = ReminderDbHelperCadastro.L_ID + "=?";
                    String[] whereArgs = { id };
                    if(db.update(ReminderDbHelperCadastro.TABLE, values, where, whereArgs) > 0){
                        Toast.makeText(getBaseContext(), "Cadastro alterado!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                }else{
                    long returnID = db.insertOrThrow(ReminderDbHelperCadastro.TABLE, null, values);
                    if(returnID != -1){
                        Toast.makeText(getBaseContext(), "Cadastro salvo!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        finish();
                    }
                }
            }finally
            {
                db.close();
            }
            return true;
        }catch(Exception e)
        {
            Log.e("error sqlite", e.getMessage());
            Toast.makeText(getBaseContext(), "Problema no banco", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void LoadData(){
        EditText eTextNome = (EditText) findViewById(R.id.editNome);
        EditText eTextCurso = (EditText) findViewById(R.id.editCurso);
        EditText eTextLogin = (EditText) findViewById(R.id.editLogin);
        EditText eTextSenha = (EditText) findViewById(R.id.editSenha);

        String[] columns = {ReminderDbHelperCadastro.L_ID, ReminderDbHelperCadastro.L_NOME, ReminderDbHelperCadastro.L_CURSO,
                ReminderDbHelperCadastro.L_LOGIN, ReminderDbHelperCadastro.L_SENHA};
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
                    eTextNome.setText(cursor.getString(1));
                    eTextCurso.setText(cursor.getString(2));
                    eTextLogin.setText(cursor.getString(3));
                    eTextSenha.setText(cursor.getString(4));
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
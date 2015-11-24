package unifei.edu.br.agecom;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import unifei.edu.br.agecom.R;

public class LoginActivity extends AppCompatActivity {

    EditText login,senha;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydatabase = openOrCreateDatabase("agecom", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS pessoa(id INTEGER AUTO_INCREMENT PRIMARY KEY, nome VARCHAR,login VARCHAR, senha VARCHAR);");
        mydatabase.execSQL("INSERT INTO pessoa (nome, login, senha) VALUES('agecom','agecom','123');");

        login = (EditText) findViewById(R.id.editEmail);
        senha = (EditText) findViewById(R.id.editSenha);






    }

    public void logar(View v){
        String login_text = login.getText().toString();
        String senha_text= senha.getText().toString();

        Cursor resultSet = mydatabase.rawQuery("select * from pessoa where login = '" + login_text + "' and senha = '" + senha_text + "'", null);
        resultSet.moveToFirst();
        if(resultSet.getCount() !=0) {
            String nome = resultSet.getString(1);

            Toast.makeText(this, "Oi " + nome, Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{

            Toast.makeText(this, "Dados errados", Toast.LENGTH_LONG).show();
        }
    }

}


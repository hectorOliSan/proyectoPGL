package pgl.proyecto_pgl_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private EditText etMail, etCon;
    private  DBHelper db;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etMail = findViewById(R.id.etMailLog);
        etCon = findViewById(R.id.etConLog);
        db = new DBHelper(this);

        LinearLayout rl = findViewById(R.id.llLogin);
        AnimationDrawable ad = (AnimationDrawable) rl.getBackground();
        ad.setEnterFadeDuration(1500);
        ad.setExitFadeDuration(3000);
        ad.start();

        Button btnLogin = findViewById(R.id.btnISLog);
        btnLogin.setOnClickListener(view -> {
            Cursor res = db.getUsuario(etMail.getText().toString());
            if(res.getCount() != 0) {
                while(res.moveToNext()){
                    Toast t;
                    if(res.getString(4).equals(etCon.getText().toString())) {
                        Intent intent = new Intent(LoginActivity.this, ListaActivity.class);
                        Bundle b = new Bundle();
                        b.putString("ID", res.getInt(0)+"");
                        intent.putExtras(b);

                        finish();
                        startActivity(intent);
                        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);

                        t = Toast.makeText(getApplicationContext(), R.string.logC, Toast.LENGTH_SHORT);
                        t.show();

                    } else t = Toast.makeText(getApplicationContext(), R.string.logE2, Toast.LENGTH_SHORT);

                    t.show();
                }

            } else {
                Toast t = Toast.makeText(getApplicationContext(), R.string.logE, Toast.LENGTH_SHORT);
                t.show();
            }
        });

        Button btnReg = findViewById(R.id.btnRLog);
        btnReg.setOnClickListener(view -> {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
                this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });
    }

    public void Menu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_login);
        popup.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuUsuarios:
                Cursor res = db.getUsuarios();
                if(res.getCount()==0){
                    Toast.makeText(LoginActivity.this, "No hay Usuarios", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder s = new StringBuilder();
                    while(res.moveToNext()){
                        s.append(">  ").append(res.getString(3)).append("\n");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Usuarios:");
                    builder.setMessage(s.toString());
                    builder.show();
                }
                return true;

            case R.id.mnuAcerca:
                Intent intent = new Intent(getApplicationContext(), AcercaActivity.class);
                startActivity(intent);
                this.overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                return true;

            case R.id.mnuSalir:
                finishAffinity();
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
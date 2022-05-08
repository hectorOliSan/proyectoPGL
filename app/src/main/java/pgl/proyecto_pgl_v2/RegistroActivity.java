package pgl.proyecto_pgl_v2;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNom, etAp, etMail, etCon;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNom= findViewById(R.id.etNomReg);
        etAp = findViewById(R.id.etApReg);
        etMail = findViewById(R.id.etMailReg);
        etCon = findViewById(R.id.etConReg);

        db = new DBHelper(this);

        LinearLayout rl = findViewById(R.id.llReg);
        AnimationDrawable ad = (AnimationDrawable) rl.getBackground();
        ad.setEnterFadeDuration(1500);
        ad.setExitFadeDuration(3000);
        ad.start();

        Button btnReg = findViewById(R.id.btnRReg);
        btnReg.setOnClickListener(view -> {
            if(comprobar()) {
                Toast t;
                Intent intent;
                boolean b = db.addUsuario(etNom.getText().toString(),
                        etAp.getText().toString(), etMail.getText().toString(), etCon.getText().toString());
                if(b) {
                    intent = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.left_in, R.anim.left_out);

                    t = Toast.makeText(getApplicationContext(), R.string.regC, Toast.LENGTH_SHORT);

                } else {
                    t = Toast.makeText(getApplicationContext(), R.string.regE, Toast.LENGTH_SHORT);
                }
                t.show();
            }
        });
    }

    private boolean comprobar() {
        String n = etNom.getText().toString();
        String a = etAp.getText().toString();
        String m = etMail.getText().toString();
        String c = etCon.getText().toString();

        boolean b1 = false, b2=false, b3 = false, b4 = false;

        if(n.length() == 0) etNom.setError("Nombre no válido");
        else {
            etNom.setError(null);
            b1 = true;
        }

        if(a.length() == 0) etAp.setError("Apellido no válido");
        else {
            etAp.setError(null);
            b2 = true;
        }

        if(!m.endsWith("@ieselrincon.es")) etMail.setError("Debe Terminar con \"@ieselrincon.es\"");
        else {
            etMail.setError(null);
            b3=true;
        }

        String s = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
        if (!c.matches(s)) {
            etCon.setError("- Debe tener 8 o más caracteres" +
                    "\n- Debe contener mayúsculas y minúsculas" +
                    "\n- Debe contener números");
        } else {
            etCon.setError(null);
            b4=true;
        }

        return b1 && b2 && b3 && b4;
    }

    public void Back(View v) {
        finish();
        super.onBackPressed();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
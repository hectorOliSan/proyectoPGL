package pgl.proyecto_pgl_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ConfActivity extends AppCompatActivity {
    private DrawerLayout dw;
    private EditText etNomC, etApC, etMailC, etConC;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        dw = findViewById(R.id.drawer_layout_conf);

        DBHelper db = new DBHelper(this);

        TextView tvNDNombre = findViewById(R.id.tvNDNombre);
        TextView tvNDMail = findViewById(R.id.tvNDMail);

        etNomC = findViewById(R.id.etNomConf);
        etApC = findViewById(R.id.etApConf);
        etMailC = findViewById(R.id.etMailConf);
        etConC = findViewById(R.id.etConConf);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("ID") != null) {
            id = bundle.getString("ID");
            Cursor res = db.getUsuarioID(id);
            while(res.moveToNext()) {
                etNomC.setText(res.getString(1));
                etApC.setText(res.getString(2));
                etMailC.setText(res.getString(3));
                etConC.setText(res.getString(4));
                tvNDNombre.setText(res.getString(1).concat(" ").concat(res.getString(2)));
                tvNDMail.setText(res.getString(3));
            }
        }

        Button btnConfElim = findViewById(R.id.btnConfElim);
        btnConfElim.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfActivity.this);
            builder.setTitle(R.string.elim);
            builder.setMessage("¿Seguro que quieres eliminar el Usuario?");
            builder.setPositiveButton("Sí", (dialog, i) -> {
                dialog.dismiss();
                Toast t;
                boolean bo = db.delUsuario(Integer.parseInt(id));
                if(bo) {
                    Intent intent = new Intent(ConfActivity.this, LoginActivity.class);
                    Bundle b = new Bundle();
                    b.putString("ID", id);
                    intent.putExtras(b);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    t = Toast.makeText(getApplicationContext(), R.string.crearC, Toast.LENGTH_SHORT);
                } else t = Toast.makeText(getApplicationContext(), R.string.crearE, Toast.LENGTH_SHORT);
                t.show();
            });
            builder.setNegativeButton("No", (dialog, i) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        });

        Button btnConfGuardar = findViewById(R.id.btnConfGuardar);
        btnConfGuardar.setOnClickListener(view -> {
            if(comprobar()) {
                Toast t;
                Intent intent;
                boolean bo = db.upUsuario(Integer.parseInt(id), etNomC.getText().toString(),
                        etApC.getText().toString(), etMailC.getText().toString(), etConC.getText().toString());
                if(bo) {
                    intent = new Intent(ConfActivity.this, ListaActivity.class);
                    Bundle b = new Bundle();
                    b.putString("ID", id);
                    intent.putExtras(b);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    t = Toast.makeText(getApplicationContext(), R.string.actC, Toast.LENGTH_SHORT);

                } else {
                    t = Toast.makeText(getApplicationContext(), R.string.actE, Toast.LENGTH_SHORT);
                }
                t.show();
            }
        });
    }

    private boolean comprobar() {
        String n = etNomC.getText().toString();
        String a = etApC.getText().toString();
        String m = etMailC.getText().toString();
        String c = etConC.getText().toString();

        boolean b1 = false, b2=false, b3 = false, b4 = false;

        if(n.length() == 0) etNomC.setError("Nombre no válido");
        else {
            etNomC.setError(null);
            b1 = true;
        }

        if(a.length() == 0) etApC.setError("Apellido no válido");
        else {
            etApC.setError(null);
            b2 = true;
        }

        if(!m.endsWith("@ieselrincon.es")) etMailC.setError("Debe Terminar con \"@ieselrincon.es\"");
        else {
            etMailC.setError(null);
            b3=true;
        }

        String s = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
        if (!c.matches(s)) {
            etConC.setError("- Debe tener 8 o más caracteres" +
                    "\n- Debe contener mayúsculas y minúsculas" +
                    "\n- Debe contener números");
        } else {
            etConC.setError(null);
            b4=true;
        }

        return b1 && b2 && b3 && b4;
    }

    public void Abrir(View v) {
        dw.openDrawer(GravityCompat.START);
    }
    public void Cerrar(View v) {
        dw.closeDrawer(GravityCompat.START);
    }

    public void tareas(View v) {
        finishAffinity();
        this.overridePendingTransition(0, 0);
        Intent intent = new Intent(ConfActivity.this, ListaActivity.class);
        Bundle b = new Bundle();
        b.putString("ID", id);
        intent.putExtras(b);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void conf(View v) { Cerrar(v); }

    public void acercaDe(View v) {
        Cerrar(v);
        Intent intent = new Intent(ConfActivity.this, AcercaActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

    public void cerrarSesion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfActivity.this);
        builder.setTitle(R.string.cerrar);
        builder.setMessage("¿Seguro que quieres Cerrar Sesión?");
        builder.setPositiveButton("Sí", (dialog, i) -> {
            dialog.dismiss();
            finishAffinity();
            Intent intent = new Intent(ConfActivity.this, LoginActivity.class);
            startActivity(intent);
            this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });
        builder.setNegativeButton("No", (dialog, i) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void MenuLista(View v) {}
}
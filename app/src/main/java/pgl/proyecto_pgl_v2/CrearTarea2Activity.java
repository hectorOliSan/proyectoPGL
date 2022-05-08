package pgl.proyecto_pgl_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrearTarea2Activity extends AppCompatActivity {
    private EditText etDes;
    private String id, tit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea2);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("ID");
        tit = bundle.getString("TIT");

        etDes = findViewById(R.id.etDesCre);
        Button btnSig2 = findViewById(R.id.btnSig2);

        btnSig2.setOnClickListener(view -> {
            if(!etDes.getText().toString().equals("")) {
                Intent intent = new Intent(CrearTarea2Activity.this, CrearTarea3Activity.class);
                Bundle b = new Bundle();
                b.putString("ID", id);
                b.putString("TIT", tit);
                b.putString("DES", etDes.getText().toString());
                intent.putExtras(b);
                startActivity(intent);
                this.overridePendingTransition(R.anim.left_in, R.anim.left_out);

            } else {
                Toast t = Toast.makeText(getApplicationContext(), R.string.campos, Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    public void Back(View v) {
        finish();
        super.onBackPressed();
        this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
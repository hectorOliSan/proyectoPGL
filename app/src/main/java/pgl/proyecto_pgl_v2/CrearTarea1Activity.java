package pgl.proyecto_pgl_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrearTarea1Activity extends AppCompatActivity {
    private EditText etTit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea1);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("ID");

        etTit = findViewById(R.id.etTitCre);
        Button btnSig1 = findViewById(R.id.btnSig1);


        btnSig1.setOnClickListener(view -> {
            if(!etTit.getText().toString().equals("")) {
                Intent intent =
                        new Intent(CrearTarea1Activity.this, CrearTarea2Activity.class);

                Bundle b = new Bundle();
                b.putString("ID", id);
                b.putString("TIT", etTit.getText().toString());

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
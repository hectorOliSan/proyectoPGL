package pgl.proyecto_pgl_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MostrarTareaActivity extends AppCompatActivity {
    private Tarea t;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_tarea);

        DBHelper db = new DBHelper(this);

        Bundle bundle =getIntent().getExtras();
        id = bundle.getString("ID");
        t = (Tarea) bundle.getSerializable("TAREA");

        TextView tvMosTit = findViewById(R.id.tvMosTit);
        TextView tvMosDes = findViewById(R.id.tvMosDes);
        TextView tvMosFe = findViewById(R.id.tvMosFe);
        Button btnMosElim = findViewById(R.id.btnMosElim);
        Button btnMosEdit = findViewById(R.id.btnMosEdit);

        tvMosTit.setText(t.getTitulo());
        tvMosDes.setText(t.getDescripcion());
        tvMosFe.setText(t.getFecha());

        btnMosElim.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MostrarTareaActivity.this);
            builder.setTitle(R.string.elim);
            builder.setMessage("¿Seguro que quieres eliminar la Tarea?");

            builder.setPositiveButton("Sí", (dialog, i) -> {
                dialog.dismiss();
                Toast toast;
                boolean bo = db.delTarea(t.getIdTarea());
                if(bo) {
                    Intent intent = new Intent(MostrarTareaActivity.this, ListaActivity.class);
                    Bundle b = new Bundle();
                    b.putString("ID", id);
                    intent.putExtras(b);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    toast = Toast.makeText(getApplicationContext(), R.string.elimC, Toast.LENGTH_SHORT);
                } else toast = Toast.makeText(getApplicationContext(), R.string.elimE, Toast.LENGTH_SHORT);
                toast.show();
            });
            builder.setNegativeButton("No", (dialog, i) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();
        });

        btnMosEdit.setOnClickListener(view -> {
            Intent intent = new Intent(MostrarTareaActivity.this, EditarTareaActivity.class);
            Bundle b = new Bundle();
            b.putString("ID", id);
            b.putSerializable("TAREA", t);
            intent.putExtras(b);
            startActivity(intent);
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });
    }

    public void Back(View v) {
        finish();
        super.onBackPressed();
        this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
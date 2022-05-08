package pgl.proyecto_pgl_v2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarTareaActivity extends AppCompatActivity {
    private EditText etTit, etDes, etFe;

    private Tarea t;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        DBHelper db = new DBHelper(this);

        Bundle bundle =getIntent().getExtras();
        id = bundle.getString("ID");
        t = (Tarea) bundle.getSerializable("TAREA");

        etTit = findViewById(R.id.etTitEdit);
        etDes = findViewById(R.id.etDesEdit);
        etFe = findViewById(R.id.etFeEdit);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        etTit.setText(t.getTitulo());
        etDes.setText(t.getDescripcion());
        etFe.setText(t.getFecha());

        // DATEPICKER
        etFe.setOnClickListener(view -> {
            int day = Integer.parseInt(etFe.getText().toString().substring(0,2));
            int month = Integer.parseInt(etFe.getText().toString().substring(3,5));
            int year = Integer.parseInt(etFe.getText().toString().substring(6));

            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            DatePickerDialog dpd = new DatePickerDialog(EditarTareaActivity.this,
                    (datePicker, nYear, nMonth, nDay) ->
                            etFe.setText(String.format("%02d/%02d/%04d", nDay, (nMonth+1), nYear)), year,month-1,day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dpd.show();
        });


        btnGuardar.setOnClickListener(view -> {
            Toast toast;
            if(comprobar()){
                boolean bo = db.upTarea(t.getIdTarea(),Integer.parseInt(id), etTit.getText().toString(),
                        etDes.getText().toString(), etFe.getText().toString(),t.getFav());

                if(bo) {
                    Intent intent = new Intent(EditarTareaActivity.this, ListaActivity.class);
                    Bundle b = new Bundle();
                    b.putString("ID", id);
                    intent.putExtras(b);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.right_in, R.anim.right_out);

                    toast = Toast.makeText(getApplicationContext(), R.string.editarC, Toast.LENGTH_SHORT);

                } else toast = Toast.makeText(getApplicationContext(), R.string.editarE, Toast.LENGTH_SHORT);

            } else toast = Toast.makeText(getApplicationContext(), R.string.campos, Toast.LENGTH_SHORT);
            toast.show();
        });
    }


    private boolean comprobar() {
        return (!etTit.getText().toString().equals("")) && (!etDes.getText().toString().equals(""))
                && (!etFe.getText().toString().equals(""));
    }

    public void Back(View v) {
        finish();
        super.onBackPressed();
        this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
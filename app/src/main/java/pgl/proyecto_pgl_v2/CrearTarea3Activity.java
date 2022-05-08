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

import java.util.Calendar;

public class CrearTarea3Activity extends AppCompatActivity {
    private EditText etFe;
    private String id, tit, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea3);

        DBHelper db = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("ID");
        tit = bundle.getString("TIT");
        des = bundle.getString("DES");

        etFe = findViewById(R.id.etFeCre);
        Button btnSig3 = findViewById(R.id.btnSig3);

        // DATEPICKER
        etFe.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            @SuppressLint("DefaultLocale")
            DatePickerDialog dpd = new DatePickerDialog(CrearTarea3Activity.this,
                    (datePicker, nYear, nMonth, nDay) ->
                            etFe.setText(String.format("%02d/%02d/%04d", nDay, (nMonth+1), nYear)), year,month,day);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dpd.show();
        });


        btnSig3.setOnClickListener(view -> {
            Toast t;
            if(!etFe.getText().toString().equals("")) {
                boolean bo = db.addTarea(Integer.parseInt(id), tit, des, etFe.getText().toString(),0);

                if(bo) {
                    Intent intent = new Intent(CrearTarea3Activity.this, ListaActivity.class);
                    Bundle b = new Bundle();
                    b.putString("ID", id);
                    intent.putExtras(b);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.right_in, R.anim.right_out);

                    t = Toast.makeText(getApplicationContext(), R.string.crearC, Toast.LENGTH_SHORT);

                } else t = Toast.makeText(getApplicationContext(), R.string.crearE, Toast.LENGTH_SHORT);
            } else {
                t = Toast.makeText(getApplicationContext(),  R.string.campos, Toast.LENGTH_SHORT);
            }
            t.show();
        });
    }

    public void Back(View v) {
        finish();
        super.onBackPressed();
        this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
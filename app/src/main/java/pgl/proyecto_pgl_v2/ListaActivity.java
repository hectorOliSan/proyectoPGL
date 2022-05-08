package pgl.proyecto_pgl_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ListaActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private DrawerLayout dw;
    private DBHelper db;
    private RecyclerView rv;
    private String id = "";
    private ArrayList<Tarea> alTareas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        dw = findViewById(R.id.drawer_layout);
        //dw.openDrawer(GravityCompat.START);

        TextView tvNDNombre = findViewById(R.id.tvNDNombre);
        TextView tvNDMail = findViewById(R.id.tvNDMail);

        rv = findViewById(R.id.rvTareas);
        rv.setHasFixedSize(true);

        FloatingActionButton fb = findViewById(R.id.fbTarea);
        fb.setColorFilter(Color.WHITE);

        db = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("ID") != null) {
            id = bundle.getString("ID");
            Cursor res = db.getUsuarioID(id);
            while(res.moveToNext()) {
                String nom = res.getString(1);
                String ap = res.getString(2);
                String mail = res.getString(3);
                tvNDNombre.setText(nom.concat(" ").concat(ap));
                tvNDMail.setText(mail);
            }
        }

        if (bundle.getSerializable("TAREAS") == null) {
            Cursor res = db.getTareas(id);
            while(res.moveToNext()){
                alTareas.add(new Tarea(res.getInt(0), res.getString(2), res.getString(3), res.getString(4), res.getInt(5)));
            }
        } else alTareas = (ArrayList<Tarea>) bundle.getSerializable("TAREAS");

        AdaptadorLista al = new AdaptadorLista(alTareas);
        al.setOnClickListener(view -> {
            int i = rv.getChildAdapterPosition(view);
            Log.d("test", alTareas.get(i).toString());

            LottieAnimationView animationView = findViewById(R.id.animationView);
            animationView.setOnClickListener(view1 -> {
                if(animationView.getProgress()==0) {
                    animationView.playAnimation();
                    db.upTarea(alTareas.get(i).getIdTarea(), Integer.parseInt(id), alTareas.get(i).getTitulo(),
                            alTareas.get(i).getDescripcion(), alTareas.get(i).getFecha(),1);
                } else {
                    animationView.setFrame(0);
                    db.upTarea(alTareas.get(i).getIdTarea(), Integer.parseInt(id), alTareas.get(i).getTitulo(),
                            alTareas.get(i).getDescripcion(), alTareas.get(i).getFecha(),0);
                }
                rellenar();
                actualizarLista();
            });

            Intent intent = new Intent(ListaActivity.this, MostrarTareaActivity.class);
            Bundle b = new Bundle();
            b.putString("ID", id);
            b.putSerializable("TAREA", alTareas.get(i));
            intent.putExtras(b);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });

        rv.setAdapter(al);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        fb.setOnClickListener(view -> {
            Intent intent = new Intent(ListaActivity.this, CrearTarea1Activity.class);
            Bundle b = new Bundle();
            b.putString("ID", id);
            intent.putExtras(b);
            startActivity(intent);
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });
    }

    public void Abrir(View v) { dw.openDrawer(GravityCompat.START); }
    public void Cerrar(View v) { dw.closeDrawer(GravityCompat.START); }
    public void tareas(View v) { Cerrar(v); }

    public void conf(View v) {
        finishAffinity();
        Intent intent = new Intent(ListaActivity.this, ConfActivity.class);
        Bundle b = new Bundle();
        b.putString("ID", id);
        intent.putExtras(b);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void acercaDe(View v) {
        Cerrar(v);
        Intent intent = new Intent(ListaActivity.this, AcercaActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }

    public void cerrarSesion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
        builder.setTitle(R.string.cerrar);
        builder.setMessage("¿Seguro que quieres Cerrar Sesión?");
        builder.setPositiveButton("Sí", (dialog, i) -> {
            dialog.dismiss();
            finishAffinity();
            Intent intent = new Intent(ListaActivity.this, LoginActivity.class);
            startActivity(intent);
            this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });
        builder.setNegativeButton("No", (dialog, i) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void MenuLista(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_lista);
        popup.show();
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuP:
                rellenar();
                actualizarLista();
                return true;

            case R.id.mnuAZ:
                rellenar();
                alTareas.sort(Comparator.comparing(Tarea::getTitulo));
                actualizarLista();
                return true;

            case R.id.mnuZA:
                rellenar();
                alTareas.sort((t1, t2) -> t2.getTitulo().compareTo(t1.getTitulo()));
                actualizarLista();
                return true;

            case R.id.mnuNew:
                rellenar();
                alTareas.sort((t1, t2) -> {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try { return Objects.requireNonNull(sdf.parse(t2.getFecha())).compareTo(sdf.parse(t1.getFecha()));
                    } catch (ParseException e) {e.printStackTrace();}
                    return 0;
                });
                actualizarLista();
                return true;

            case R.id.mnuOld:
                rellenar();
                alTareas.sort((t1, t2) -> {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try { return Objects.requireNonNull(sdf.parse(t1.getFecha())).compareTo(sdf.parse(t2.getFecha()));
                    } catch (ParseException e) {e.printStackTrace();}
                    return 0;
                });
                actualizarLista();
                return true;

            case R.id.mnuFav:
                rellenar();
                for (int i=0; i<alTareas.size(); i++) {
                    if(alTareas.get(i).getFav()==0){
                        alTareas.remove(i);
                        i--;
                    }
                }

                actualizarLista();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void rellenar() {
        alTareas = new ArrayList<>();
        Cursor res = db.getTareas(id);
        while(res.moveToNext()) {
            alTareas.add(new Tarea(res.getInt(0), res.getString(2), res.getString(3), res.getString(4), res.getInt(5)));
        }
    }

    private void actualizarLista() {
        Bundle b = new Bundle();
        b.putString("ID", id);
        b.putSerializable("TAREAS", alTareas);

        finish();
        startActivity(getIntent().putExtras(b));
        this.overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }
}
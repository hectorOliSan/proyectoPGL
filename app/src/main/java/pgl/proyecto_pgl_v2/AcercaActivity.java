package pgl.proyecto_pgl_v2;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AcercaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        LinearLayout rl = findViewById(R.id.llAcerca);
        AnimationDrawable ad = (AnimationDrawable) rl.getBackground();
        ad.setEnterFadeDuration(1500);
        ad.setExitFadeDuration(3000);
        ad.start();
    }

    public void Back(View v) {
        finish();
        super.onBackPressed();
        this.overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }
}
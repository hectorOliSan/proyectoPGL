package pgl.proyecto_pgl_v2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.TareasViewHolder> implements View.OnClickListener {
    private View.OnClickListener listener;
    private final ArrayList<Tarea> alTareas;

    public static class TareasViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitulo, tvFecha;
        private final LottieAnimationView lottie;

        public TareasViewHolder(View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            lottie = itemView.findViewById(R.id.animationView);
        }

        @SuppressLint("SetTextI18n")
        public void bindTarea(Tarea t) {
            tvTitulo.setText(t.getTitulo());
            tvFecha.setText(t.getFecha());
            if(t.getFav()==1) lottie.setProgress(1);
        }
    }

    public AdaptadorLista(ArrayList<Tarea> alTareas) { this.alTareas = alTareas; }

    @NonNull @Override
    public TareasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_tarea, viewGroup, false);
        itemView.setOnClickListener(this);
        return new TareasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TareasViewHolder viewHolder, int pos) {
        Tarea item = alTareas.get(pos);
        viewHolder.bindTarea(item);
    }

    @Override
    public int getItemCount() {
        return alTareas.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null) listener.onClick(view);
    }
}

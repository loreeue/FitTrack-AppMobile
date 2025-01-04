package com.ldm.practica2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.Toast;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private final List<Actividad> actividades;
    private final Context context;
    private final ActividadDao actividadDao;
    private final ProgresoFragment fragment;

    public ActividadAdapter(List<Actividad> actividades, Context context, ActividadDao actividadDao, ProgresoFragment fragment) {
        this.actividades = actividades;
        this.context = context;
        this.actividadDao = actividadDao;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_actividad, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {
        Actividad actividad = actividades.get(position);

        holder.textViewTipo.setText(actividad.getTipo());
        holder.textViewDistancia.setText("Distancia: " + actividad.getDistancia() + " km");

        holder.textViewTiempo.setText("Tiempo: " + actividad.getTiempo() + " min");
        holder.btnEliminar.setOnClickListener(v -> eliminarActividad(position));

        // Mostrar datos adicionales para ciclismo
        if (actividad.getTipo().equals("Ciclismo")) {
            holder.textViewVelocidadMaxima.setVisibility(View.VISIBLE);
            holder.textViewVelocidadMaxima.setText("Velocidad Máxima: " + actividad.getVelocidadMaxima() + " km/h");
        } else {
            holder.textViewVelocidadMaxima.setVisibility(View.GONE);
        }

        // Mostrar datos adicionales para natación
        if (actividad.getTipo().equals("Nadar")) {
            holder.textViewTipoAgua.setVisibility(View.VISIBLE);
            holder.textViewTipoAgua.setText("Tipo de Agua: " + actividad.getTipoAgua());
        } else {
            holder.textViewTipoAgua.setVisibility(View.GONE);
        }

        holder.btnEliminar.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    AppDatabase db = AppDatabase.getInstance(holder.itemView.getContext());
                    db.actividadDao().eliminarActividad(actividad);

                    // Actualizar la UI después de eliminar
                    ((AppCompatActivity) holder.itemView.getContext()).runOnUiThread(() -> {
                        actividades.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(holder.itemView.getContext(), "Actividad eliminada", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    Log.e("ActividadAdapter", "Error al eliminar la actividad: " + e.getMessage());
                }
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return (actividades != null) ? actividades.size() : 0;
    }

    public void eliminarActividad(int position) {
        Actividad actividadAEliminar = actividades.get(position);

        new Thread(() -> {
            actividadDao.eliminarActividad(actividadAEliminar);

            fragment.getActivity().runOnUiThread(() -> {
                actividades.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, actividades.size());

                fragment.actualizarListaGlobal();
            });
        }).start();
    }

    public static class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTipo;
        TextView textViewDistancia;
        TextView textViewTiempo;
        TextView textViewVelocidadMaxima;
        TextView textViewTipoAgua;
        ImageButton btnEliminar;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTipo = itemView.findViewById(R.id.textViewTipo);
            textViewDistancia = itemView.findViewById(R.id.textViewDistancia);
            textViewTiempo = itemView.findViewById(R.id.textViewTiempo);
            textViewVelocidadMaxima = itemView.findViewById(R.id.textViewVelocidadMaxima);
            textViewTipoAgua = itemView.findViewById(R.id.textViewTipoAgua);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
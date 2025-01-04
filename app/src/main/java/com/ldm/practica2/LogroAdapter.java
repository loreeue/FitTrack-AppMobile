package com.ldm.practica2;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LogroAdapter extends RecyclerView.Adapter<LogroAdapter.LogroViewHolder> {

    private final List<Logro> logros;
    private final Context context;

    public LogroAdapter(Context context, List<Logro> logros) {
        this.context = context;
        this.logros = logros;
    }

    @NonNull
    @Override
    public LogroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logro, parent, false);
        return new LogroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogroViewHolder holder, int position) {
        Logro logro = logros.get(position);

        holder.textTituloLogro.setText(logro.getTitulo());
        holder.textDescripcionLogro.setText(logro.getDescripcion());

        if (logro.getIcono() != 0) {
            holder.iconLogro.setImageResource(logro.getIcono());
        }

        holder.playButton.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, logro.getSonido());
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        });
    }

    @Override
    public int getItemCount() {
        return logros.size();
    }

    public static class LogroViewHolder extends RecyclerView.ViewHolder {
        TextView textTituloLogro, textDescripcionLogro;
        ImageView iconLogro;
        ImageButton playButton;

        public LogroViewHolder(@NonNull View itemView) {
            super(itemView);
            textTituloLogro = itemView.findViewById(R.id.textTituloLogro);
            textDescripcionLogro = itemView.findViewById(R.id.textDescripcionLogro);
            iconLogro = itemView.findViewById(R.id.iconLogro);
            playButton = itemView.findViewById(R.id.playButton);
        }
    }
}
package com.ldm.practica2;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class LogrosFragment extends Fragment {

    private RecyclerView recyclerViewLogros;
    private Button btnVolver;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;

    public LogrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logros, container, false);

        btnVolver = view.findViewById(R.id.btnVolver);
        recyclerViewLogros = view.findViewById(R.id.recyclerViewLogros);
        recyclerViewLogros.setLayoutManager(new LinearLayoutManager(requireContext()));

        new Thread(() -> {
            List<Logro> listaLogros = obtenerLogros();
            requireActivity().runOnUiThread(() -> {
                LogroAdapter adapter = new LogroAdapter(requireContext(), listaLogros);
                recyclerViewLogros.setAdapter(adapter);
            });
        }).start();

        btnVolver.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        return view;
    }

    private List<Logro> obtenerLogros() {
        List<Logro> logros = new ArrayList<>();

        // Logros para correr
        logros.add(new Logro("Primer 5 km corriendo", "Has corrido tu primera carrera de 5 km. ¡Enhorabuena!", R.drawable.logro1, R.raw.logro1));
        logros.add(new Logro("Primer maratón", "¡Felicidades por completar tu primer maratón!", R.drawable.logro2, R.raw.logro2));

        // Logros para ciclismo
        logros.add(new Logro("Primera Ruta de 50 km", "Has completado tu primera ruta de 50 km en ciclismo.", R.drawable.logro3, R.raw.logro3));
        logros.add(new Logro("Velocidad Máxima", "Alcanzaste una velocidad de 40 km/h. ¡Increíble rapidez sobre la bici!", R.drawable.logro4, R.raw.logro4));

        // Logros para natación
        logros.add(new Logro("Mejor tiempo en natación", "Has mejorado tu tiempo nadando 500 m.", R.drawable.logro5, R.raw.logro5));
        logros.add(new Logro("Reto del Mar Abierto", "Has nadado en aguas abiertas por primera vez. ¡Superaste el reto del mar y la corriente!", R.drawable.logro6, R.raw.logro6));
        return logros;
    }
}
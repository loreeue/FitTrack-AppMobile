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
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

public class ProgresoFragment extends Fragment {

    private Spinner spinnerTipoActividad;
    private ActividadDao actividadDao;
    private List<Actividad> listaActividades = new ArrayList<>();
    private Button btnVolver;
    private RecyclerView recyclerViewActividades;
    private ActividadAdapter actividadAdapter;

    public ProgresoFragment() {}

    public static ProgresoFragment newInstance() {
        return new ProgresoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actividadDao = AppDatabase.getInstance(requireContext()).actividadDao();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progreso, container, false);

        recyclerViewActividades = view.findViewById(R.id.recyclerViewActividades);
        btnVolver = view.findViewById(R.id.btnVolver);
        spinnerTipoActividad = view.findViewById(R.id.spinnerTipoActividad);

        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnVolver.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        setupSpinner();
        obtenerActividades();

        return view;
    }

    private void obtenerActividades() {
        new Thread(() -> {
            List<Actividad> actividades = actividadDao.obtenerTodasActividades();

            requireActivity().runOnUiThread(() -> {
                listaActividades.clear();
                listaActividades.addAll(actividades);
                if (actividadAdapter == null) {
                    actividadAdapter = new ActividadAdapter(listaActividades, requireContext(), actividadDao, this);
                    recyclerViewActividades.setAdapter(actividadAdapter);
                } else {
                    actividadAdapter.notifyDataSetChanged();
                }
            });
        }).start();
    }

    private void setupSpinner() {
        String[] tiposActividades = {"Todas", "Correr", "Nadar", "Ciclismo"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.estilo_letra, tiposActividades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoActividad.setAdapter(adapter);

        spinnerTipoActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoSeleccionado = tiposActividades[position];
                filtrarActividades(tipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    private void filtrarActividades(String tipoActividad) {
        new Thread(() -> {
            List<Actividad> actividadesFiltradas;

            if ("Todas".equalsIgnoreCase(tipoActividad)) {
                actividadesFiltradas = actividadDao.obtenerTodasActividades();
            }
            else {
                actividadesFiltradas = actividadDao.obtenerActividadesPorTipo(tipoActividad);
            }

            requireActivity().runOnUiThread(() -> {
                if (listaActividades == null) {
                    listaActividades = new ArrayList<>();
                }
                listaActividades.clear();
                listaActividades.addAll(actividadesFiltradas);

                if (actividadAdapter != null) {
                    actividadAdapter.notifyDataSetChanged();
                }
            });
        }).start();
    }

    public void actualizarListaGlobal() {
        new Thread(() -> {
            List<Actividad> nuevasActividades = actividadDao.obtenerTodasActividades();
            requireActivity().runOnUiThread(() -> {
                listaActividades.clear();
                listaActividades.addAll(nuevasActividades);
                actividadAdapter.notifyDataSetChanged();
            });
        }).start();
    }
}
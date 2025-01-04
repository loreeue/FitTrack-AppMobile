package com.ldm.practica2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class RegistroFragment extends Fragment {

    private Spinner spinnerTipoActividad, spinnerTipoAgua;
    private EditText editTextDistancia, editTextTiempo, editTextVelocidad;
    private ProgressBar progressBar;
    private Button btnGuardarActividad, btnVolver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        spinnerTipoActividad = view.findViewById(R.id.spinnerTipoActividad);
        editTextDistancia = view.findViewById(R.id.editTextDistancia);
        editTextTiempo = view.findViewById(R.id.editTextTiempo);
        editTextVelocidad = view.findViewById(R.id.editTextVelocidad);
        spinnerTipoAgua = view.findViewById(R.id.spinnerTipoAgua);
        btnGuardarActividad = view.findViewById(R.id.btnGuardarActividad);
        btnVolver = view.findViewById(R.id.btnVolver);
        progressBar = view.findViewById(R.id.progressBar);

        setupSpinners();

        btnGuardarActividad.setOnClickListener(v -> {
            try {
                guardarActividad();
            } catch (Exception e) {
                Log.e("RegistroFragment", "Error al registrar actividad: " + e.getMessage());
                Toast.makeText(requireContext(), "Error al registrar la actividad", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.popBackStack();
        });

        return view;
    }

    private void setupSpinners() {
        String[] tiposActividades = {"Correr", "Nadar", "Ciclismo"};
        ArrayAdapter<String> adapterTipoActividad = new ArrayAdapter<>(requireContext(), R.layout.estilo_letra, tiposActividades);
        adapterTipoActividad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoActividad.setAdapter(adapterTipoActividad);

        String[] tiposAgua = {"Piscina", "Mar"};
        ArrayAdapter<String> adapterTipoAgua = new ArrayAdapter<>(requireContext(), R.layout.estilo_letra, tiposAgua);
        adapterTipoAgua.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAgua.setAdapter(adapterTipoAgua);

        spinnerTipoActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoActividad = parent.getItemAtPosition(position).toString();
                if (tipoActividad.equals("Ciclismo")) {
                    editTextVelocidad.setVisibility(View.VISIBLE);
                    spinnerTipoAgua.setVisibility(View.GONE);
                } else if (tipoActividad.equals("Nadar")) {
                    spinnerTipoAgua.setVisibility(View.VISIBLE);
                    editTextVelocidad.setVisibility(View.GONE);
                } else {
                    editTextVelocidad.setVisibility(View.GONE);
                    spinnerTipoAgua.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void guardarActividad() {
        String tipoActividad = spinnerTipoActividad.getSelectedItem().toString();
        String distanciaStr = editTextDistancia.getText().toString();
        String tiempoStr = editTextTiempo.getText().toString();

        if (distanciaStr.isEmpty() || tiempoStr.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        double distancia;
        try {
            distancia = Double.parseDouble(distanciaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Distancia inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        double velocidadMaxima = 0;
        String tipoAgua = "";

        if (tipoActividad.equals("Ciclismo")) {
            String velocidadStr = editTextVelocidad.getText().toString();
            velocidadMaxima = velocidadStr.isEmpty() ? 0 : Double.parseDouble(velocidadStr);
        }

        if (tipoActividad.equals("Nadar")) {
            tipoAgua = spinnerTipoAgua.getSelectedItem().toString();
        }

        Actividad nuevaActividad = new Actividad(tipoActividad, distancia, tiempoStr, velocidadMaxima, tipoAgua);

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            db.actividadDao().insertarActividad(nuevaActividad);

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Actividad guardada exitosamente", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
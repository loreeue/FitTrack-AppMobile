package com.ldm.practica2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2;

    public HomeFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnRegistro).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_registro)
        );

        view.findViewById(R.id.btnProgreso).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_progreso)
        );

        view.findViewById(R.id.btnLogros).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_logros)
        );

        Button btnHelp = view.findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), HelpActivity.class);
            startActivity(intent);
            MediaPlayerSingleton.getInstance().initialize(getContext(), R.raw.help);
            MediaPlayerSingleton.getInstance().play();
        });
    }
}
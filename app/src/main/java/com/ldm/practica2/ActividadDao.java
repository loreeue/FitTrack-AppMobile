package com.ldm.practica2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ActividadDao {

    @Insert
    void insertarActividad(Actividad actividad);

    @Query("SELECT * FROM actividades")
    List<Actividad> obtenerTodasActividades();

    @Query("SELECT * FROM actividades WHERE tipo = :tipoActividad")
    List<Actividad> obtenerActividadesPorTipo(String tipoActividad);

    @Delete
    void eliminarActividad(Actividad actividad);
}
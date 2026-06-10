package es.ies.puerto.service;

import es.ies.puerto.entity.Actividad;
import es.ies.puerto.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    public Optional<Actividad> findById(Integer id) {
        return actividadRepository.findById(id);
    }

    public Actividad save(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    public void deleteById(Integer id) {
        actividadRepository.deleteById(id);
    }

    public Actividad update(Integer id, Actividad actividadDetails) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        actividad.setNombre(actividadDetails.getNombre());
        actividad.setTipoActividad(actividadDetails.getTipoActividad());
        actividad.setDuracion(actividadDetails.getDuracion());
        actividad.setPrecio(actividadDetails.getPrecio());
        actividad.setPlazasMaximas(actividadDetails.getPlazasMaximas());
        actividad.setPlazasOcupadas(actividadDetails.getPlazasOcupadas());
        return actividadRepository.save(actividad);
    }
}

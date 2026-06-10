package es.ies.puerto.service;

import es.ies.puerto.entity.Incidencia;
import es.ies.puerto.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    public List<Incidencia> findAll() {
        return incidenciaRepository.findAll();
    }

    public Optional<Incidencia> findById(Integer id) {
        return incidenciaRepository.findById(id);
    }

    public Incidencia save(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public void deleteById(Integer id) {
        incidenciaRepository.deleteById(id);
    }

    public Incidencia update(Integer id, Incidencia incidenciaDetails) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));
        incidencia.setIdUsuario(incidenciaDetails.getIdUsuario());
        incidencia.setAsunto(incidenciaDetails.getAsunto());
        incidencia.setDescripcion(incidenciaDetails.getDescripcion());
        incidencia.setFecha(incidenciaDetails.getFecha());
        incidencia.setEstado(incidenciaDetails.getEstado());
        return incidenciaRepository.save(incidencia);
    }
}

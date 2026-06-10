package es.ies.puerto.service;

import es.ies.puerto.entity.Reserva;
import es.ies.puerto.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Integer id) {
        return reservaRepository.findById(id);
    }

    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void deleteById(Integer id) {
        reservaRepository.deleteById(id);
    }

    public Reserva update(Integer id, Reserva reservaDetails) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setIdUsuario(reservaDetails.getIdUsuario());
        reserva.setIdActividad(reservaDetails.getIdActividad());
        reserva.setFecha(reservaDetails.getFecha());
        reserva.setEstado(reservaDetails.getEstado());
        return reservaRepository.save(reserva);
    }
}

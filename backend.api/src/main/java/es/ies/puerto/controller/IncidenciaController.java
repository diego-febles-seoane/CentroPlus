package es.ies.puerto.controller;

import es.ies.puerto.entity.Incidencia;
import es.ies.puerto.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = "*")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<Incidencia>> getAllIncidencias() {
        return ResponseEntity.ok(incidenciaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> getIncidenciaById(@PathVariable Integer id) {
        return incidenciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Incidencia> createIncidencia(@Valid @RequestBody Incidencia incidencia) {
        Incidencia savedIncidencia = incidenciaService.save(incidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncidencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> updateIncidencia(@PathVariable Integer id, @Valid @RequestBody Incidencia incidencia) {
        try {
            Incidencia updatedIncidencia = incidenciaService.update(id, incidencia);
            return ResponseEntity.ok(updatedIncidencia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncidencia(@PathVariable Integer id) {
        incidenciaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

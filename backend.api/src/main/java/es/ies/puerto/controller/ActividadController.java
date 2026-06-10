package es.ies.puerto.controller;

import es.ies.puerto.entity.Actividad;
import es.ies.puerto.service.ActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@CrossOrigin(origins = "*")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping
    public ResponseEntity<List<Actividad>> getAllActividades() {
        return ResponseEntity.ok(actividadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actividad> getActividadById(@PathVariable Integer id) {
        return actividadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Actividad> createActividad(@Valid @RequestBody Actividad actividad) {
        Actividad savedActividad = actividadService.save(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActividad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> updateActividad(@PathVariable Integer id, @Valid @RequestBody Actividad actividad) {
        try {
            Actividad updatedActividad = actividadService.update(id, actividad);
            return ResponseEntity.ok(updatedActividad);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActividad(@PathVariable Integer id) {
        actividadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

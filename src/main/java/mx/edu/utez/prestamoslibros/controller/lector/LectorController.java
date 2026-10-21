package mx.edu.utez.prestamoslibros.controller.lector;

import jakarta.validation.Valid;
import mx.edu.utez.prestamoslibros.controller.lector.dto.RequestLectorDTO;
import mx.edu.utez.prestamoslibros.model.lector.Lector;
import mx.edu.utez.prestamoslibros.service.lector.LectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lector")
@CrossOrigin({"*"})
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping
    public ResponseEntity<List<Lector>> getAll() {
        return ResponseEntity.ok(lectorService.findAll());
    }

    @PostMapping
    public ResponseEntity<Lector> create(@RequestBody @Valid RequestLectorDTO dto) {
        return ResponseEntity.status(201).body(lectorService.save(dto));
    }

    @PutMapping("/{id}/rehabilitar")
    public ResponseEntity<Lector> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(id));
    }
}
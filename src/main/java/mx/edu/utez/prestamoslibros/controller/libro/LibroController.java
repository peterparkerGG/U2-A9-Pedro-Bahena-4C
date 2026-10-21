package mx.edu.utez.prestamoslibros.controller.libro;

import jakarta.validation.Valid;
import mx.edu.utez.prestamoslibros.controller.libro.dto.RequestLibroDTO;
import mx.edu.utez.prestamoslibros.model.libro.Libro;
import mx.edu.utez.prestamoslibros.service.libro.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
@CrossOrigin({"*"})
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }

    @PostMapping
    public ResponseEntity<Libro> create(@RequestBody @Valid RequestLibroDTO dto) {
        return ResponseEntity.status(201).body(libroService.save(dto));
    }
}
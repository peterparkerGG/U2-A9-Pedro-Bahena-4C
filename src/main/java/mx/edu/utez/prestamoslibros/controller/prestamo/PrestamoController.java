package mx.edu.utez.prestamoslibros.controller.prestamo;

import jakarta.validation.Valid;
import mx.edu.utez.prestamoslibros.controller.prestamo.dto.RequestDevolverLibroDTO;
import mx.edu.utez.prestamoslibros.controller.prestamo.dto.RequestRenovarPrestamoDTO;
import mx.edu.utez.prestamoslibros.controller.prestamo.dto.RequestSolicitarPrestamoDTO;
import mx.edu.utez.prestamoslibros.model.prestamo.Prestamo;
import mx.edu.utez.prestamoslibros.service.prestamo.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamo")
@CrossOrigin({"*"})
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody @Valid RequestSolicitarPrestamoDTO dto) {
        return ResponseEntity.status(201).body(prestamoService.solicitarPrestamo(dto));
    }

    @PutMapping("/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@RequestBody @Valid RequestDevolverLibroDTO dto) {
        return ResponseEntity.ok(prestamoService.devolverLibro(dto));
    }

    @PutMapping("/renovar")
    public ResponseEntity<Prestamo> renovarPrestamo(@RequestBody @Valid RequestRenovarPrestamoDTO dto) {
        return ResponseEntity.ok(prestamoService.renovarPrestamo(dto));
    }

    @GetMapping("/lector/{lectorId}/activos")
    public ResponseEntity<List<Prestamo>> listarPrestamosActivosLector(@PathVariable Long lectorId) {
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosLector(lectorId));
    }
}
package mx.edu.utez.prestamoslibros.service.prestamo;

import jakarta.transaction.Transactional;
import mx.edu.utez.prestamoslibros.controller.prestamo.dto.RequestDevolverLibroDTO;
import mx.edu.utez.prestamoslibros.controller.prestamo.dto.RequestRenovarPrestamoDTO;
import mx.edu.utez.prestamoslibros.controller.prestamo.dto.RequestSolicitarPrestamoDTO;
import mx.edu.utez.prestamoslibros.exception.CustomException;
import mx.edu.utez.prestamoslibros.model.lector.Lector;
import mx.edu.utez.prestamoslibros.model.libro.Libro;
import mx.edu.utez.prestamoslibros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.prestamoslibros.model.prestamo.Prestamo;
import mx.edu.utez.prestamoslibros.repository.lector.LectorRepository;
import mx.edu.utez.prestamoslibros.repository.libro.LibroRepository;
import mx.edu.utez.prestamoslibros.repository.prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LectorRepository lectorRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Prestamo solicitarPrestamo(RequestSolicitarPrestamoDTO dto) {
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new CustomException("El lector con ID " + dto.getLectorId() + " no existe"));

        if (!lector.isActivo()) {
            throw new CustomException("El lector se encuentra inactivo/bloqueado");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new CustomException("El libro con ID " + dto.getLibroId() + " no existe"));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new CustomException("El libro no cuenta con ejemplares disponibles");
        }

        long prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(
                lector.getId(),
                Arrays.asList(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO)
        );

        int limitePrestamos = lector.isMembresiaPremium() ? 5 : 3;

        if (prestamosActivos >= limitePrestamos) {
            throw new CustomException("El lector ha alcanzado su límite de préstamos activos (" + limitePrestamos + ")");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(RequestDevolverLibroDTO dto) {
        Prestamo prestamo = prestamoRepository.findById(dto.getPrestamoId())
                .orElseThrow(() -> new CustomException("El préstamo con ID " + dto.getPrestamoId() + " no existe"));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomException("El préstamo ya fue devuelto anteriormente");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(RequestRenovarPrestamoDTO dto) {
        Prestamo prestamo = prestamoRepository.findById(dto.getPrestamoId())
                .orElseThrow(() -> new CustomException("El préstamo con ID " + dto.getPrestamoId() + " no existe"));

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomException("Un préstamo en estado " + prestamo.getEstado() + " no se puede renovar");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamosActivosLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new CustomException("El lector con ID " + lectorId + " no existe"));

        return prestamoRepository.findByLectorIdAndEstadoIn(
                lector.getId(),
                Arrays.asList(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO)
        );
    }
}
package mx.edu.utez.prestamoslibros.repository.prestamo;

import mx.edu.utez.prestamoslibros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.prestamoslibros.model.prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByLectorIdAndEstadoIn(Long lectorId, List<EstadoPrestamo> estados);

    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, List<EstadoPrestamo> estados);
}
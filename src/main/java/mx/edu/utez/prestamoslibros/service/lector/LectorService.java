package mx.edu.utez.prestamoslibros.service.lector;

import jakarta.transaction.Transactional;
import mx.edu.utez.prestamoslibros.controller.lector.dto.RequestLectorDTO;
import mx.edu.utez.prestamoslibros.exception.CustomException;
import mx.edu.utez.prestamoslibros.model.lector.Lector;
import mx.edu.utez.prestamoslibros.repository.lector.LectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    public List<Lector> findAll() {
        return lectorRepository.findAll();
    }

    public Lector findById(Long id) {
        return lectorRepository.findById(id)
                .orElseThrow(() -> new CustomException("El lector con ID " + id + " no existe"));
    }

    @Transactional
    public Lector save(RequestLectorDTO dto) {
        Lector lector = new Lector();
        lector.setNombre(dto.getNombre());
        lector.setMembresiaPremium(dto.isMembresiaPremium());
        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    @Transactional
    public Lector rehabilitarLector(Long id) {
        Lector lector = findById(id);
        lector.setActivo(true);
        return lectorRepository.save(lector);
    }
}
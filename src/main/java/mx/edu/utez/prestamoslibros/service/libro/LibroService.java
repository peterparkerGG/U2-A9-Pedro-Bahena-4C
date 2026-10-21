package mx.edu.utez.prestamoslibros.service.libro;

import jakarta.transaction.Transactional;
import mx.edu.utez.prestamoslibros.controller.libro.dto.RequestLibroDTO;
import mx.edu.utez.prestamoslibros.exception.CustomException;
import mx.edu.utez.prestamoslibros.model.libro.Libro;
import mx.edu.utez.prestamoslibros.repository.libro.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Libro findById(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new CustomException("El libro con ID " + id + " no existe"));
    }

    @Transactional
    public Libro save(RequestLibroDTO dto) {
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());
        return libroRepository.save(libro);
    }
}
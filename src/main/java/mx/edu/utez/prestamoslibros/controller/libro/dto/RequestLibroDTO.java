package mx.edu.utez.prestamoslibros.controller.libro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLibroDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Min(value = 0, message = "Los ejemplares disponibles no pueden ser negativos")
    private int ejemplaresDisponibles;
}
package mx.edu.utez.prestamoslibros.controller.prestamo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRenovarPrestamoDTO {

    @NotNull(message = "El ID del préstamo es obligatorio")
    private Long prestamoId;
}
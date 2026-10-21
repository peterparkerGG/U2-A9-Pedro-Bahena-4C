package mx.edu.utez.prestamoslibros.controller.lector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLectorDTO {

    @NotBlank(message = "El nombre del lector es obligatorio")
    private String nombre;

    private boolean membresiaPremium;
}
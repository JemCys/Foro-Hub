package com.rebollarclary.forohub.domain.topico;

import com.rebollarclary.forohub.domain.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(

        @NotBlank(message = "No puede estar vacío")
        String titulo,

        @NotBlank(message = "No puede estar vacio")
        String mensaje,

        @NotBlank(message = "No puede estar vacio")
        String autor,

        @NotNull(message = "No puede estar vacio")
        Curso curso

) {
}

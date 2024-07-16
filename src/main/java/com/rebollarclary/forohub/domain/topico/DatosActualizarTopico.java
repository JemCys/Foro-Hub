package com.rebollarclary.forohub.domain.topico;

import com.rebollarclary.forohub.domain.Curso;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}

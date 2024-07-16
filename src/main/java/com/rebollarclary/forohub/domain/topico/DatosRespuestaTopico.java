package com.rebollarclary.forohub.domain.topico;

import com.rebollarclary.forohub.domain.Curso;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}

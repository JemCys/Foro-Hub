package com.rebollarclary.forohub.domain.topico;

import com.rebollarclary.forohub.domain.Curso;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso,
        String status,
        LocalDateTime fechaCreacion

) {
    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso(), topico.getSinRespuesta() ? "sin respuesta" : "con respuesta", topico.getFechaCreacion());
    }
}

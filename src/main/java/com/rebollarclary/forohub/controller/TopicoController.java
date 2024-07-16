package com.rebollarclary.forohub.controller;

import com.rebollarclary.forohub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "Gestión de tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    /**
     * Crear un nuevo tópico en el foro.
     *
     * @param datosRegistroTopico los datos del nuevo tópico
     * @param uriComponentsBuilder para construir la URI del nuevo recurso
     * @return una respuesta con el nuevo tópico creado
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo tópico", description = "Crear un nuevo tópico en el foro.")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso());

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    /**
     * Obtener una lista de todos los tópicos del foro.
     *
     * @param paginacion configuración de paginación
     * @return una lista paginada de tópicos
     */
    @GetMapping
    @Operation(summary = "Listar todos los tópicos", description = "Obtener una lista de todos los tópicos del foro.")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopico(@PageableDefault(size = 10) Pageable paginacion) {
        Pageable sortedByFechaCreacionAsc = PageRequest.of(paginacion.getPageNumber(), paginacion.getPageSize(), Sort.by("fechaCreacion").ascending());
        return ResponseEntity.ok(topicoRepository.findBySinRespuestaTrue(sortedByFechaCreacionAsc)
                .map(DatosListadoTopico::new));
    }

    /**
     * Actualizar los detalles de un tópico existente mediante su ID.
     *
     * @param id el ID del tópico a actualizar
     * @param datosActualizarTopico los nuevos datos del tópico
     * @return una respuesta con el tópico actualizado
     */
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar un tópico", description = "Actualizar los detalles de un tópico existente mediante su ID.")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        if (!id.equals(datosActualizarTopico.id())) {
            return ResponseEntity.badRequest().build();
        }
        Topico topico = verificarExistenciaTopico(id);
        topico.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getAutor(), topico.getCurso()));
    }

    /**
     * Eliminar un tópico existente mediante su ID.
     *
     * @param id el ID del tópico a eliminar
     * @return una respuesta indicando el resultado de la operación
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un tópico", description = "Eliminar un tópico existente mediante su ID.")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        Topico topico = verificarExistenciaTopico(id);
        topicoRepository.delete(topico);
        return ResponseEntity.ok("Su tópico fue eliminado");
    }

    /**
     * Obtener los detalles de un tópico específico mediante su ID.
     *
     * @param id el ID del tópico a obtener
     * @return una respuesta con los detalles del tópico
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tópico por ID", description = "Obtener los detalles de un tópico específico mediante su ID.")
    public ResponseEntity<DatosListadoTopico> obtenerTopicoPorId(@PathVariable Long id) {
        Topico topico = verificarExistenciaTopico(id);
        DatosListadoTopico datosListadoTopico = new DatosListadoTopico(topico);
        return ResponseEntity.ok(datosListadoTopico);
    }

    /**
     * Verificar la existencia de un tópico mediante su ID.
     *
     * @param id el ID del tópico
     * @return el tópico si existe
     * @throws EntityNotFoundException si el tópico no existe
     */
    private Topico verificarExistenciaTopico(Long id) {
        return topicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El ID " + id + " no existe."));
    }
}

package com.rebollarclary.forohub.controller;


import com.rebollarclary.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.rebollarclary.forohub.domain.usuario.Usuario;
import com.rebollarclary.forohub.infra.security.DatosJWTtoken;
import com.rebollarclary.forohub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Acceso", description = "Permite a los usuarios iniciar sesión en la plataforma.")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Autentica a un usuario y genera un token JWT.
     *
     * @param datosAutenticacionUsuario los datos de autenticación del usuario
     * @return ResponseEntity con el token JWT
     */
    @PostMapping
    @Operation(summary = "Iniciar sesión", description = "Acceso.")
    public ResponseEntity<DatosJWTtoken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
    }
}

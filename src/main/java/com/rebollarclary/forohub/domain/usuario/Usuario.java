package com.rebollarclary.forohub.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String clave;

    /**
     * Devuelve una colección de autoridades concedidas al usuario.
     *
     * @return colección de GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return la contraseña del usuario
     */
    @Override
    public String getPassword() {
        return clave;
    }

    /**
     * Devuelve el nombre de usuario.
     *
     * @return el nombre de usuario
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Indica si la cuenta del usuario no ha expirado.
     *
     * @return true si la cuenta no ha expirado, false en caso contrario
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario no está bloqueada.
     *
     * @return true si la cuenta no está bloqueada, false en caso contrario
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario no han expirado.
     *
     * @return true si las credenciales no han expirado, false en caso contrario
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     *
     * @return true si el usuario está habilitado, false en caso contrario
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
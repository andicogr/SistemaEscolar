package com.agonzales.SistemaEscolar.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agonzales.SistemaEscolar.domain.Privilegio;
import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.Usuario;
import com.agonzales.SistemaEscolar.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByUsername(username);

		if(usuario == null){
			throw new UsernameNotFoundException("No se encontró el usuario " + username);
		}
		
		System.out.println("Username: " + usuario.getUsername());
		System.out.println("Usuario Activo: " + usuario.isActivo());
		System.out.println("Usuario No Expirado: " + !usuario.isUsuarioExpirado());
		System.out.println("Usuario No Bloqueado: " + !usuario.isBloqueado());

		return new User(
				username, usuario.getPassword(), usuario.isActivo(),
				!usuario.isUsuarioExpirado(), true, !usuario.isBloqueado(),
				getAuthorities(usuario.getRolPorDefecto()));

	}

	public Collection<? extends GrantedAuthority> getAuthorities(List<Rol> roles) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		for(Rol rol: roles){
			authList.addAll(getGrantedAuthorities(getPrivilegios(rol)));
		}

		return authList;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Rol rol) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getPrivilegios(rol));

		System.out.println("Rol: " + rol.getNombre());
		for(GrantedAuthority a: authList) {
			System.out.println("	" + a.getAuthority());
		}
		return authList;
	}

    private List<String> getPrivilegios(Rol rol) {
        List<String> privilegios = new ArrayList<String>();
        List<Privilegio> collection = new ArrayList<Privilegio>();
        if(rol != null){
            collection.addAll(rol.getPrivilegios());  
            
            for (Privilegio item : collection) {
            	privilegios.add(item.getNombrePrivilegio());
            }
        }

        return privilegios;
    }

	public static List<GrantedAuthority> getGrantedAuthorities(List<String> privilegios) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String privilegio : privilegios) {
			authorities.add(new SimpleGrantedAuthority(privilegio));
		}
		return authorities;
	}
	
	public void aupdateAuthorities(Rol rol) {
		Collection<? extends GrantedAuthority> authenticationList = this.getAuthorities(rol);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
				authentication.getPrincipal(), authentication.getCredentials(), authenticationList);

		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	}

}

package com.agonzales.SistemaEscolar.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByUsername(username);

		if(usuario == null){
			throw new UsernameNotFoundException("No se encontró el usuario " + username);
		}

		LOG.info("Username: " + usuario.getUsername());
		LOG.info("Usuario Activo: " + usuario.isActivo());
		LOG.info("Usuario No Expirado: " + !usuario.isUsuarioExpirado());
		LOG.info("Usuario No Bloqueado: " + !usuario.isBloqueado());
		LOG.info("Usuario Roles Asignados: " + usuario.haveActiveRoles());

		User user = new User(
				username, usuario.getPassword(), usuario.isActivo(),
				!usuario.isUsuarioExpirado(), usuario.haveActiveRoles(), !usuario.isBloqueado(),
				getAuthorities(usuario.getRolPorDefecto()));
		
		
		return user; 

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

		for(GrantedAuthority a: authList) {
			LOG.info("	" + a.getAuthority());
		}
		return authList;
	}

    private List<String> getPrivilegios(Rol rol) {
        List<String> privilegios = new ArrayList<String>();
        List<Privilegio> collection = new ArrayList<Privilegio>();
        if(rol != null){
        	LOG.info("Rol: " + rol.getNombre());
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

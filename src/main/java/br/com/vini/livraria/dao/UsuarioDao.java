package br.com.vini.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.vini.livraria.entity.Usuario;

public class UsuarioDao implements Serializable{
	private static final long serialVersionUID = 6948012826236738155L;
	
	@Inject
	private EntityManager em;
	
	public boolean existe(Usuario usuario) {
		try {
		Usuario resultado = em.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class)
				.setParameter("pEmail", usuario.getEmail())
				.setParameter("pSenha", usuario.getSenha())
				.getSingleResult();
		
		em.close();

		return resultado != null;
		
		}catch(NoResultException e) {
			return false;
		}
	}

}

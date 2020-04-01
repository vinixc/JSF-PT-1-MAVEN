package br.com.vini.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vini.livraria.entity.Autor;

public class AutorDao implements Serializable{

	private static final long serialVersionUID = 291276214021240458L;

	@Inject
	EntityManager em;
	
	private DAO<Autor> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(this.em, Autor.class);
	}

	public void adiciona(Autor t) {
		dao.adiciona(t);
	}

	public void remove(Autor t) {
		dao.remove(t);
	}

	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
}

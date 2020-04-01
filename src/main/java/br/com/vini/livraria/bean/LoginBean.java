package br.com.vini.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vini.livraria.dao.UsuarioDao;
import br.com.vini.livraria.entity.Usuario;
import br.com.vini.livraria.util.RedirectView;

@Named
@ViewScoped
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1981047718464300886L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao dao;
	
	@Inject
	private FacesContext context;
	
	public String efetuaLogin() {
		
		System.out.println("Fazendo Login do usuario: " +  usuario.getEmail());
		
		boolean existe = dao.existe(this.usuario);
		
		if(existe) {
			this.context.getExternalContext().getSessionMap().put("usuario", this.usuario);
			return new RedirectView("livro").toString();
		}else {
			this.context.getExternalContext().getFlash().setKeepMessages(true);
			this.context.addMessage(null, new FacesMessage("Usuario ou senha incorretos!"));
			return "login?faces-redirect=true";
		}
	}
	
	public RedirectView deslogar() {
		this.context.getExternalContext().getSessionMap().remove("usuario");
		return new RedirectView("login");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}

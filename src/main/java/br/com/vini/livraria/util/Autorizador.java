package br.com.vini.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.vini.livraria.entity.Usuario;

public class Autorizador implements PhaseListener{
	private static final long serialVersionUID = 6432675672647723302L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		
		String nomePagina = context.getViewRoot().getViewId();
		System.out.println(nomePagina);
		
		if("/login.xhtml".equals(nomePagina)) {
			return;
		}
		
		Usuario usuario =(Usuario) context.getExternalContext().getSessionMap().get("usuario");
		if(usuario != null) {
			return;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, new RedirectView("login").toString());
		context.getRenderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}

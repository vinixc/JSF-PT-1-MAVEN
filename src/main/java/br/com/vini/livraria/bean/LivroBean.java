package br.com.vini.livraria.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vini.livraria.dao.AutorDao;
import br.com.vini.livraria.dao.LivroDao;
import br.com.vini.livraria.entity.Autor;
import br.com.vini.livraria.entity.Livro;
import br.com.vini.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable{
	private static final long serialVersionUID = 3768368818679263137L;
	
	private Livro livro = new Livro();
	private Integer autorId;
	
	private List<Livro> livros;
	
	@Inject
	private LivroDao dao;
	
	@Inject
	private AutorDao autorDao;
	
	@Inject
	private FacesContext context;
	
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			context.addMessage("autor", new FacesMessage("Livro deve ter um autor"));
			return;
		}
		
		if(this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		}else {
			dao.atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	
	public void removerAutor(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	@Transacional
	public void remover(Livro livro) {
		dao.remove(livro);
	}
	
	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public void comecaoComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException{
		String valor = value.toString();
		if(!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deve come�ar com 1"));
		}
	}
	
	public String formAutor() {
		System.out.println("Chamando o Formulario Autor");
		return "autor?faces-redirect=true";
	}
	
	public List<Livro> getLivros() {
		  if(this.livros == null) {
			  this.livros = dao.listaTodos();
		  }
		return livros;
	}
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { //java.util.Locale
		 //tirando espa�os do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        // o filtro � nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela � nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value � menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
	}
	
	public void carregarLivro(Livro livro) {
		this.livro = this.dao.buscaPorId(livro.getId());
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public List<Autor> getAutores(){
		return autorDao.listaTodos();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
}

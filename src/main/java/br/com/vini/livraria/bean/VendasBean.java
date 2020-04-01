package br.com.vini.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.vini.livraria.dao.VendaDao;
import br.com.vini.livraria.entity.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable{
	private static final long serialVersionUID = 2358293964529134200L;
	
	@Inject
	private VendaDao vendaDao;

	public List<Venda> getVendas(){
		return vendaDao.listaTodos();
	}
	
	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		model.setTitle("Vendas");
		model.setLegendPosition("ne");
		
		Axis xAxis = model.getAxis(AxisType.X);
	    xAxis.setLabel("Titulo");
	    
	    Axis yAxis = model.getAxis(AxisType.Y);
	    yAxis.setLabel("Quantidade");
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2020");
		
		List<Venda> vendas = getVendas();
		for(Venda venda : vendas ) {
			if(vendaSerie.getData().containsKey(venda.getLivro().getTitulo())){
				vendaSerie.getData().put(venda.getLivro().getTitulo(),
						vendaSerie.getData().get(venda.getLivro().getTitulo()).intValue() + venda.getQuantidade());
			}else {
				vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
			}
		}
		
		model.addSeries(vendaSerie);
		return model;
	}

}

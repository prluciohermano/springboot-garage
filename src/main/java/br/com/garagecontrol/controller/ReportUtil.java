package br.com.garagecontrol.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.activation.DataSource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable {
	
	public byte[] gerarRelatorio (List listDados, 
				String relatorio, ServletContext servletContext) 
				throws Exception {
		
		/* Cria a lista de dados para o relatório com a nossa lsita de objetos para imprimir */
		JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listDados);
		
		/* Carrega o caminho do arquivo jastper compilado */
		String caminhoJasper = servletContext.getRealPath("relatorios")
				+ File.separator + relatorio + ".jasper";
		
		/* Carrega o arquivo jasper passando os dados */		
		JasperPrint impressoraJasperPrint = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbc);
		
		/* Exporta para byte[] para fazer o download do PDF */
		return JasperExportManager.exportReportToPdf(impressoraJasperPrint);
		
	}
}

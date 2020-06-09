package controllers.classe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Domaine.evaluation.FormeEvaluation;
import Domaine.matiere.ConfigurationMatiere;
import Domaine.matiere.ConfigurationModule;
import Domaine.matiere.Module;
import Domaine.matiere.Periode;
import dao.matiere.ConfigurationModuleDAO;

/**
 * Servlet implementation class ClasseGeneriqueController
 */
@WebServlet("/ClasseGenerique/Unite/Matiere/Module/Add")
public class ClasseGeneriqueUniteMatiereModuleAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConfigurationModuleDAO configurationModuleDAO;
       
    public ClasseGeneriqueUniteMatiereModuleAdd() {
        super();
        configurationModuleDAO = new ConfigurationModuleDAO();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int configurationMatiereId = Integer.parseInt(request.getParameter("configurationMatiereId"));
		int moduleId = Integer.parseInt(request.getParameter("module"));
		int periodeId = Integer.parseInt(request.getParameter("periode"));
		Short volumeHoraire= Short.parseShort(request.getParameter("volumeHoraire"));
		Short horaireS1= Short.parseShort(request.getParameter("horaireS1"));
		Short horaireS2= Short.parseShort(request.getParameter("horaireS2"));
		Short nbreControles= Short.parseShort(request.getParameter("nbreControles"));
		int formeControleId = Integer.parseInt(request.getParameter("formeControle"));
		float noteEliminatoire = Float.parseFloat(request.getParameter("noteEliminatoire"));
		
		
		ConfigurationMatiere configurationMatiere = new ConfigurationMatiere();
		configurationMatiere.setId(configurationMatiereId);
		
		Module module = new Module();
		module.setId(moduleId);
		
		Periode periode = new Periode();
		periode.setId(periodeId);
		
		FormeEvaluation formeControle = new FormeEvaluation();
		formeControle.setId(formeControleId);
		
		ConfigurationModule configurationModule= new ConfigurationModule();
		configurationModule.setConfigurationMatiere(configurationMatiere);
		configurationModule.setModule(module);
		configurationModule.setPeriode(periode);
		configurationModule.setFormeControle(formeControle);
		configurationModule.setVolumeHoraire(volumeHoraire);
		configurationModule.setHoraireS1(horaireS1);
		configurationModule.setHoraireS2(horaireS2);
		configurationModule.setNbreControles(nbreControles);
		configurationModule.setNoteEliminatoire(noteEliminatoire);
		
		
		String message=null;
		boolean success =false;
		boolean warning = false;
		
		try {
			//TODO : ce test est incomplet : � corriger pour 
			// �viter la duplication d'une module dans d'autre unit�s ou d'autres mati�res
//			if(configurationModuleDAO.exists(configurationModule)== null){
				configurationModuleDAO.insert(configurationModule);
				success=true;
				warning = false;
				message="Le module a �l� ajout� avec succ�s � la mati�re";
				
//			}else{
//				success=true;
//				warning = true;
//				message="Ce  module existe d�ja dans l'une des mati�res";
//			}
		} catch (SQLException e1) {
			success=false;
			warning = false;
			message="Impossible d'ajouter le module � la mati�re ";
			e1.printStackTrace();
		}
		
		String json = new Gson().toJson(message);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

}
package objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Commune {
	
	private ArrayList<Historique> historiques = new ArrayList<Historique>();

	private String nomRegion;

	private String nomDpt;

	private int codePostal;
	
	private int nbNouveauContamine24h;
	
	private int nbDeces24h;
	
	private int nbGuerison24h;
	
	private String nomCommune;
	
	private Departement departement;

	private Historique h;

	private String isConfine;

	private ArrayList<Historique> listeHisto = new ArrayList<Historique>();

	private ArrayList<LocalDate> listeDateHisto = new ArrayList<LocalDate>();

	Vertex vertex ;

	public Commune(String nomRegion, String nomDpt, String nomCommune, int codePostal) {
		this.nomRegion = nomRegion;
		this.nomDpt = nomDpt;
		this.nomCommune = nomCommune;
		this.codePostal = codePostal;
		this.vertex = new Vertex(this.nomCommune);
	}
	
	public void ajouterHistorique(Historique h) {
		historiques.add(h);
	}
	
	public void supprimerHistorique(Historique h) {
		historiques.remove(h);
	}
	
	public ArrayList<Historique> getHistoriques() {
		return historiques;
	}

	public void setHistoriques(ArrayList<Historique> historiques) {
		this.historiques = historiques;
	}
	
	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public int getNbNouveauContamine24h() {
		return nbNouveauContamine24h;
	}

	public void setNbNouveauContamine24h(int nbNouveauContamine24h) {
		this.nbNouveauContamine24h = nbNouveauContamine24h;
	}

	public int getNbDeces24h() {
		return nbDeces24h;
	}

	public void setNbDeces24h(int nbDeces24h) {
		this.nbDeces24h = nbDeces24h;
	}

	public int getNbGuerison24h() {
		return nbGuerison24h;
	}

	public void setNbGuerison24h(int nbGuerison24h) {
		this.nbGuerison24h = nbGuerison24h;
	}
	
	public String getNomCommune() {
		return nomCommune;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public String getIsConfine(){ return  this.isConfine;}

	public void setIsConfine(String confine) {
		isConfine = confine;
	}

	public Historique getH() {
		return h;
	}

	public void setH(Historique h) {
		this.h = h;
	}

	public ArrayList<Historique> getListeHisto() {
		return listeHisto;
	}


	public String getNomRegion() {
		return nomRegion;
	}

	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	public String getNomDpt() {
		return nomDpt;
	}

	public void setNomDpt(String nomDpt) {
		this.nomDpt = nomDpt;
	}

	public ArrayList<LocalDate> getListeDateHisto() {
		return listeDateHisto;
	}

	public void setListeDateHisto(ArrayList<LocalDate> listeDateHisto) {
		this.listeDateHisto = listeDateHisto;
	}

	public void setListeHisto(ArrayList<Historique> listeHisto) {
		this.listeHisto = listeHisto;
	}

	public String toStringHisto(){
		return "Nombre de contaminés : "+ getNbNouveauContamine24h()+ " Nombre de décès : "+ getNbDeces24h() + " Nombre de guéris : "+ getNbGuerison24h() + " ("+getNomCommune()+ " " + getCodePostal()+")";
	}

	public Edge lierCommune(Commune c, int distance) {
		Edge edge = vertex.addNeighbour(new Edge(distance,vertex,c.getVertex()));
		return edge;
	}

	public List<Vertex> shortestPath(Commune c) {
		Dijkstra d = new Dijkstra();
		List<Vertex> res = null;
		if(this instanceof  ComNonConf && c instanceof ComNonConf) {
			d.computePath(vertex);
			res = d.getShortestPathTo(c.getVertex());
		}
		return res;
	}

	public Vertex getVertex() {
		return vertex;
	}
}

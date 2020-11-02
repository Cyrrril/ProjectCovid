package objects;

import java.util.ArrayList;

public class Region {
	
	private String nomRegion;
	
	private ArrayList<Departement> dpts = new ArrayList<Departement>();
	
	public Region(String nomRegion) {
		this.nomRegion = nomRegion;
	}


	public void ajouterDpt(Departement dpt) {
		dpts.add(dpt);
	}
	
	public void supprimerDpt(Departement dpt) {
		dpts.remove(dpt);
	}
	
	public ArrayList<Departement> getDpts() {
		return dpts;
	}

	public void setDpts(ArrayList<Departement> dpts) {
		this.dpts = dpts;
	}

	public String getNomRegion() {
		return nomRegion;
	}

	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	@Override
	public String toString() {
		return nomRegion + " ";
	}
}

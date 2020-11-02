package objects;

import java.util.ArrayList;

public class Departement {
	
	private String nomDpt;
	
	private Region region;
	
	private ArrayList<Commune> communes = new ArrayList<Commune>();

	private ArrayList<ComConf> comConfs = new ArrayList<ComConf>();

	private ArrayList<ComNonConf> comNonConfs = new ArrayList<ComNonConf>();
	
	public Departement(String nomDpt, Region region) {
		this.nomDpt = nomDpt;
		this.region = region;
	}

	public void ajouterCommune(Commune c) {
		communes.add(c);
	}

	public void ajouterComConf(ComConf c) {
		comConfs.add(c);
	}

	public void ajouterComNonConf(ComNonConf c) {
		comNonConfs.add(c);
	}
	
	public void supprimerCommune(Commune c) {
		communes.remove(c);
	}

	public String getNomDpt() {
		return nomDpt;
	}

	public void setNomDpt(String nomDpt) {
		this.nomDpt = nomDpt;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public ArrayList<Commune> getCommunes() {
		return communes;
	}

	public void setCommunes(ArrayList<Commune> communes) {
		this.communes = communes;
	}

	public ArrayList<ComConf> getcomConfs(){
		return comConfs;
	}

	public ArrayList<ComNonConf> getcomNonConfs(){
		return comNonConfs;
	}

	@Override
	public String toString() {
		return this.nomDpt;
	}
}

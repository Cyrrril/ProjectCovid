package objects;

import java.time.LocalDate;

public class ComNonConf extends Commune{

	private LocalDate dateDeconfinement;
	
	public ComNonConf(String region, String departement, String nomCommune, int codePostal, LocalDate dateDeconfinement) {
		super(region,departement,nomCommune,codePostal);
		setIsConfine("Non");
		this.dateDeconfinement = dateDeconfinement;
	}

	public LocalDate getDateDeconfinement() {
		return dateDeconfinement;
	}

	public void setDateDeconfinement(LocalDate dateDeconfinement) {
		this.dateDeconfinement = dateDeconfinement;
	}


	@Override
	public String toString() {
		return super.getNomCommune() + " " + super.getCodePostal();
	}
}

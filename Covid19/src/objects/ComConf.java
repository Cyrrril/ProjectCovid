package objects;

import java.time.LocalDate;
import java.time.Period;

public class ComConf extends Commune{

	private LocalDate dateConfinement;
	
	private Period dureePrevisionelle;
	
	public ComConf(String region,String departement,String nomCommune, int codePostal, LocalDate dateConfinement) {
		super(region, departement,nomCommune,codePostal);
		setIsConfine("Oui");
		this.dateConfinement = dateConfinement;
		this.dureePrevisionelle = Period.between(LocalDate.now(), dateConfinement);
	}

	public LocalDate getDateConfinement() {
		return dateConfinement;
	}

	public void setDateConfinement(LocalDate dateConfinement) {
		this.dateConfinement = dateConfinement;
	}

	public Period getDureePrevisionelle() {
		return dureePrevisionelle;
	}

	public void setDureePrevisionelle(Period dureePrevisionelle) {
		this.dureePrevisionelle = dureePrevisionelle;
	}

	@Override
	public String toString() {
		return super.getNomCommune() + " " + super.getCodePostal();
	}
}

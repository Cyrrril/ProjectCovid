package objects;

import java.time.LocalDate;

public class Historique {

	private int nbContaminations;
	
	private int nbDeces;
	
	private int nbGuerison;
	
	private boolean estConfine;
	
	private LocalDate date;

	public Historique(int nbContaminations, int nbDeces, int nbGuerison) {
		this.nbContaminations = nbContaminations;
		this.nbDeces = nbDeces;
		this.nbGuerison = nbGuerison;
	}

	public int getNbContaminations() {
		return nbContaminations;
	}

	public void setNbContaminations(int nbContaminations) {
		this.nbContaminations = nbContaminations;
	}

	public int getNbDeces() {
		return nbDeces;
	}

	public void setNbDeces(int nbDeces) {
		this.nbDeces = nbDeces;
	}

	public int getNbGuerison() {
		return nbGuerison;
	}

	public void setNbGuerison(int nbGuerison) {
		this.nbGuerison = nbGuerison;
	}

	public boolean isEstConfine() {
		return estConfine;
	}

	public void setEstConfine(boolean estConfine) {
		this.estConfine = estConfine;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Historique(" +
				"nbContaminations=" + nbContaminations +
				", nbDeces=" + nbDeces +
				", nbGuerison=" + nbGuerison +
				", date=" + date +
				')';
	}
}

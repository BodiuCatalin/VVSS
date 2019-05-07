package note.repository;

import note.model.Nota;
import note.utils.NotaException;

import java.util.List;

public interface NoteRepository {
	
	public void addNota(int nrmatricol, String materie, double nota) throws NotaException;
	public List<Nota> getNote(); 
	public void readNote(String fisier);
	void writeNote(String fisier);
}

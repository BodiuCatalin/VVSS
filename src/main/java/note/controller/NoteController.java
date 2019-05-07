package note.controller;

import note.model.Corigent;
import note.model.Elev;
import note.model.Medie;
import note.model.Nota;
import note.repository.*;
import note.utils.ClasaException;
import note.utils.NotaException;

import java.util.HashMap;
import java.util.List;

public class NoteController {
  private NoteRepository note;
  private ClasaRepository clasa;
  private EleviRepository elevi;

  public NoteController() {
    note = new NoteRepositoryImpl();
    clasa = new ClasaRepositoryImpl();
    elevi = new EleviRepositoryImpl();
  }

  public void addNota(Nota nota) throws NotaException {
    note.addNota(nota.getNrmatricol(), nota.getMaterie(), nota.getNota());
  }

  public void addElev(Elev elev) {
    elevi.addElev(elev);
  }

  public void creeazaClasa(List<Elev> elevi, List<Nota> note) {
    clasa.creazaClasa(elevi, note);
  }

  public List<Medie> calculeazaMedii() throws ClasaException {
    return clasa.calculeazaMedii();
  }

  public void saveSession() {
    note.writeNote("note.txt");
    elevi.writeElevi("elevi.txt");
  }

  public List<Nota> getNote() {
    return note.getNote();
  }

  public List<Elev> getElevi() {
    return elevi.getElevi();
  }

  public HashMap<Elev, HashMap<String, List<Double>>> getClasa() {
    return clasa.getClasa();
  }

  public void afiseazaClasa() {
    clasa.afiseazaClasa();
  }

  public void readElevi(String fisier) {
    elevi.readElevi(fisier);
  }

  public void readNote(String fisier) {
    note.readNote(fisier);
  }

  public List<Corigent> getCorigenti() throws ClasaException {
    return clasa.getCorigenti();
  }
}

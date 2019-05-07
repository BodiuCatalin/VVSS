package note.repository;

import note.model.Nota;
import note.utils.NotaException;
import note.validator.NoteValidator;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
  private List<Nota> note;
  private NoteValidator noteValidator;

  public NoteRepositoryImpl() {
    note = new LinkedList<Nota>();
    noteValidator = new NoteValidator();
  }

  @Override
  public void addNota(int nrmatricol, String materie, double notaV) throws NotaException {
    Nota nota = new Nota(nrmatricol, materie, notaV);
    // TODO Auto-generated method stub
    if (!noteValidator.validareNota(nota))
      return;
    note.add(nota);
  }

  @Override
  public List<Nota> getNote() {
    // TODO Auto-generated method stub
    return note;
  }

  public void readNote(String fisier) {
    try {
      FileInputStream fstream = new FileInputStream(fisier);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(";");
        Nota nota = new Nota(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]));
        note.add(nota);
      }
      br.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void writeNote(String fisier) {
    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(new File(fisier)));
      for (Nota nota : note) {
        writer.println(nota.getNrmatricol() + ";" + nota.getMaterie() + ";" + nota.getNota());
      }
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

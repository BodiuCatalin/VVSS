package note.repository;

import note.model.Elev;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class EleviRepositoryImpl implements EleviRepository {

  private List<Elev> elevi;

  public EleviRepositoryImpl() {
    elevi = new LinkedList<Elev>();
  }

  @Override
  public void addElev(Elev e) {
    // TODO Auto-generated method stub
    elevi.add(e);
  }

  @Override
  public List<Elev> getElevi() {
    // TODO Auto-generated method stub
    return elevi;
  }

  @Override
  public void readElevi(String fisier) {
    try {
      FileInputStream fstream = new FileInputStream(fisier);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(";");
        Elev elev = new Elev(Integer.parseInt(values[0]), values[1]);
        elevi.add(elev);
      }
      br.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void writeElevi(String fisier) {
    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(new File(fisier)));
      for (Elev elev : elevi) {
        writer.println(elev.getNrmatricol() + ";" + elev.getNume());
      }
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

package note.repository;

import note.model.Corigent;
import note.model.Elev;
import note.model.Medie;
import note.model.Nota;
import note.utils.ClasaException;
import note.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ClasaRepositoryImpl implements ClasaRepository {

  private HashMap<Elev, HashMap<String, List<Double>>> clasa;

  public ClasaRepositoryImpl() {
    clasa = new HashMap<Elev, HashMap<String, List<Double>>>();
  }

  @Override
  public void creazaClasa(List<Elev> elevi, List<Nota> note) {
    // TODO Auto-generated method stub
    List<String> materii = new LinkedList<String>();
    for (Nota nota : note) {
      if (!materii.contains(nota.getMaterie()))
        materii.add(nota.getMaterie());
    }
    for (Elev elev : elevi) {
      HashMap<String, List<Double>> situatie = new HashMap<String, List<Double>>();
      for (String materie : materii) {
        List<Double> noteMaterie = new LinkedList<Double>();
        for (Nota nota : note)
          if (nota.getMaterie().equals(materie) && nota.getNrmatricol() == elev.getNrmatricol())
            noteMaterie.add(nota.getNota());
        situatie.put(materie, noteMaterie);
      }
      clasa.put(elev, situatie);
    }
  }

  @Override
  public HashMap<Elev, HashMap<String, List<Double>>> getClasa() {
    // TODO Auto-generated method stub
    return clasa;
  }

  @Override
  public List<Medie> calculeazaMedii() throws ClasaException {
    List<Medie> medii = new LinkedList<Medie>();
    if (!clasa.isEmpty()) {
      for (Elev elev : clasa.keySet()) {
        Medie medie = new Medie();
        medie.setElev(elev);
        int nrMaterii = 0;
        double sumaMedii = 0;
        double medieElev = 0;
        for (String materie : clasa.get(elev).keySet()) {
          nrMaterii++;
          List<Double> noteElev = clasa.get(elev).get(materie);
          int nrNote = noteElev.size();
          int i = 0;
          double suma = 0;
          if (nrNote > 0) {
            while (i < nrNote) {
              double nota = noteElev.get(i);
              suma += nota;
              i++;
            }
            sumaMedii = sumaMedii + suma / i;
          }
        }
        medieElev = sumaMedii / nrMaterii;
        medie.setMedie(medieElev);
        medii.add(medie);
      }
    } else
      throw new ClasaException(Constants.emptyRepository);
    return medii;
  }

  public void afiseazaClasa() {
    for (Elev elev : clasa.keySet()) {
      System.out.println(elev);
      for (String materie : clasa.get(elev).keySet()) {
        System.out.println(materie);
        for (double nota : clasa.get(elev).get(materie))
          System.out.print(nota + " ");
      }
    }
  }

  @Override
  public List<Corigent> getCorigenti() throws ClasaException {
    List<Corigent> corigenti = new ArrayList<Corigent>();
    if (!clasa.isEmpty()) {
      for (Elev elev : clasa.keySet()) {
        Corigent corigent = new Corigent(elev.getNume(), 0);
        for (String materie : clasa.get(elev).keySet()) {
          List<Double> noteElev = clasa.get(elev).get(materie);
          int nrNote = noteElev.size();
          int i = 0;
          double suma = 0;
          if (nrNote > 0) {
            while (i < nrNote) {
              double nota = noteElev.get(i);
              suma += nota;
              i++;
            }
            double media = suma / i;
            if (media < 4.5)
              corigent.setNrMaterii(corigent.getNrMaterii() + 1);
          }
        }
        if (corigent.getNrMaterii() > 0) {
          int i = 0;
          while (i < corigenti.size() && corigenti.get(i).getNrMaterii() < corigent.getNrMaterii())
            i++;
          if (i != corigenti.size() && corigenti.get(i).getNrMaterii() == corigent.getNrMaterii()) {
            while (i < corigenti.size() && corigenti.get(i).getNrMaterii() == corigent.getNrMaterii() && corigenti.get(i).getNumeElev().compareTo(corigent.getNumeElev()) < 0)
              i++;
            corigenti.add(i, corigent);
          } else
            corigenti.add(i, corigent);
        }
      }
    } else {
      throw new ClasaException("Clasa este goala");
    }

    corigenti.sort((c1, c2) -> {
      if (c1.getNrMaterii() > c2.getNrMaterii()) return -1;
      else if (c1.getNrMaterii() == c2.getNrMaterii()) return 0;
      return 1;
    });
    return corigenti;
  }
}

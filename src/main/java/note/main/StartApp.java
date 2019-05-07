package note.main;

import note.controller.NoteController;
import note.model.Corigent;
import note.model.Medie;
import note.model.Nota;
import note.utils.ClasaException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//functionalitati
//F01.	 adaugarea unei note la o anumita materie (nr. matricol, materie, nota acordata);
//F02.	 calcularea mediilor semestriale pentru fiecare elev (nume, nr. matricol),
//F03.	 afisarea elevilor coringenti, ordonati descrescator dupa numarul de materii la care nu au promovat şi alfabetic dupa nume.

public class StartApp {

  /**
   * @param args
   * @throws ClasaException
   */
  public static void main(String[] args) throws ClasaException {
    // TODO Auto-generated method stub
    NoteController ctrl = new NoteController();
    List<Medie> medii = new LinkedList<Medie>();
    List<Corigent> corigenti = new ArrayList<Corigent>();
    if (args.length < 2) System.out.println("Fisierele cu date nu au fost specificate...");
    else {
      ctrl.readElevi(args[0]);
      ctrl.readNote(args[1]);
    }
    ctrl.creeazaClasa(ctrl.getElevi(), ctrl.getNote());
    boolean gasit = false;
    while (!gasit) {
      System.out.println("1. Adaugare Nota");
      System.out.println("2. Calculeaza medii");
      System.out.println("3. Elevi corigenti");
      System.out.println("4. Iesire");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      try {
        int option = Integer.parseInt(br.readLine());
        switch (option) {
          case 1:
            String input = br.readLine();
            String[] noteParts = input.split(";");
            ctrl.addNota(new Nota(Integer.parseInt(noteParts[0]), noteParts[1], Double.parseDouble(noteParts[2])));
            break;
          case 2:
            medii = ctrl.calculeazaMedii();
            for (Medie medie : medii)
              System.out.println(medie);
            break;
          case 3:
            corigenti = ctrl.getCorigenti();
            for (Corigent corigent : corigenti)
              System.out.println(corigent);
            break;
          case 4:
            gasit = true;
            ctrl.saveSession();
            break;
          default:
            System.out.println("Introduceti o optiune valida!");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

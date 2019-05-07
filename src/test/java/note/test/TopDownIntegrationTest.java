package note.test;

import note.model.Corigent;
import note.model.Elev;
import note.model.Medie;
import note.model.Nota;
import note.repository.*;
import note.utils.ClasaException;
import note.utils.NotaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TopDownIntegrationTest {

  private NoteRepository notaRepository;
  private ClasaRepository clasaRepository;
  private EleviRepository eleviRepository;

  @Before
  public void before() {
    notaRepository = new NoteRepositoryImpl();
    clasaRepository = new ClasaRepositoryImpl();
    eleviRepository = new EleviRepositoryImpl();
  }

  @Test
  public void testF02() throws ClasaException {
    Elev elev = new Elev(230, "Ana");
    HashMap<String, List<Double>> materii = new HashMap<>();
    List<Double> note = new ArrayList<>();
    note.add(7.0);
    materii.put("romana", note);
    clasaRepository.getClasa().put(elev, materii);
    List<Medie> result = clasaRepository.calculeazaMedii();
    Assert.assertTrue(result.size() == 1);
    Assert.assertTrue(result.get(0).getMedie() == 7.0);
  }

  @Test
  public void testF03() throws ClasaException {
    List<Elev> elevi = new ArrayList<>();
    elevi.add(new Elev(1, "Ana"));
    List<Nota> note = new ArrayList<>();
    note.add(new Nota(1, "romana", 4.0));
    clasaRepository.creazaClasa(elevi, note);
    List<Corigent> corigenti = clasaRepository.getCorigenti();
    Assert.assertTrue(corigenti.size() == 1);
    Assert.assertTrue(corigenti.get(0).getNumeElev().equals("Ana"));
    Assert.assertTrue(corigenti.get(0).getNrMaterii() == 1);
  }

  @Test
  public void testF01() throws NotaException {
    notaRepository.addNota(5, "abcdef", 1.0);
    Nota addedNota = notaRepository.getNote().stream().
        filter(nota -> nota.getNota() == 1.0 &&
            nota.getNrmatricol() == 5 &&
            nota.getMaterie().equals("abcdef")).findFirst().get();
    Assert.assertNotNull(addedNota);
  }

  @Test
  public void testIntegrateA() throws NotaException {
    eleviRepository.addElev(new Elev(1, "Ana"));
    eleviRepository.addElev(new Elev(2, "George"));
    eleviRepository.addElev(new Elev(3, "Andrei"));
    notaRepository.addNota(1, "romana", 4.0);
    notaRepository.addNota(2, "matematica", 4.0);
    notaRepository.addNota(2, "romana", 3.0);
    notaRepository.addNota(3, "romana", 6.0);
    notaRepository.addNota(3, "matematica", 8.0);
    clasaRepository.creazaClasa(eleviRepository.getElevi(), notaRepository.getNote());
    Assert.assertTrue(notaRepository.getNote().size() == 5);
    Assert.assertTrue(notaRepository.getNote().get(0).getNrmatricol() == 1);
    Assert.assertTrue(notaRepository.getNote().get(1).getNrmatricol() == 2);
    Assert.assertTrue(notaRepository.getNote().get(2).getNrmatricol() == 2);
    Assert.assertTrue(notaRepository.getNote().get(3).getNrmatricol() == 3);
    Assert.assertTrue(notaRepository.getNote().get(4).getNrmatricol() == 3);
  }

  @Test
  public void testIntegrateAB() throws NotaException, ClasaException {
    testIntegrateA();
    List<Medie> medii = clasaRepository.calculeazaMedii();
    List<Double> mediiDouble = medii.stream().map(Medie::getMedie).collect(Collectors.toList());
    Assert.assertTrue(mediiDouble.size() == 3);
    Assert.assertTrue(mediiDouble.contains(7.0) && mediiDouble.contains(2.0) && mediiDouble.contains(3.5));
  }

  @Test
  public void testIntegrateABC()throws NotaException,ClasaException{
    testIntegrateAB();
    List<Corigent> corigenti = clasaRepository.getCorigenti();
    Assert.assertTrue(corigenti.size() == 2);
    Assert.assertTrue(corigenti.get(0).getNumeElev().equals("George"));
    Assert.assertTrue(corigenti.get(0).getNrMaterii() == 2);
    Assert.assertTrue(corigenti.get(1).getNumeElev().equals("Ana"));
    Assert.assertTrue(corigenti.get(1).getNrMaterii() == 1);
  }

}

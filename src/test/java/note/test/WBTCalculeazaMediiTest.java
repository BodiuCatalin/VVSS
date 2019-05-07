package note.test;

import note.model.Elev;
import note.model.Medie;
import note.repository.ClasaRepository;
import note.repository.ClasaRepositoryImpl;
import note.utils.ClasaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WBTCalculeazaMediiTest {

  private ClasaRepository clasaRepository;

  @Before
  public void before() {
    clasaRepository = new ClasaRepositoryImpl();
  }

  @Test(expected = ClasaException.class)
  public void test01() throws ClasaException {
    clasaRepository.calculeazaMedii();
  }

  @Test
  public void test02() throws ClasaException {
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
  public void test03() throws ClasaException {
    Elev elev = new Elev(230, "Ana");
    HashMap<String, List<Double>> materii = new HashMap<>();
    List<Double> note = new ArrayList<>();
    materii.put("romana", note);
    clasaRepository.getClasa().put(elev, materii);
    List<Medie> result = clasaRepository.calculeazaMedii();
    Assert.assertTrue(result.size() == 1);
    Assert.assertTrue(result.get(0).getMedie() == 0.0);
  }

  @Test
  public void test04() throws ClasaException {
    Elev elev = new Elev(230, "Ana");
    HashMap<String, List<Double>> materii = new HashMap<>();
    List<Double> note = new ArrayList<>();
    note.add(7.0);
    materii.put("romana", note);
    clasaRepository.getClasa().put(elev, materii);

    elev = new Elev(135, "Andrei");
    materii = new HashMap<>();
    note = new ArrayList<>();
    materii.put("romana", new ArrayList<>());
    clasaRepository.getClasa().put(elev, materii);

    elev = new Elev(193, "Stefan");
    materii = new HashMap<>();
    note.add(8.0);
    note.add(8.0);
    note.add(8.0);
    materii.put("matematica", note);
    clasaRepository.getClasa().put(elev, materii);

    List<Medie> result = clasaRepository.calculeazaMedii();
    List<String> eleviList = result.stream().map(Medie::getElev).map(Elev::getNume).collect(Collectors.toList());
    List<Double> medii = result.stream().map(Medie::getMedie).collect(Collectors.toList());

    Assert.assertTrue(result.size() == 3);
    Assert.assertTrue(eleviList.contains("Andrei"));
    Assert.assertTrue(medii.contains(0.0));

    Assert.assertTrue(eleviList.contains("Ana"));
    Assert.assertTrue(medii.contains(7.0));

    Assert.assertTrue(eleviList.contains("Stefan"));
    Assert.assertTrue(medii.contains(8.0));
  }
}

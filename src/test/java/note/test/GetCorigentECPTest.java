package note.test;

import note.model.Corigent;
import note.model.Elev;
import note.model.Nota;
import note.repository.ClasaRepository;
import note.repository.ClasaRepositoryImpl;
import note.utils.ClasaException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetCorigentECPTest {

  private ClasaRepository clasaRepository = new ClasaRepositoryImpl();

  @Test
  public void testValid01() throws ClasaException {
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

  @Test(expected = ClasaException.class)
  public void testInvalid01() throws ClasaException {
    clasaRepository.creazaClasa(new ArrayList<>(),new ArrayList<>());
    clasaRepository.getCorigenti();
  }
}

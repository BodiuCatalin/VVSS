package note.test;

import note.model.Nota;
import note.repository.NoteRepository;
import note.repository.NoteRepositoryImpl;
import note.utils.NotaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BVATest {

  private NoteRepository notaRepository;

  @Before
  public void before() {
    notaRepository = new NoteRepositoryImpl();
  }

  @Test
  public void test01() throws NotaException {
    notaRepository.addNota(5, "abcdef", 1.0);
    Nota addedNota = notaRepository.getNote().stream().
        filter(nota -> nota.getNota() == 1.0 &&
            nota.getNrmatricol() == 5 &&
            nota.getMaterie().equals("abcdef")).findFirst().get();
    Assert.assertNotNull(addedNota);
  }

  @Test
  public void test02() throws NotaException {
//    notaRepository.addNota(7, "istorie", "a");
  }

  @Test
  public void test03() throws NotaException {
    notaRepository.addNota(7, "romana", 6.0);
    Nota addedNota = notaRepository.getNote().stream().
        filter(nota -> nota.getNota() == 6.0 &&
            nota.getNrmatricol() == 7 &&
            nota.getMaterie().equals("romana")).findFirst().get();
    Assert.assertNotNull(addedNota);
  }

  @Test(expected = NotaException.class)
  public void test04() throws NotaException {
    notaRepository.addNota(8, "matematica", 0.0);
  }
}

package note.validator;

import note.model.Nota;
import note.utils.Constants;
import note.utils.NotaException;

public class NoteValidator {

  public boolean validareNota(Nota nota) throws NotaException {
    // TODO Auto-generated method stub
    if (nota.getMaterie().length() < 5 || nota.getMaterie().length() > 20)
      throw new NotaException(Constants.invalidMateria);
    if (nota.getNrmatricol() < Constants.minNrmatricol || nota.getNrmatricol() > Constants.maxNrmatricol)
      throw new NotaException(Constants.invalidNrmatricol);
    if (nota.getNota() < Constants.minNota || nota.getNota() > Constants.maxNota || nota.getNota() != (int) nota.getNota())
      throw new NotaException(Constants.invalidNota);
    if (nota.getNrmatricol() != (int) nota.getNrmatricol())
      throw new NotaException(Constants.invalidNrmatricol);
    return true;
  }
}

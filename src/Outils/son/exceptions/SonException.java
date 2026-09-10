package Outils.son.exceptions;

import java.io.Serializable;

/**
 * Excpetion g�n�rale <br>
 */

public class SonException
    extends Exception implements Serializable
{
  /**
   * Construit l'exception
   * @param message Message de l'exception
   */
  public SonException(String message)
  {
    super(message);
  }
}

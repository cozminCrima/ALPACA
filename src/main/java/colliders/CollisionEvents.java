package colliders;

/**
 * Interfață care definește evenimentele de coliziune.
 */
public interface CollisionEvents {

  /**
   * Metodă apelată atunci când are loc o coliziune.
   * 
   * @param col collider-ul cu care s-a produs coliziunea
   */
  public void OnCollisionEnter(Collider col);

  /**
   * Metodă apelată în timpul unei coliziuni continue.
   * 
   * @param col collider-ul cu care se menține coliziunea
   */
  public default void OnCollisionStay(Collider col) {
    // Implementați această metodă în clasele care implementează această interfață,
    // dacă este necesar.
  }

  /**
   * Metodă apelată atunci când se termină o coliziune.
   * 
   * @param col collider-ul cu care s-a terminat coliziunea
   */
  public default void OnCollisionExit(Collider col) {
    // Implementați această metodă în clasele care implementează această interfață,
    // dacă este necesar.
  }
}

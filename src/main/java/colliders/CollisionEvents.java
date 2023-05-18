package colliders;

public interface CollisionEvents {
	public void OnCollisionEnter(Collider col);
	public default void OnCollisionStay(Collider col)
	{
		
	}
	public default void OnCollisionExit(Collider col)
	{
		
	}
}

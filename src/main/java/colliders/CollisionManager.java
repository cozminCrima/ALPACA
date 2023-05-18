package colliders;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
	private ArrayList<Collider> colliders;
	
	public CollisionManager() {
		colliders = new ArrayList<Collider>();
	}
	
	public void updateColliders()
	{
		int index = 0;
		for(int i=0;i<colliders.size();++i)
		{
			if(i!=index)
			{
				Collider c1 = colliders.get(index);
				Collider c2 = colliders.get(i);
				if(c1.checkForCollision(c2))
				{
					if(!c1.isColliding())
					{
						c1.setIsColliding(true);
						c1.OnCollisionEnter(c2);
					}
					else
					{
						c1.OnCollisionStay(c2);
					}
				}
				else
				{
					c1.setIsColliding(false);
				}
			}
		}
	}
	
	public void addCollider(Collider col)
	{
		colliders.add(col);
	}
}

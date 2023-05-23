package colliders;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
	private ArrayList<Collider> colliders;

	public CollisionManager() {
		colliders = new ArrayList<Collider>();
	}

	public void updateColliders() {
		for (int i = 0; i < colliders.size() - 1; ++i) {
			for (int j = 1; j < colliders.size(); ++j) {
				Collider c1 = colliders.get(i);
				Collider c2 = colliders.get(j);
				if (c1.checkForCollision(c2)) {
					if (!c1.isColliding()) {
						c1.setIsColliding(true);
						c2.setIsColliding(true);
						c1.OnCollisionEnter(c2);
						c2.OnCollisionEnter(c1);
					} else {
						c1.OnCollisionStay(c2);
						c2.OnCollisionStay(c1);
					}
				} else {
					c1.setIsColliding(false);
				}
			}
		}
	}

	public void addCollider(Collider col) {
		colliders.add(col);
	}
	
	public void removeCollider(Collider col)
	{
		colliders.remove(col);
	}
}

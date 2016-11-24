package pl.rembol.jme3.copernicus.objects;

import com.jme3.scene.plugins.blender.math.Vector3d;
import org.junit.Test;
import org.mockito.Mockito;

public class GravityAppStateTest {

    @Test
    public void objectsOnParallelCourseDoNotCollide() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(0, 10, 0), new Vector3d(10, 0, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyNoCollision(object1, object2);
    }

    @Test
    public void hitsNotMovingObject() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(10, 0, 0), new Vector3d(0, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyCollision(object1, object2);
    }

    @Test
    public void missesNotMovingObject() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(10, 10, 0), new Vector3d(0, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyNoCollision(object1, object2);
    }

    @Test
    public void passesBehindObject() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(10, 0, 0), new Vector3d(0, -10, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyNoCollision(object1, object2);
    }

    @Test
    public void passesInFrontOfObject() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(10, 20, 0), new Vector3d(0, -10, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyNoCollision(object1, object2);
    }

    @Test
    public void collideAtTheEndOfMovement() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(10, 10, 0), new Vector3d(0, -10, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyCollision(object1, object2);
    }

    @Test
    public void collideInTheMiddleOfMovement() throws Exception {
        // given
        GravityAppState gravityAppState = new GravityAppState(null);

        CollidableSpaceObject object1 = prepareObject(gravityAppState, new Vector3d(0, 0, 0), new Vector3d(10, 0, 0));
        CollidableSpaceObject object2 = prepareObject(gravityAppState, new Vector3d(5, 5, 0), new Vector3d(0, -10, 0));

        // when
        gravityAppState.checkCollisions(Vector3d.ZERO);

        // then
        verifyCollision(object1, object2);
    }

    private void verifyCollision(CollidableSpaceObject object1, CollidableSpaceObject object2) {
        Mockito.verify(object1).hit(Mockito.anyDouble(), Mockito.any(Vector3d.class));
        Mockito.verify(object2).hit(Mockito.anyDouble(), Mockito.any(Vector3d.class));
    }

    private void verifyNoCollision(CollidableSpaceObject object1, CollidableSpaceObject object2) {
        Mockito.verify(object1, Mockito.never()).hit(Mockito.anyDouble(), Mockito.any(Vector3d.class));
        Mockito.verify(object2, Mockito.never()).hit(Mockito.anyDouble(), Mockito.any(Vector3d.class));
    }

    private CollidableSpaceObject prepareObject(GravityAppState gravityAppState, Vector3d position, Vector3d velocity) {
        CollidableSpaceObject object = Mockito.mock(CollidableSpaceObject.class);
        Mockito.when(object.getRadius()).thenReturn(1d);
        Mockito.when(object.getVelocity()).thenReturn(velocity);
        Mockito.when(object.getPrecisePosition()).thenAnswer(invocationOnMock -> gravityAppState.getPosition((SpaceObject) invocationOnMock.getMock()));
        gravityAppState.setPosition(object, position);
        gravityAppState.move(object, velocity);
        return object;
    }

}
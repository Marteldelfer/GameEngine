package engine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Gravity {
    float acceleration() default 1;
    float direction() default Direction.DOWN;

    class Direction {
        public static final float RIGHT = 0;
        public static final float UP = (float) (Math.PI / 2);
        public static final float LEFT = (float) (Math.PI);
        public static final float DOWN = (float) (3 * Math.PI / 2);
    }
}
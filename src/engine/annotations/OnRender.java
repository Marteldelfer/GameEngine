package engine.annotations;

import engine.state.DefaultGameState;
import engine.state.GameState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnRender {
    Class<? extends GameState> gamestate() default DefaultGameState.class;
}

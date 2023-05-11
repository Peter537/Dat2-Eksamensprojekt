package dat.backend.model.annotation;

public @interface IgnoreCoverage {
    String reason() default "";
}

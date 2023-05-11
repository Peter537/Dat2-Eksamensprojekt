package dat.backend.annotation;

@IgnoreCoverage(reason = "This is a marker annotation to ignore coverage on a class or method")
public @interface IgnoreCoverage {
    String reason() default "";
}

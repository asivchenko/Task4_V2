

package org.example.LogTransformation;

        import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)   // для класса для метода METHOD
public @interface LogTransformation {
    String LogFile () default "default.log";
}

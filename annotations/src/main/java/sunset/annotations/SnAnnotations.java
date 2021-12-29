package sunset.annotations;

import java.lang.annotation.*;

public class SnAnnotations{
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BothExtends{
        String[] classes();

        String[] prefixes() default {};
        String packageName() default "\n\n";
    }
}

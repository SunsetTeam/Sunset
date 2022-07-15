package sunset.annotations;

import java.lang.annotation.*;

public class SnAnnotations{
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BothExtends{
        String[] classes();

        String[] classNames() default {};
        String packageName() default "\n\n";
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface TestAnnotation{
    }
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataKeyType{
        Class<?> value();
    }
}

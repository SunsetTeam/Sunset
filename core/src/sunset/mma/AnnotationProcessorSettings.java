package sunset.mma;

import mma.annotations.ModAnnotations.*;
import sunset.*;

@AnnotationSettings(
rootPackage = "sunset",
classPrefix = "Sn"
)
@ModAssetsAnnotation
@DependenciesAnnotation
@MainClass(Sunset.class)
class AnnotationProcessorSettings {
}

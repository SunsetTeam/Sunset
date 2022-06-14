package sunset.annotations.advancedExtands;

import arc.struct.*;
import arc.util.*;
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Modifier.*;
import com.github.javaparser.ast.body.*;
import mindustry.annotations.*;
import mindustry.annotations.util.*;
import mma.annotations.*;
import mma.annotations.SupportedAnnotationTypes;
import sunset.annotations.SnAnnotations.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.io.*;
import java.util.*;

@SupportedAnnotationTypes(BothExtends.class)
public class ExtendsProcessor extends ModBaseProcessor{
    static void write(CompilationUnit unit, String className){
        try{
//            unit.setPackageDeclaration(packageName);
            JavaFileObject object = filer.createSourceFile(unit.getPackageDeclaration().get().getNameAsString() + "." + className);
            OutputStream stream = object.openOutputStream();
            stream.write(unit.toString().getBytes());
            stream.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        return super.process(annotations, roundEnv);
    }

    @Override
    public void process(RoundEnvironment env) throws Exception{
        Seq<Stype> types = types(BothExtends.class);
        for(Stype type : types){
            BothExtends annotation = type.annotation(BothExtends.class);
            String packageName = BaseProcessor.packageName;
            if(!annotation.packageName().contains("\n")){
                packageName = annotation.packageName();
            }

            String[] classNames = annotation.classNames();
            CompilationUnit rootUnit = StaticJavaParser.parse(trees.getPath(type.e).getCompilationUnit().toString());
//            Log.info("imports: @",rootUnit.getImports().toString());

            String name = type.cname().packageName();
//            rootUnit.addImport(name,false,true);
            rootUnit.getImports().add(new ImportDeclaration(name, false, true));
            ClassOrInterfaceDeclaration clazz = rootUnit.getClassByName(type.name()).get();
            if(!clazz.hasModifier(Keyword.PUBLIC)){
                clazz.getModifiers().add(0, new Modifier(Keyword.PUBLIC));
            }
            clazz.getConstructors().forEach(constructorDeclaration -> {
                if(!constructorDeclaration.hasModifier(Keyword.PUBLIC) && !constructorDeclaration.hasModifier(Keyword.PRIVATE)){
                    constructorDeclaration.getModifiers().add(0, new Modifier(Keyword.PUBLIC));
                }
            });
//            Log.info("extra: @",new ImportDeclaration(name,false,true));
//            Log.info("extra2: @",new ImportDeclaration(name,true,true));
            String[] classes = annotation.classes();
            for(int i = 0; i < classes.length; i++){
                String className = classes[i];

                CompilationUnit unit = rootUnit.clone();
                unit.setPackageDeclaration(packageName);

                String newClassName;
                if(classNames.length == classes.length){
                    newClassName = classNames[i];
                }else{
                    newClassName = className.substring(className.contains(".") ? className.lastIndexOf(".") + 1 : 0) + Strings.capitalize(type.name());
                }


                ClassOrInterfaceDeclaration declaration = unit.getClassByName(type.name()).get();
                declaration.setExtendedTypes(new NodeList<>()).addExtendedType(className);
                setName(declaration, newClassName);

                declaration.getAnnotations().clear();

                ;


                write(unit, newClassName);
//                System.out.println(tree.toString());
            }
        }
    }

    private void setName(ClassOrInterfaceDeclaration declaration, String className){
        declaration.setName(className)
        .getConstructors().forEach(c -> c.setName(className));
    }
}

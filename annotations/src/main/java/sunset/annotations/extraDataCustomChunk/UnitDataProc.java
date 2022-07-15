package sunset.annotations.extraDataCustomChunk;

import arc.func.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import arc.util.*;
import arc.util.io.*;
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.Modifier.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import mindustry.annotations.*;
import sunset.annotations.extraDataCustomChunk.ExtraDataProc.*;

import javax.tools.*;
import java.io.*;

public class UnitDataProc{
    public static void generateSerializer(ObjectMap<String, IntMap<SaveField>> revisions, ObjectSet<String> extraImports) throws IOException{
        ClassOrInterfaceDeclaration declaration = new ClassOrInterfaceDeclaration()
        .setName("UnitDataKeySerializer")
        .addModifier(Keyword.PUBLIC);
        processReaderMap(declaration, revisions);
        processGetMethod(declaration, revisions);
//        processInitMethod(declaration, revisions);

        System.out.println(declaration);

        CompilationUnit unit = new CompilationUnit("sunset.gen");
        unit.addImport(IntMap.class);
        unit.addImport(Func2.class);
        unit.addImport(ObjectMap.class);
        unit.addImport(Reads.class);

        for(String anImport : extraImports){
            unit.addImport(StaticJavaParser.parseImport(anImport));
        }

        unit.addType(declaration);

        JavaFileObject object = BaseProcessor.filer.createSourceFile(unit.getPackageDeclaration().get().getNameAsString() + "." + declaration.getNameAsString());
        Writer stream = object.openWriter();
        stream.write(unit.toString());
        stream.close();
    }

    private static void processGetMethod(ClassOrInterfaceDeclaration declaration, ObjectMap<String, IntMap<SaveField>> revisions){
        MethodDeclaration method = declaration.addMethod("getReader")
        .addModifier(Keyword.PUBLIC, Keyword.STATIC)
        .addTypeParameter("DATA_KEY_TYPE")
        .addParameter(String.class, "name")
        .addParameter(int.class, "version")
        .setType(StaticJavaParser.parseType("Func2<Reads, mindustry.gen.Unit, DATA_KEY_TYPE>"))
        .addAnnotation( Nullable.class.getCanonicalName())
        .addAnnotation( org.jetbrains.annotations.Nullable.class.getCanonicalName())
//        .addAnnotation( javax.annotation.Nullable.class.getCanonicalName())
        ;
        BlockStmt body = new BlockStmt();
        method.setBody(body);
        body.addStatement("if (!readerMap.containsKey(name))return null;");
        body.addStatement("IntMap<Func2> map= readerMap.get(name);");
        body.addStatement("return map.get(version);");
    }

    private static void processReaderMap(ClassOrInterfaceDeclaration declaration, ObjectMap<String, IntMap<SaveField>> revisions){
        declaration.addField("ObjectMap<String, IntMap<Func2>>", "readerMap")
        .addModifier(Keyword.PRIVATE, Keyword.STATIC, Keyword.FINAL)
        ;

        BlockStmt stmt = declaration.addStaticInitializer();
        stmt.addStatement("readerMap=new ObjectMap<>();");
        stmt.addStatement("IntMap<Func2> tmpMap;");

        for(Entry<String, IntMap<SaveField>> revision : revisions){
            IntSeq keys = revision.value.keys().toArray();
            keys.sort();
            stmt.addStatement("tmpMap=new IntMap<>();");
            keys.each(key -> {
                SaveField field = revision.value.get(key);
                LambdaExpr readCode = field.readCode.clone();
                String funcType = Strings.format("(Func2<Reads,mindustry.gen.Unit, @>)", field.fullTypeName);
                String statement = "tmpMap.put(" + key + ", (" + funcType + readCode.toString() + "));";
                System.out.println(statement);
                stmt.addStatement(statement);
            });
            stmt.addStatement("readerMap.put(\"" + revision.key + "\", tmpMap);");

        }

    }
}

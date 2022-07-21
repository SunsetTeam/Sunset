package sunset.annotations.extraDataCustomChunk;

import arc.files.*;
import arc.func.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import arc.util.*;
import arc.util.serialization.*;
import com.github.javaparser.*;
import com.github.javaparser.ParserConfiguration.*;
import com.github.javaparser.ast.expr.*;
import com.squareup.javapoet.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;
import mindustry.annotations.util.*;
import mma.annotations.SupportedAnnotationTypes;
import mma.annotations.*;
import sunset.annotations.*;
import sunset.annotations.SnAnnotations.*;

import javax.annotation.processing.*;
import javax.lang.model.element.Name;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.lang.annotation.*;
import java.util.*;

@SupportedAnnotationTypes(SnAnnotations.TestAnnotation.class)
public class ExtraDataProc extends ModBaseProcessor{
    static int unitDataNameIndex;
    static
    ClassName unitDataClassName = ClassName.bestGuess("sunset.utils.UnitData");
    static boolean hasErrors = false;
    static Element currentElement;

    @Override
    public void process(RoundEnvironment env) throws Exception{
        StaticJavaParser.getConfiguration().setLanguageLevel(LanguageLevel.JAVA_16_PREVIEW);
        /*Fi sources = rootDirectory.child("annotation/resources/java_sources");
        sources.delete();
        sources.deleteDirectory();
        sources.delete();
        sources.deleteDirectory();*/

        Set<? extends Element> elements = env.getRootElements();
        ClassName unitDataClassName = ClassName.bestGuess("sunset.utils.UnitData");
        Name unitDataName = findUnitDataName(elements, unitDataClassName);
        unitDataNameIndex = Reflect.<Integer>invoke(unitDataName, "getIndex");
        /*for(Tree decl : trees.getPath(unitDataElement).getCompilationUnit().getTypeDecls()){
            System.out.println(decl);
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
        }*/
        Fi revisionsPath = rootDirectory.child("annotations/src/main/resources/extra-data-revisions");

        ObjectMap<String, IntMap<SaveField>> revisions = new ObjectMap<>();
        collectRevisions(revisionsPath, revisions);
        ObjectSet<String> extraImports = new ObjectSet<>();
        for(Element element : elements){
            if(element.getKind() != ElementKind.CLASS) continue;
//            System.out.println();

            Stype classType = new Stype((TypeElement)element);

            TreePath path = trees.getPath(classType.e);
           /* path.getLeaf().accept(new TreeScanner<Void, Void>(){
                @Override
                public Void visitClass(ClassTree node, Void unused){
                    System.out.println(node.getSimpleName());
                    System.out.println(node);
                    return super.visitClass(node, unused);
                }
            },null);*/
            CompilationUnitTree unit = path.getCompilationUnit();
//            sources.child(unit.getPackageName().toString().replace(".", "/") + "/" + unit.getSourceFile().getName());
            Seq<SaveField> data = new Seq<>();
//            System.out.println(classType.fullName());
            Seq<VariableTree> variableTrees =
            Seq.with(((ClassTree)trees.getPath(classType.e).getLeaf()).getMembers())
            .select(it -> {
//                    System.out.println(it.getClass());
                return it instanceof VariableTree;
            }).as();
            for(ImportTree importTree : trees.getPath(classType.e).getCompilationUnit().getImports()){
//                System.out.println(importTree);
//                if (importTree.toString().contains("*"))throw new IllegalArgumentException();
                extraImports.add(importTree.toString());
//                so
            }
            for(Element member : elementu.getAllMembers(classType.e)){

                if(member instanceof VariableElement){
                    Svar svar = new Svar((VariableElement)member);
                    if(!svar.enclosingType().fullName().equals(classType.fullName())) continue;
                    currentElement = svar.e;
//                    System.out.println("trees.getPath(svar.e): "+trees.getPath(member));
//                    System.out.println("trees.getTree(svar.e): "+trees.getTree(member));
//                    System.out.println(trees.getPath(classType.e));

                    VariableTree variableTree = variableTrees.find(it -> {
//                        System.out.println(it.getName());
//                        System.out.println(it.getNameExpression());
//                        System.out.println("____");
                        return it.getName().contentEquals(svar.name());
                    });
                    variableTree.accept(new MethodInvocationProc(), saveField -> {
                        if(data.contains(it -> it.saveName.equals(saveField.saveName))){
                            err("find name shadowing for data key named \"" + saveField.saveName + "\"", currentElement);
                            hasErrors = true;
                        }else{
                            if(saveField.fullTypeName == null && svar.has(SnAnnotations.DataKeyType.class)){
                                DataKeyType annotation = svar.annotation(DataKeyType.class);
                                saveField.fullTypeName = types(annotation, DataKeyType::value).get(0).fullName();
                            }
                            if(saveField.fullTypeName == null){
//                                System.out.println("svar.tname(): "+svar.tname());
//                                System.out.println("svar.cname(): "+svar.cname());
//                                System.out.println("svar.descString(): "+svar.descString());
//                                System.out.println(svar.e.asType());
//                                System.out.println(svar.e.getConstantValue());
//                                saveField.fullTypeName = svar.tname().toString();
                                saveField.fullTypeName = "<any>";
                                if(saveField.fullTypeName.equals("<any>")){
                                    saveField.fullTypeName = null;
                                    Tree type = variableTree.getType();
                                    if(type instanceof ParameterizedTypeTree){
//                                        System.out.println("full-type: "+type);
                                        //                                        System.out.println("inner-type: "+fullTypeName);
                                        saveField.fullTypeName = ((ParameterizedTypeTree)type).getTypeArguments().get(0).toString();
                                    }
                                }
                            }
                            if(saveField.fullTypeName == null){
                                err("cannot find type for data key named \"" + saveField.saveName + "\"", currentElement);
                                hasErrors = true;
                                return;
                            }
                            data.add(saveField);
                        }
                    });
//                    unit.accept(new MethodInvocationProc(), data);
                }
            }

//            ObjectSet<String> nameSet = new ObjectSet<>();
//            data.find()
            for(SaveField datum : data){
                addSaveField(revisions, datum);
            }
        }
        if(hasErrors){
            throw new RuntimeException();
        }else{
            saveRevisions(revisionsPath, revisions);
            UnitDataProc.generateSerializer(revisions, extraImports);
        }
        //throw new RuntimeException();
    }


    <T extends Annotation> Seq<Stype> types(T t, Cons<T> consumer){
        try{
            consumer.get(t);
        }catch(MirroredTypesException e){
            return Seq.with(e.getTypeMirrors()).map(Stype::of);
        }
        throw new IllegalArgumentException("Missing types.");
    }

    private void saveRevisions(Fi revisionsPath, ObjectMap<String, IntMap<SaveField>> revisions){
        for(Entry<String, IntMap<SaveField>> revision : revisions){
            Fi folder = revisionsPath.child(revision.key);
            IntMap<SaveField> entries = revision.value;
            IntSeq keys = entries.keys().toArray();
            keys.sort();
            keys.each(key -> {
                Jval jval = Jval.newObject();
                SaveField saveField = entries.get(key);
                jval.put("write", saveField.writeCode.toString());
                jval.put("read", saveField.readCode.toString());
                jval.put("fullTypeName", saveField.fullTypeName);
                Fi path = folder.child(key + ".json");
//                System.out.println(path.absolutePath());
                path.writeString(jval.toString());
            });
        }
    }

    public void addSaveField(ObjectMap<String, IntMap<SaveField>> revisions, SaveField datum){
        revisions.get(datum.saveName, IntMap::new).put(datum.version, datum);
    }

    public void collectRevisions(Fi revisionsPath, ObjectMap<String, IntMap<SaveField>> revisions){
        for(Fi folder : revisionsPath.list()){
            if(!folder.isDirectory()) continue;
            String saveName = folder.name();
            for(Fi revisionFile : folder.list()){
                if(!revisionFile.extension().equals("json")) continue;
                int version = Integer.parseInt(revisionFile.nameWithoutExtension());

                Jval jval = Jval.read(revisionFile.readString());
                LambdaExpr writeBlock = StaticJavaParser.parseExpression(jval.get("write").asString()).asLambdaExpr();
                LambdaExpr readBlock = StaticJavaParser.parseExpression(jval.get("read").asString()).asLambdaExpr();
                SaveField datum = new SaveField(saveName, version, writeBlock, readBlock);
                datum.fullTypeName = jval.get("fullTypeName").asString();
                addSaveField(revisions, datum);
            }
        }
    }

    public Name findUnitDataName(Set<? extends Element> elements, ClassName className){
        Element unitDataElement = Seq.with(elements).find(it -> {
            if(it.getKind() != ElementKind.CLASS) return false;

            Stype classType = new Stype((TypeElement)it);
            return classType.fullName().equals(className.canonicalName());
        });
        Tree unitDataTree = Seq.with(trees.getPath(unitDataElement).getCompilationUnit().getTypeDecls()).find(it -> it instanceof ClassTree && ((ClassTree)it).getSimpleName().contentEquals(className.simpleName()));
        Name unitDataName = ((ClassTree)unitDataTree).getSimpleName();
        return unitDataName;
    }

    static class SaveField{
        public String saveName;
        public int version;
        public LambdaExpr writeCode;
        public LambdaExpr readCode;
        public String fullTypeName;

        public SaveField(String saveName, int version, LambdaExpr writeCode, LambdaExpr readCode){
            this.saveName = saveName;
            this.version = version;
            this.writeCode = writeCode;
            this.readCode = readCode;
        }
    }

    private static class MethodInvocationProc extends TreeScanner<Void, Cons<SaveField>>{
//static ;
/*
        @Override
        public Void visitVariable(VariableTree node, Cons<SaveField> saveFieldCons){
            System.out.println("node: "+node);
            return super.visitVariable(node, saveFieldCons);
        }*/

        @Override
        public Void visitMethodInvocation(MethodInvocationTree node, Cons<SaveField> consumer){
            customVisitMethodInvocation(node, consumer);
                    /*
                    Log.info("kind(@), class(@), element: @", element.getKind(), element.getClass(), element);
                    System.out.println();
                    Log.info("methodSelect(@), typeArguments(@), arguments(@)", methodSelect, node.getTypeArguments(), node.getArguments());
                    System.out.println(methodSelect.getClass());
                    System.out.println(node);
                    * */
            return super.visitMethodInvocation(node, consumer);
        }

        public void customVisitMethodInvocation(MethodInvocationTree node, Cons<SaveField> consumer){
            String string = node.toString();
            if(!string.contains(unitDataClassName.simpleName())){
                return;
            }
            ExpressionTree methodSelect = node.getMethodSelect();

            if(!(methodSelect instanceof /*com.sun.tools.javac.tree.JCTree.JCFieldAccess*/ MemberSelectTree)){
                return;
            }
            MemberSelectTree fieldAccess = (MemberSelectTree)methodSelect;
            if(!(fieldAccess.getExpression() instanceof MethodInvocationTree && isCreatingDataKey((MethodInvocationTree)fieldAccess.getExpression()))){
//                System.out.println("Second return");
//                System.out.println(fieldAccess);
                return;
            }
            if(!fieldAccess.getIdentifier().contentEquals("shouldSave")){
                return;
            }
//            System.out.println(node);

            List<? extends ExpressionTree> arguments = node.getArguments();
            String saveName = (String)((LiteralTree)arguments.get(0)).getValue();
            int version = (Integer)((LiteralTree)arguments.get(1)).getValue();
            LambdaExpr writeBlock = StaticJavaParser.parseExpression(arguments.get(2).toString()).asLambdaExpr();
            LambdaExpr readBlock = StaticJavaParser.parseExpression(arguments.get(3).toString()).asLambdaExpr();
            consumer.get(new SaveField(saveName, version, writeBlock, readBlock));


//            System.out.println(saveName);
//            System.out.println(version);
//            System.out.println(arguments.get(2).getClass());
//            System.out.println(arguments.get(3).getClass());

//            System.out.println(arguments);
                   /* ExpressionTree expression = fieldAccess.getExpression();

                    if(!(
                    (expression instanceof IdentifierTree && Reflect.<Integer>invoke(((IdentifierTree)expression).getName(), "getIndex") == unitDataNameIndex)
                    || (expression instanceof MemberSelectTree && expression.toString().equals(unitDataClassName.canonicalName()))
                    )){
                        return;
                    }
                    fieldAccess.getIdentifier().equals("data")   */
        }

        public boolean isCreatingDataKey(MethodInvocationTree node){
            String string = node.toString();
            if(!string.contains(unitDataClassName.simpleName())){
                return false;
            }
            ExpressionTree methodSelect = node.getMethodSelect();

            if(!(methodSelect instanceof /*com.sun.tools.javac.tree.JCTree.JCFieldAccess*/ MemberSelectTree)){
//                System.out.println("methodselect: "+methodSelect.getClass());
                return false;
            }
            MemberSelectTree fieldAccess = (MemberSelectTree)methodSelect;

            ExpressionTree expression = fieldAccess.getExpression();

            if(!(
            (expression instanceof IdentifierTree && Reflect.<Integer>invoke(((IdentifierTree)expression).getName(), "getIndex") == unitDataNameIndex)
            || (expression instanceof MemberSelectTree && expression.toString().equals(unitDataClassName.canonicalName()))
            )){
                return false;
            }
            return fieldAccess.getIdentifier().contentEquals("dataKey");
        }
    }
}

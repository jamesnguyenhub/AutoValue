package com.tuyenmonkey.autovalue.compiler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.JavaFileObject;

/**
 * Created by Tuyen Monkey on 3/23/17.
 */

@SupportedAnnotationTypes("com.tuyenmonkey.autovalue.annotation.AutoValue")
public class AutoValueProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement annotation : annotations) {
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      List<Element> setterMethods = collectSetterMethods(annotatedElements);

      if (setterMethods.isEmpty()) continue;

      String className = ((TypeElement)setterMethods.get(0).getEnclosingElement()).getQualifiedName().toString();
      Map<String, String> setterMap = buildSetterMethodsMap(setterMethods);

      writeToFile(className, setterMap);
    }
    return true;
  }

  private void writeToFile(String className, Map<String, String> setterMap) {
    String packageName = null;
    int lastDot = className.lastIndexOf('.');
    if (lastDot > 0) {
      packageName = className.substring(0, lastDot);
    }

    String simpleClassName = className.substring(lastDot + 1);
    String builderClassName = className + "Builder";
    String simpleBuilderClassName = builderClassName.substring(lastDot + 1);
    try {
      JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(builderClassName);
      PrintWriter writer = new PrintWriter(javaFileObject.openWriter());

      if (packageName != null) {
        writer.print("package ");
        writer.print(packageName);
        writer.println(";");
        writer.println();
      }

      writer.print("public class ");
      writer.print(simpleBuilderClassName);
      writer.print(" {");
      writer.println();

      writer.print("  private ");
      writer.print(simpleClassName);
      writer.print(" object = new ");
      writer.print(simpleClassName);
      writer.println("();");
      writer.println();

      writer.print("  public ");
      writer.print(simpleClassName);
      writer.println(" build() {");
      writer.println("    return object;");
      writer.println("  }");
      writer.println();

      for (Map.Entry entry : setterMap.entrySet()) {
        String methodName = entry.getKey().toString();
        String firstParamType = entry.getValue().toString();

        writer.print("  public ");
        writer.print(simpleBuilderClassName);
        writer.print(" ");
        writer.print(methodName);

        writer.print("(");

        writer.print(firstParamType);
        writer.println(" value) {");
        writer.print("    object.");
        writer.print(methodName);
        writer.println("(value);");
        writer.println("    return this;");
        writer.println("  }");
        writer.println();
      }

      writer.println("}");

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Map<String, String> buildSetterMethodsMap(List<Element> setterMethods) {
    Map<String, String> setterMap = new HashMap<>();

    for (Element setterMethod : setterMethods) {
      String simpleMethodName = setterMethod.getSimpleName().toString();
      String firstParamType = ((ExecutableType)setterMethod.asType()).getParameterTypes().get(0).toString();
      setterMap.put(simpleMethodName, firstParamType);
    }

    return setterMap;
  }

  private List<Element> collectSetterMethods(Set<? extends Element> annotatedElements) {
    List<Element> setterMethods = new ArrayList<>();

    for (Element annotatedElement : annotatedElements) {
      if (((ExecutableType)annotatedElement.asType()).getParameterTypes().size() == 1
          && annotatedElement.getSimpleName().toString().startsWith("set")) {
        setterMethods.add(annotatedElement);
      }
    }

    return setterMethods;
  }
}

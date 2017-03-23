package com.tuyenmonkey.autovalue.compiler;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

/**
 * Created by Tuyen Monkey on 3/23/17.
 */

@SupportedAnnotationTypes("com.tuyenmonkey.autovalue.annotation.AutoValue")
public class AutoValueProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    return false;
  }
}

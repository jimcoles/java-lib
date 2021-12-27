/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2018 through present.
 *
 * Licensed under the following license agreement:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */

package org.jkcsoft.annos.xpack;

import org.apache.log4j.Logger;
import org.jkcsoft.java.util.JavaHelper;
import org.jkcsoft.java.util.Strings;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

/**
 * Annotation processor for my XPack set of annotations.
 *
 * @author Jim Coles
 */
@SupportedAnnotationTypes("org.jkcsoft.annos.xpack.XPack")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class XPackProcessor extends AbstractProcessor {

    private static final Logger log = Logger.getLogger(XPackProcessor.class);

    public XPackProcessor() {

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log.info(Strings.replace("process called with {0} annotations; env isover = {1}",
                                 annotations.size(),
                                 roundEnv.processingOver())
                );
        if (roundEnv.processingOver())
        for (TypeElement anno : annotations) {
            Set<? extends Element> taggedElems = roundEnv.getElementsAnnotatedWith(anno);
            log.info("tagged: " + JavaHelper.EOL + Strings.buildNewlineList(taggedElems));
            for (Element taggedElem : taggedElems) {
                PackageElement packageElem = (PackageElement) taggedElem;
                log.info("handling: " + packageElem);
                packageElem.getQualifiedName();
                List<? extends Element> packageChildren = packageElem.getEnclosedElements();
                log.info("pack encl: " + JavaHelper.EOL + Strings.buildNewlineList(packageChildren));
            }
        }
        return true;
    }

}

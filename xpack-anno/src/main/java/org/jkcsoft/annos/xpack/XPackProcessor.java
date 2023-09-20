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

import org.jkcsoft.java.util.JavaHelper;
import org.jkcsoft.java.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Annotation processor for my XPack set of annotations.
 *
 * @author Jim Coles
 */
@SupportedAnnotationTypes("org.jkcsoft.annos.xpack.XPack")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class XPackProcessor extends AbstractProcessor {
    
    private static final Logger log = LoggerFactory.getLogger(XPackProcessor.class);
    
    public XPackProcessor() {
    
    }
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        log.info("initialized");
    }
    
    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation,
                                                         ExecutableElement member, String userText)
    {
        return super.getCompletions(element, annotation, member, userText);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log.info(Strings.fmt("process called with {0} annotations; env processingOver = {1}",
                             annotations.size(),
                             roundEnv.processingOver()) );
        
        for (TypeElement annotatedElement : annotations) {
            Set<? extends Element> taggedElems = roundEnv.getElementsAnnotatedWith(annotatedElement);
            XPack xpack = annotatedElement.getAnnotation(XPack.class);
            log.info("tagged: " + JavaHelper.EOL + Strings.buildNewlineList(taggedElems));
            List<? extends Element> packageChildren = Collections.emptyList();
            for (Element taggedElem : taggedElems) {
                PackageElement packageElem = (PackageElement) taggedElem;
                log.info("handling: " + packageElem);
                packageElem.getQualifiedName();
                packageChildren = packageElem.getEnclosedElements();
                log.info("pack encl: " + JavaHelper.EOL + Strings.buildNewlineList(packageChildren));
            }
            List<Class> annotatedClasses = new LinkedList<>();
            packageChildren.forEach(annoElem -> {
                if (annoElem.getKind().equals(ElementKind.CLASS)) {
                
                }
            });
            if (xpack != null) {
                try {
                    XPackHandler handler = xpack.handler().getDeclaredConstructor().newInstance();
                    handler.handle(annotatedClasses);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                log.info("could not find any xpack annotations");
            }
        }
        
        return true;
    }
    
}

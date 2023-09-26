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
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

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
        
        Properties properties = new Properties();
        for (Element element : roundEnv.getElementsAnnotatedWith(XPack.class)) {
            // Process the annotated element (e.g., class, method, field)
            // Collect information and add it to the properties
            properties.setProperty("element_" + element.getSimpleName(), element.toString());
        }
        
        // Generate the resource file (e.g., my_annotations.properties)
        try (Writer writer = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "my_annotations.properties").openWriter()) {
            properties.store(writer, "xpack-elements.props");
        } catch (IOException e) {
            log.error("", e);
        }
        
        return true;
    }
    
}

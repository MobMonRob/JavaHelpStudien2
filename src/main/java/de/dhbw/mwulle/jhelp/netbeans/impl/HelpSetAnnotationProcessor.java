package de.dhbw.mwulle.jhelp.netbeans.impl;

import de.dhbw.mwulle.jhelp.netbeans.api.HelpSetRegistration;
import org.openide.filesystems.annotations.LayerBuilder;
import org.openide.filesystems.annotations.LayerGeneratingProcessor;
import org.openide.filesystems.annotations.LayerGenerationException;
import org.openide.util.lookup.ServiceProvider;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.Set;

@ServiceProvider(service = Processor.class) // Creates a services entry in META-INF.services
public class HelpSetAnnotationProcessor extends LayerGeneratingProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(HelpSetRegistration.class.getCanonicalName());
    }

    @Override
    protected boolean handleProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) throws LayerGenerationException {
        if (roundEnvironment.processingOver()) {
            return false;
        }

        for (Element element : roundEnvironment.getElementsAnnotatedWith(HelpSetRegistration.class)) {
            if (!(element instanceof PackageElement)) {
                System.out.println("Error: " + element.getClass());
                // TODO: 11/6/23 Log this
                continue;
            }
            PackageElement packageElement = (PackageElement) element;
            LayerBuilder builder = layer(element);
            HelpSetRegistration annotation = element.getAnnotation(HelpSetRegistration.class);
            builder.file(String.format("Services/JavaHelp/%s.xml", annotation.helpSet())).contents(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<!DOCTYPE helpsetlink PUBLIC \"-//DHBW_Karlsruhe//DTD JavaHelp Help Set link 1.0//EN\" \"helpsetlink.dtd\">\n" +
                    "<helpsetlink url=\"/%s\"/>", packageElement.getQualifiedName().toString().replace('.', '/') + '/' + annotation.helpSet())).write();
        }

        return false;
    }
}

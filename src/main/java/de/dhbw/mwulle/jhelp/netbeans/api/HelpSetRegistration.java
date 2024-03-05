package de.dhbw.mwulle.jhelp.netbeans.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for registration a help set.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface HelpSetRegistration {

    /**
     * @return the path to the help set file, relative to the location of this annotation
     */
    String helpSet();
}

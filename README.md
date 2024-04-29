# JHelp - A replacement for JavaHelp on NetBeans

## Disclaimer

The project is in an early state of development, so it is not advised to use it in real world 
applications. If you have feedback or feature suggestions, please create a new
[GitHub Issue](https://github.com/MobMonRob/JavaHelpStudien/issues/new).

## Description

JHelp is a Maven NetBeans Module that should replace JavaHelp and the JavaHelp NetBeans Module.

# Limitations
JHelp currently only supports a limited subset of features of JavaHelp and only HelpSets from the version 2.0.

Features it does support:
- Registering a HelpSet via annotation
- Toc and Index view and selection
- Viewing one HelpSet
- Netbeans HelpSet context
- Merging multiple HelpSets
- Fulltext search

Features it does **not** support:
- Favorites
- and more

## Installation

To install JHelp in your locale maven repository clone and then run:

```shell
mvn clean install
```

## Usage

First, make sure you followed to installation steps in ## Installation, since JHelp is currently not in any maven repo.
To use the JHelp NetBeans module inside your NetBeans-Platform application, add the
maven dependency to your Project pom.xml:

```xml
<dependency>
    <groupId>de.dhbw.mwulle</groupId>
    <artifactId>jhelp</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**Note:** JHelp uses netbeans platform version `RELEASE200`

When setting up JHelp, you can either configure it manually or use the NetBeans IDE Wizard to let it create a simple HelpSet.
You can find a demo repository [here](https://github.com/DerFrZocker/DHBW-JHelp-Demo).

### Manually

1. Create a `package-info.java` in the package which should house the HelpSet.
2. Add the `@HelpSetRegistration` annotation to the `package-info.java`
   and add the path to the `hs` file in the annotations `helpset` field.
    Note: The path is relative to the package the `package-info.java` is in.
    ```java
    @HelpSetRegistration(helpSet = "Animals.hs")
    package de.derfrzocker.jhelp.demo.docs;

    import de.dhbw.mwulle.jhelp.netbeans.api.HelpSetRegistration;
    ```
3. Create a directory with the same path as the package of the `package-info.java` in your resource directory.
4. Now you can create your HelpSet in this directory with the `hs` being in the location set in the`@HelpSetRegistration` annotation.

### Via NetBeans IDE Wizard

1. Select the module you want to create a HelpSet in.
2. Go to the `New File` wizard.
3. Go to the `Module Development` tab and select `JavaHelp Help Set`.
4. Go to the created `package-info.java` and change the import of the `@HelpSetRegistration` annotation from `org.netbeans.api.javahelp.HelpSetRegistration` to `de.dhbw.mwulle.jhelp.netbeans.api.HelpSetRegistration`.
5. Go to the `pom.xml` and delete the added `org-netbeans-modules-javahelp` dependency.
6. Modify the created HelpSet to your need.

## License

JHelp is licensed under the [Apache 2.0 License](LICENSE).

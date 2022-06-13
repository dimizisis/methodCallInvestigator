
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import infrastructure.entities.MethodCallSet;
import infrastructure.entities.MethodDecl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Set;

/*
 *
 *  *
 *  *  * Copyright (C) 2021 UoM - University of Macedonia
 *  *  *
 *  *  * This program and the accompanying materials are made available under the
 *  *  * terms of the Eclipse Public License 2.0 which is available at
 *  *  * https://www.eclipse.org/legal/epl-2.0/
 *  *  *
 *  *  * SPDX-License-Identifier: EPL-2.0
 *  *
 *
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2)
            System.exit(-1);

        /* Input provided via command line arguments (handling slashes & whitespaces) */
        String projectDir = args[0].replace("\\", "/").replace("\\s+", "");
        String startingFile = args[1].replace("\\", "/").replace("\\s+", "");

        /* The initial MethodDeclaration provided as input, dummy example */
        MethodDeclaration methodDeclaration = StaticJavaParser.parse(new File(startingFile)).findAll(ClassOrInterfaceDeclaration.class).get(0).getMethods().get(0);

        /* Create a facade object, must provide: (i) project root directory (absolute path),
                                                 (ii) starting file path (absolute path) &
                                                 (iii) the starting method's MethodDeclaration object */
        InvestigatorFacade facade = new InvestigatorFacade(projectDir, startingFile, methodDeclaration);

        /* If all the methods of the starting file must be investigated, you can create a facade directly as: */
//        InvestigatorFacade facade = new InvestigatorFacade(projectDir, startingFile);

        /* Just call start, method call sets can also be retrieved by getter */
        Set<MethodCallSet> methodCallSets = facade.start();

        /* If null, operation failed */
        if (Objects.isNull(methodCallSets))
            return;

        /* Example Results Printing */
        printResults(methodCallSets);

    }

    private static void printResults(Set<MethodCallSet> results) {
        for (MethodCallSet methodCallSet : results) {
            System.out.printf("Methods involved with %s method: %s", methodCallSet.getMethod().getQualifiedName(), System.lineSeparator());
            for (MethodDecl methodCall : methodCallSet.getMethodCalls()) {
                System.out.print(methodCall.getFilePath() + " | " + methodCall.getQualifiedName());
                System.out.printf(" | StartLine: %d | EndLine: %d%s", methodCall.getCodeRange().getStartLine(), methodCall.getCodeRange().getEndLine(), System.lineSeparator());
            }
            System.out.println();
        }
    }
}

# Using Structured Testing Frameworks
This program provides an example of how to add structured tests to your projects, and furthermore, 
how to cleanly integrate a structured testing framework such as JUnit. By clean integration, I mean 
that CT Test Suite will build your project even though you make use of an unsupported library such as JUnit. 
 
The basic idea is two have two source folders:

* `src/` -- where the main project packages (lexer, parser, etc.) reside
* `testsuite/` -- where the testing classes reside
    
Everything under src/ is old news. You will find the interesting stuff under testsuite/.
You will find that we extend the packages from src/ with testing classes. For example, 
we add the class testsuite/parser/ParserTests.java to the parser package. This class contains 
a handful of test cases for the parser which is defined in src/parser/Parser.java.
   
This program implements a parser for the language of sequenced well-balanced parentheses. 
It is given by the following grammar:

```   
S ::= S S 
    | ( S ) 
    | epsilon 
```
where epsilon denotes the empty string.

### Example outputs
It may be instructive to get an intuition for the kind of programs that the parser accepts. The parser has a simple command line interface:
```
$ java -cp bin Main
Usage: java Main string
```
where `string` is the input. Below are some *ad-hoc* tests:
```
$ java -cp bin Main "()"
$ java -cp bin Main "("
Parsing error: expected (RPAR) found (EOF) at 1:2
Parse errors: 1
$ java -cp bin Main "(())()(())"
$ java -cp bin Main "(((x)))"
Lexing error: unrecognised character (x) at 1:4
Parsing error: expected (RPAR) found (INVALID) at 1:4
Parse errors: 2
```

## Building the project
I have provided two build files:

* `ctbuild.xml` the standard build file from the [ct repository](https://bitbucket.org/cdubach/ct-16-17) (which is the build file used by the automated feedback system).
* `build.xml` which contains a recipe for building JUnit test reports.

The project builds with the standard build file, e.g.
```
$ ant -f ctbuild.xml build
Buildfile: /home/dhil/teaching/ct/ct-structured-tests-example/ctbuild.xml

build-subprojects:

init:
    [mkdir] Created dir: /home/dhil/teaching/ct/ct-structured-tests-example/bin

build-project:
     [echo] ct-16-17: /home/dhil/teaching/ct/ct-structured-tests-example/ctbuild.xml
    [javac] Compiling 7 source files to /home/dhil/teaching/ct/ct-structured-tests-example/bin

build:

BUILD SUCCESSFUL
Total time: 0 seconds
```
The other build file works as well:
```
$ ant -f build.xml build
Buildfile: /home/dhil/teaching/ct/ct-structured-tests-example/build.xml

build:
    [mkdir] Created dir: /home/dhil/teaching/ct/ct-structured-tests-example/bin
    [javac] Compiling 7 source files to /home/dhil/teaching/ct/ct-structured-tests-example/bin

BUILD SUCCESSFUL
Total time: 0 seconds
```
In addition it builds the test classes, e.g.
```
$ ant -f build.xml test-compile
Buildfile: /home/dhil/teaching/ct/ct-structured-tests-example/build.xml

build:

test-compile:
    [mkdir] Created dir: /home/dhil/teaching/ct/ct-structured-tests-example/bin/test
    [javac] Compiling 2 source files to /home/dhil/teaching/ct/ct-structured-tests-example/bin/test

BUILD SUCCESSFUL
Total time: 0 seconds
```
The compiled test cases can be found under `bin/test/`. The build file also contains a rule for running test cases:
```
$ ant -f build.xml test
Buildfile: /home/dhil/teaching/ct/ct-structured-tests-example/build.xml

build:

test-compile:

test:
    [junit] Running lexer.TokeniserTests
    [junit] Testsuite: lexer.TokeniserTests
    [junit] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 sec
    [junit] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 sec
    [junit] 
    [junit] ------------- Standard Output ---------------
    [junit] Lexing error: unrecognised character (c) at 1:3
    [junit] ------------- ---------------- ---------------
    [junit] Running parser.ParserTests
    [junit] Testsuite: parser.ParserTests
    [junit] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.013 sec
    [junit] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.013 sec
    [junit] 
    [junit] ------------- Standard Output ---------------
    [junit] Parsing error: expected (RPAR) found (EOF) at 1:2
    [junit] Parsing error: expected (EOF) found (RPAR) at 1:3
    [junit] Parsing error: expected (EOF) found (RPAR) at 1:5
    [junit] Parsing error: expected (RPAR) found (EOF) at 1:8
    [junit] ------------- ---------------- ---------------

BUILD SUCCESSFUL
Total time: 0 seconds
```
## Writing test cases
See the files [`testsuite/lexer/TokeniserTests.java`](testsuite/lexer/TokeniserTests.java) and [`testsuite/parser/ParserTests.java`](testsuite/parser/ParserTests.java) for example test cases.

## Exercises
These exercises are optional. If you have no experience with structured testing then I strongly encourage you to attempt exercises 2 and 3.

1. Apply the left recursion elimination algorithm to the grammar.
2. Implement two failing test cases for either the tokeniser or parser (or both!). Thereafter, implement two correct test cases.
3. Implement a testing class for the scanner.
4. Modify the parser and lexer to accept the language of alternating sequenced homogeneous well-balanced parentheses and braces, given by the grammar `S ::= S P | ( S ) | epsilon` where `P ::= { P } S | epsilon`. In addition fix the failing test cases and write some new test cases.

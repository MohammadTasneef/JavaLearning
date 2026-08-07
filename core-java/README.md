# Core Java Fundamentals

Exercises covering arrays, strings and object orientation. Each class has a `main` method
and runs on its own.

## Layout

```
src/main/java/com/corejava/
  arrays/    array traversal, copying, rotation, min/max
  oop/       constructors, static vs instance, inheritance, overloading, overriding,
             abstract classes, interfaces
  strings/   reversal, palindromes, vowel and whitespace counting, char arrays
```

## Running

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=com.corejava.strings.StringPalindrome
```

Or run any class directly from the IDE, since each one has its own `main`.

`MaximumNumber` and `ReverseArrayCopy` read from standard input; the rest use fixed values.

## Requirements

Java 17 and Maven 3.8+.
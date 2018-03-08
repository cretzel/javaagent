# Simple javaagent demo

A simple javaagent using javassist to instrument bytecode.

After mvn install, run Main.java with the following VM argument

```
-javaagent:"javaagent-agent/javaagent-assist/target/javaagent-javassist-1.0-SNAPSHOT-jar-with-dependencies.jar"
```


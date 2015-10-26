# slf4j-compatibility-suite

Ensure `slf4j-api` retains source and binary compatibility with the
addition of Java 8 lambda support.

Tested against (so far):

* slf4j-simple 1.7.12
* slf4j-simple 1.6.6
* Logback 1.0.1

No artifacts from this suite should end up in a user's local repository,
so long as the `install` command is not given to Maven.

## Running

The best way to run a build is as follows:

```sh
mvn clean verify -Dit-repo-directory=`pwd`/it/target/it-repo
```

This will cause all dependencies required by the integration tests to be
copied into the `it/target/it-repo` directory, ensuring isolation from the
local repository. The default execution works, but will write to a temp
directory which may not be cleaned up in a timely fashion. Example:

```sh
mvn clean verify
```

## Compatibility notes

Virtually all uses of the SLF4J API should be source and binary compatible,
with the exception of direct use of the `null` keyword for arguments.
Example:

```java
logger.info("message", null);
```

Direct use of the `null` keyword is not source-compatible due to overloading
restrictions. While it is highly probable that real-world cases of this sort
of code do exist, those cases are probably very rare. It is even less likely
that those cases have a sane rationale for their existence.

Lambdas that throw exceptions are the major source of (potential) runtime
incompatibility. If the process of evaluating the lambdas does not catch
exceptions (similar to what is done in `org.slf4j.helpers.MessageFormatter`)
then logging operations can fail. Whether this is ultimately allowed will be
a design choice agreed upon by the project lead(s). The best option is
probably to be completely safe and allow implementers of the API to override
the default behavior to be more inline with their own style and performance
goals.

## Backporting

Thanks to the [Retrolambda](https://github.com/orfjackal/retrolambda) project,
it is possible to run code that uses the Java 8 lambda features on older JREs,
as long as dependencies are carefully managed. The `Logger` API is therefore
safe to use, and a test module in this suite is present as a proof of concept.
The [maven plugin]
(http://orfjackal.github.io/retrolambda/retrolambda-maven-plugin/plugin-info.html)
is a good starting point for those who want to use lambdas but execute under
older implementations of Java.


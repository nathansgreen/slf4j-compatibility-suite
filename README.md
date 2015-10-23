# slf4j-compatibility-suite
Ensure slf4j-api retains source and binary compatibility.

Tested against:

* slf4j-simple 1.7.12
* slf4j-simple 1.6.6

Should not pollute the local maven repository.

## Running

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

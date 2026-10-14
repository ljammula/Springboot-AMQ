# Agent instructions

The Spring Boot application and Maven project live in `AmqDemo/`. It targets Java 8 and uses ActiveMQ.

## Verification

- Run `mvn -B -o test` from `AmqDemo/`.
- Keep controller changes covered by tests that do not require an external broker unless the change specifically concerns broker integration.
- Do not edit the checked-in zip archives; the Maven source tree is authoritative.

---
path: "**/*.java"
---

### 1. Avoid Anemic Class

An Anemic Class is an anti-pattern where Java classes contain only data (fields + getters/setters) and no behavior. All business logic ends up scattered across services, making the class a glorified data container.

**Symptoms**
- Service methods do all the work: validation, state transitions, computations
- Classes are pure data bags with no methods beyond accessors
- Logic that naturally belongs to a type is written inline at the call site

**Principle**
Classes should own the behavior that operates on their data. A type that knows its own structure is the right place to encapsulate rules about that structure.

- Conversion between a type and its primitive representation → method on that type
- Validation of a value → method on that type or its factory
- State transitions → method on the class, not the service
- Derived values computed from fields → method on the class

**Example**
```java
// Anemic — logic leaks to the caller
if (user.getStatus() != null && user.getStatus().equals("ACTIVE")) { ... }
order.setStatus("CANCELLED");
order.setUpdatedAt(LocalDateTime.now());

// Rich — behavior lives on the class
if (user.isActive()) { ... }
order.cancel();
```

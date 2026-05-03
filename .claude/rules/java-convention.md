---
path: "**/*.java"
---

# Java Convention

```
Follow the rules when implementing Java classes.
```

### Avoid Anemic Class

An anemic class is an antipattern where Java classes contain only data (fields + getter/setter) and no behavior. Classes should own the behavior that operates on their data.

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
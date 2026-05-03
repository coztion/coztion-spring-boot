---
paths:
  - "src/main/java/**"
---

# Architecture Convention

The codebase is organized into two **conceptual** layers—**presentation** and **application**—and three **concrete**
roles—**controller**, **service**, and **repository**. Concepts describe _what_ is separated; concrete roles describe
_how_ that split is usually implemented.

---

### Architecture

| Layer            | Role           | Responsibility                                                                                               |
|------------------|----------------|--------------------------------------------------------------------------------------------------------------|
| **Presentation** | **Controller** | Boundary validation, invoke use cases, map results to response DTOs.                                         |
|                  | **DTO**        | Types used only at the API boundary; kept on the feature's presentation side.                                |
| **Application**  | **Service**    | Rules, orchestration, transaction boundaries where the app defines a unit of work.                           |
|                  | **Repository** | Load and persist feature state behind a narrow surface.                                                      |
|                  | **Model**      | Vocabulary shared by **service** and **repository**; distinct from DTOs unless layers are merged on purpose. |

### Dependency Direction

- Presentation may call application (e.g. controller → service, map model → DTO).
- Application may call repository (e.g. service → repository).
- Application must not depend on presentation (no DTOs or transport types inside services/repositories).

### Package Layout

**`{root-package}`** — root package. **`{domain-name}`** — one bounded context; repeat the subtree per domain. *`common`* — shared code not tied to a single domain.

```
{root-package}/
├── {domain-name}/
│   ├── dto/
│   ├── model/
│   ├── *Controller          # presentation
│   ├── *Service             # application
│   └── *Repository
└── common
```

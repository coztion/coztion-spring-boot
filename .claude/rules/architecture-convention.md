---
paths:
  - "src/main/java/**"
---

# Architecture Convention

The codebase is organized into two **conceptual** layers — **presentation** and **application** — and three **concrete**
roles — **controller**, **service**, and **repository**. Concepts describe _what_ is separated; concrete roles describe
_how_ that split is usually implemented.

---

### Architecture

| Layer            | Role           | Responsibility                                                                     |
|------------------|----------------|------------------------------------------------------------------------------------|
| **Presentation** | **Controller** | Boundary validation, invoke use cases, map results to response DTOs.               |
| **Application**  | **Service**    | Rules, orchestration, transaction boundaries where the app defines a unit of work. |
|                  | **Repository** | Load and persist feature state behind a narrow surface.                            |
|                  | **Model**      | All model types for a feature                                                      |

### Dependency Direction

- Presentation may call application (controller -> service).
- Application may call repository (service -> repository).
- Application must not depend on presentation (no transport types inside services/repositories).
- Model types are shared across both layers within the same feature.
- Within the same bounded context, a service may call its own repository directly.
- Across bounded contexts, communication must go through the target service — never call another context's repository
  directly.

### Package Layout

**`{root-package}`** — root package. **`{domain-name}`** — one bounded context; repeat the subtree per domain.

```
{root-package}/
├── {domain-name}/
│   ├── model/       # all model types for the feature
│   ├── *Controller   # presentation
│   ├── *Service      # application
│   └── *Repository
├── common/           # shared code not tied to a single domain
└── core/             # framework configuration and security
```

---
name: commit-message
description: Use when user requests commit.
---

### Commit Message Rules

- Determine the commit type from the changes to commit (staged changes if any, otherwise unstaged): `fix`, `feat`, `refactor`, `docs`, `chore`, `style`, `test`, etc.
- Fill the template matching the type:
    - `fix` → follow @.claude/skills/commit-message/templates/bugfix-template.md
    - `feat` → follow @.claude/skills/commit-message/templates/feature-template.md
    - Other types → use the same shape as the feature template, with `feat` replaced by the actual type (e.g. `refactor: {message}`).
- `{message}` should be a concise, single-line summary of the change, written in English to match this repository's existing commit history (see `git log`).
- For the bugfix template, fill `Issue` / `Cause` / `Solve` based only on what the diff actually shows. Do not fabricate details that aren't evidenced by the change.
- Do not interpret the Markdown. Wrap the generated commit message in a code block when showing it to the user.

### Commit Execution Rules

- Check `git status`, `git diff` (staged and unstaged), and `git log` before drafting the message.
- Determine the commit target:
    - If there are staged changes, commit only those — do not stage any additional files.
    - If there are no staged changes, target all changed files (tracked modifications and untracked files) and stage them by name before committing — never `git add -A` or `git add .`.
- Create the commit with the generated message via heredoc.
- Do not add a `Co-Authored-By: Claude` trailer or any other Claude authorship line to the commit message.
- Always create a new commit. Never amend an existing commit unless explicitly requested.
- One user request creates exactly one commit. Determine the target once, create that single commit, then stop — do not re-check git status afterward and create additional commits in the same run, even if changes remain uncommitted. Further commits require a new explicit user request.

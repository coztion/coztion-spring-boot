---
name: generate-pull-request
description: Use when user requests to generate a pull request.
---

### Pull Request Generation Rules

- Generate pull request contents.
- Follow @.claude/skills/pull-request/templates/pull-request-template.md and fill braces with contents following:
    - Title should summarize the major changes ot the pull request. (Do not include commit type)
    - Description should show details about the pull request.
    - Changes should show each change's.
- Do not create a file or request to create a PR. Just show the plain text.
- Do not interpret the Markdown. Wrap the entire output in a code block to prevent Markdown rendering.
- Write a pull request content in Korean.

### Pull Request Target Rules

- Changes can be checked by the difference between remote develop branch and current branch.
    - Check by next git command : `git diff origin/develop HEAD`
    - Follow if user request for difference between different branch.

### After Generation

- After showing the output, copy the same content (title on the first line, followed by a blank line, then the PR template content) to the clipboard using `pbcopy`.

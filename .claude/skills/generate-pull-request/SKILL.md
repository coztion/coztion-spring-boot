---
name: generate-pull-request
description: Use when user requests to generate a pull request.
---

- Write a title, description about the changes in this branch.
    - Title should summarize the major changes of the pull request. (Do not include commit type)
    - Description should show details about the pull request.
- Output must include the title at the top, before the PR template content.
- Changes can be checked by the difference between remote develop branch and current branch
    - Check by next git command : `git diff origin/develop HEAD`
- PR template should follow @.claude/skills/pull-request/templates/pull-request-template.md
- Write a pull request content in Korean.
- Do not create a file or request to create a PR. Just show the plain text.
- Do not interpret the Markdown. Wrap the entire output in a code block to prevent Markdown rendering.

# PR Documentation Checklist

Use this checklist whenever a change affects behavior, architecture, or developer workflows.

## PR creation

- [ ] Push branch to remote: `git push -u origin <branch>`
- [ ] Create PR via GitHub CLI: `gh pr create --title "..." --body "..." --base main`

## Required updates

- [ ] Update `docs/requirements/FUNCIONALIDADES_IMPLEMENTADAS.md` for controller/response/error/persistence/i18n changes.
- [ ] Update architecture docs in `docs/architecture/` if patterns or module boundaries changed.
- [ ] Update agent docs in `docs/agents/` when skills or governance rules change.

## Optional updates

- [ ] Update `docs/technical-details/` when adding or changing cross-cutting concerns (security, config).
- [ ] Add or adjust examples to reflect new helpers (e.g., `BaseRestController`, `BaseUseCase`).

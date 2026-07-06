---
name: Release Checklist
about: A release-prep checklist
title: "Release Checklist for x.y.z"
labels:
  - release
assignees: []
---

> [!TIP]
> Create an [issue from this template](https://github.com/DataONEorg/d1_libclient_java/issues/new?template=release_checklist.md)

## Release checklist

- [ ] Create a branch named `task-<issueNum>-release-<releaseVer>-prep`, and do the following:
  - [ ] **pom.xml**: Update `<version>x.y.z</version>`
  - [ ] Grep codebase for previous release number, just in case
  - [ ] **RELEASE-NOTES.md**:
    - [ ] Update for new app
    - [ ] DON'T FORGET TO SET CORRECT RELEASE DATE!
  - [ ] PR & merge release prep branch to `develop`
- [ ] PR & merge `develop` -> `main`
- [ ] Merge `main` back to `develop`
  ```shell
  git checkout develop; git merge main --ff-only
  git push
  ```
- [ ] Tag the release; look up the `<commit-sha>` from `git log`, then:
  ```shell
  # Always use annotated tags (-a) for releases
  git tag -a x.y.z <commit-sha> -m "d1_libclient_java Release x.y.z"
  git push --tags    ## IMPORTANT - DON'T FORGET THIS!
  ```
- [ ] Add to GH `Releases` page

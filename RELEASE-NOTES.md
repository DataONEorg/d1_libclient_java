# d1_libclient_java Release Notes

## 2.4.0 (2026-07-06)

- Intent: Deliver a modernization and maintenance release aligned with current Java runtimes and the current d1_common_java line.
- Security fixes: Updated Bouncy Castle dependencies to patch known security issues and hardened supporting behavior in related client paths.
- New features: Java 17/21/25 compatibility updates, migration of deploy/build flow to maven.dataone.org and wagon-ssh, and CI coverage with Maven verify on pushes and pull requests.
- Bug fixes: Refactored deprecated IOUtils usage, removed unused or extraneous dependencies/plugins/resources, adapted to d1_common API/library changes, and tightened runtime error handling in tests and client integration paths.
- Dependency and tooling updates: Bumped d1_common_java dependency through 2.5.0 and refreshed project/build metadata for current release practices.

## 2.3.1 (2018-02-09)

- Intent: Provide a focused patch release for trust-store updates and NodeList retrieval robustness.
- New features: Added LE and IdenTrust root certificates and refined fallback behavior when CN NodeList retrieval fails.
- Bug fixes: Improved D1Client error messaging and reduced complexity in node location/selection paths used by v1 and v2 implementations.

## 2.3.0 (2016-11-08)

- Intent: Strengthen HTTP client behavior and align exception handling with the 2.3 generation of shared APIs.
- New features: Added client-side HTTP caching, improved keep-alive/connection monitoring controls, and introduced utility classes for safer stream/HTTP-client lifecycle management.
- Bug fixes: Replaced legacy JiBXException usage with MarshallingException, fixed latent NPE/class-cast edge cases in download and resolve flows, and removed outdated caching/dependency pieces.

## 2.2.0 (2016-06-14)

- Intent: Advance 2.2 compatibility and tighten client concurrency/transport defaults.
- New features: Expanded connection-pool and timeout controls and improved monitor-thread behavior for HTTP connection management.
- Bug fixes: Addressed thread-safety issues in request/header handling and synchronized client behavior with evolving DataONE endpoint expectations.

## 2.1.1 (2016-03-15)

- Intent: Deliver an incremental stabilization patch on top of 2.1.0.
- New features: Point-release packaging and branch alignment updates.
- Bug fixes: Minor release-engineering and integration adjustments to keep the 2.1 stream stable.

## 2.1.0 (2016-02-24)

- Intent: Evolve client authorization and v2 API ergonomics for broader DataONE usage.
- New features: Added AuthTokenSession support and related token-aware REST client path selection for multipart operations.
- Bug fixes: Tightened method signatures/exception behavior (including hasReservation and getCN(session) deprecations) and improved node-locator refresh and endpoint fallback logic.

## 2.0.0 (2015-11-24)

- Intent: Establish the 2.x line of the Java client library.
- New features: Added broader v2 service support for package and object retrieval/use cases and refreshed test/build composition for the major release line.
- Bug fixes: Corrected endpoint selection and fallback behavior in get/download paths and fixed stream/resource cleanup issues in multipart client operations.

## 1.3.1 (2014-11-26)

- Intent: Publish a patch-level follow-up to the 1.3.0 line.
- New features: Version/packaging alignment with companion DataONE components.
- Bug fixes: Dependency and release-preparation cleanup.

## 1.3.0 (2014-05-02)

- Intent: Open the 1.3 release branch and stabilize client behavior for the next cycle.
- New features: Consolidated 1.3 branch baseline and compatibility updates.
- Bug fixes: General release hardening and branch synchronization.

## 1.2.5 (2013-08-08)

- Intent: Provide a focused parsing refinement release.
- New features: Refined ResourceMapFactory parsing to support serialized maps where only one side of inverse cito relationships is present.
- Bug fixes: Improved resilience of ResourceMap ingestion for partially expressed relationship graphs.

## 1.2.4 (2013-07-01)

- Intent: Prepare the client library for the CCI-1.2 release train.
- New features: Release alignment with coordinated DataONE component versions.
- Bug fixes: Branch/version stabilization and release packaging updates.

## 1.2.3 (2013-06-05)

- Intent: Keep dependency behavior in sync with updated d1_common_java scope constraints.
- New features: Updated dependency targeting to match new scope decisions in shared libraries.
- Bug fixes: Reduced unnecessary transitive dependency exposure.

## 1.2.2 (2013-06-04)

- Intent: Improve distribution ergonomics for downstream consumers.
- New features: Added jar-with-dependencies Maven assembly configuration.
- Bug fixes: Build/distribution configuration cleanup for packaged client delivery.

## 1.2.1 (2013-02-22)

- Intent: Pull in the d1_common_java 1.1.1 compatibility/temp-file fix line.
- New features: Updated shared dependency baseline.
- Bug fixes: Indirectly resolves temp-file portability behavior through dependency alignment.

## 1.2.0 (2013-02-12)

- Intent: Establish the 1.2 release line.
- New features: 1.2 baseline packaging and API alignment with companion components.
- Bug fixes: Release consistency improvements and branch stabilization.

## 1.1.0 (2013-01-08)

- Intent: Deliver the first 1.1 generation of the client library.
- New features: 1.1 baseline updates and coordinated dependency progression.
- Bug fixes: General maintenance and release preparation changes.

## 1.0.4 (2012-07-11)

- Intent: Continue 1.0 patch-line hardening.
- New features: Patch-level packaging and compatibility updates.
- Bug fixes: Minor maintenance and release corrections.

## 1.0.3 (2012-07-03)

- Intent: Publish a maintenance patch after 1.0.2.
- New features: Incremental release alignment with service/API revisions.
- Bug fixes: Stabilization fixes and release engineering updates.

## 1.0.2 (2012-06-19)

- Intent: Add ORE-generation improvements in the 1.0 maintenance line.
- New features: Incorporated ORE generation behavior using a #aggregation URI.
- Bug fixes: Minor patch-level corrections accompanying the ORE update.

## 1.0.1 (2012-06-05)

- Intent: First general-availability release of d1_libclient_java after release candidates.
- New features: Consolidated RC-era client utilities, multipart behaviors, and service-call wiring into the first stable 1.0 release.
- Bug fixes: Included RC-stage fixes for stream handling, object format/cache behavior, and endpoint compatibility.

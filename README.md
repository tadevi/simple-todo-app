# simple-todo-app
# Android Architecture MVP + Clean Architecture

### Summary
Follow Uncle Bob's principle : https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html).

It includes 3 module layers: domain, data, app.
 + domain: including application bussiness specific, usecase, repository interface. This module should not depend on framework.
 + data: implementation repository, interface of domain modules using facilities of the framework such as database, remote services.
 + app: modules using MVP, Dagger 2 to inject dependency.

<img src="https://github.com/googlesamples/android-architecture/wiki/images/mvp-clean.png" alt="Diagram"/>


### Notes







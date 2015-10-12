## Description

This simple leiningen middleware rewrites the project map with a version number
supplied by a `BUILD_NUMBER` environment variable.  It does not change the
`project.clj` on disk.

It is intended to help you create repeatable builds out of SNAPSHOT builds with
your CI/CD environment.

This plugin is intended to be used along with the use of a plugin like
[lein-ancient](https://github.com/xsc/lein-ancient) in downstream projects.
[lein-ancient](https://github.com/xsc/lein-ancient) can then be used to update
to the newer build.

## Why use it?

Leiningen `-SNAPSHOT` versions are both useful and deeply problematic.  They're
useful because they indicate to a CI server that you want it to integrate and
test a project with the latest updates to a dependency.

Unfortunately though `SNAPSHOT`s don't lead to repeatable builds, and avoiding
`SNAPSHOT`s altogether means you're doing the work of your build server;
managing version numbers.

# Example Usage

Put `[lein-build-env "1.0.0"]` into the `:plugins` vector of your `project.clj`
or `:user` profile.

This simple leiningen middleware rewrites the project map with a version number
supplied by a `BUILD_NUMBER` environment variable.

This is useful if you want to practice continuous delivery with your builds and
don't want to manage `-SNAPSHOT` builds in your CI server or artifact
repositories.

By default the plugin is configured to replace the version substring
`#"\.[0-9]+-SNAPSHOT"` with the value of the `BUILD_NUMBER` environment
variable.  For example, with the following `project.clj`

````clojure
(defproject myproject "1.2.0-SNAPSHOT"
  :description "Just an example"
  :url "https://github.com/rickmoynihan/lein-build-env"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

:plugins [[lein-build-env "1.0.0"]])
````

````
$ env BUILD_NUMBER=3 lein install
Created /Users/rick/repos/myproject/target/myproject-1.2.3.jar
Wrote /Users/rick/repos/myproject/pom.xml
Installed jar and pom into local repo.
````

The plugin will perform its replacement if the `BUILD_NUMBER` environment
variable is set.  If there no `BUILD_NUMBER` environment variable set then it
will leave the project version number untouched.

This default environment variable is the same as that which is set by the
[Jenkins Continuous Integation](http://jenkins-ci.org/).

If however you're using another CI you can change the default environment
variable from `BUILD_NUMBER` to whatever you want by adding a
`:build-number-env` keyword to your project.clj map like so:

````
:build-number-env "BUILD_ID"
````

## License

Copyright © 2015 Rick Moynihan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

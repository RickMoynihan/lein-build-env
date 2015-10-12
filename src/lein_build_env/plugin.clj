(ns lein-build-env.plugin
  (:require [clojure.string :as string]))

(def default-regex #"\.[0-9]+-SNAPSHOT")

(defn middleware [{:keys [build-number-env version-regex] :as project}]
  (if-let [buildnumber (or (System/getenv (or build-number-env "BUILD_NUMBER")))]
    (let [version-regex (or version-regex default-regex)
          version-rewriter (fn [version]
                             (string/replace version
                                             version-regex
                                             (str "." buildnumber)))]
      (update-in project [:version] version-rewriter))
    project))

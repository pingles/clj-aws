(ns clj-aws.core
  (import [com.amazonaws.auth BasicAWSCredentials]))

(defn credentials
  [access-key secret]
  (BasicAWSCredentials. access-key secret))


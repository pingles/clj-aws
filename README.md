# clj-aws

Wrapper for the Amazon AWS SDK for Java.

## Supported APIs

This library is very much a work in progress. The following Amazon API's are currently supported:

* Amazon Simple Email Service

## Usage

To use the Amazon API's you'll need your AWS credentials (access key and secret key). These are available in the [AWS Console](http://aws.amazon.com).

Create your credentials using the `credentials` function:

		(def creds (clj-aws.core/credentials "ACCESS_KEY" "SECRET_KEY"))

### Simple Email Service

		(def client (clj-aws.ses/client creds))
		(def message (clj-aws.ses/message "Subject" "Hello, World!"))
		(def destination (clj-aws.ses/destination ["first@test.com" "second@test.com"]))
		(def sender "sender@origin.com")
		
		(clj-aws.ses/send-email client sender destination message)

## License

Copyright &copy; 2011 Paul Ingles

Distributed under the Eclipse Public License, the same as Clojure.

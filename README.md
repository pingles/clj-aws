# clj-aws

Wrapper for the Amazon AWS SDK for Java.

## Supported APIs

This library is very much a work in progress. The following Amazon API's are currently supported:

* Amazon Simple Email Service
* Amazon Simple Notification Service

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

### Simple Notification Service

Firstly, create a client with your AWS credentials

		(def client (clj-aws.sns/client creds))

Next, it's necessary to define the topic for the set of notifications you would like to issue. 

		(create-topic client "some_topic")

You can use `list-topics` to see all currently created topics (and their ARN).

Once you've defined a topic you can create a subscription to that topic. The library allows subscriptions to be defined with the following functions:

* `http-subscription`
* `email-subscription`
* `sqs-subscription`

All subscriptions are tied to a topic's ARN. This can be found by calling `list-topics`:

		(.getTopicArn (first (list-topics client)))
		;; "arn:aws:sns:us-east-1:121xxx:some_topic"

Then, subscriptions can be bound as follows:

		(subscribe client arn (http-subscription "https://my.site/testing"))

and an email subscription:

		(subscribe client arn (email-subscription "recipient@email.com"))

Notifications can then be posted as follows:

		(def topic-arn "arn:aws:sns:us-east-1:121xxx:some_topic")
		(publish client topic-arn "Test message!!")

## License

Copyright &copy; 2011 Paul Ingles

Distributed under the Eclipse Public License, the same as Clojure.

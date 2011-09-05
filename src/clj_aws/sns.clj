(ns ^{:author "Paul Ingles"
      :description "Interface to Amazon's Simple Notification Service"}
  clj-aws.sns
  (:import [com.amazonaws.services.sns AmazonSNSClient]
           [com.amazonaws.services.sns.model Topic CreateTopicRequest GetTopicAttributesRequest SubscribeRequest PublishRequest]
           [java.net URL]))

(defn client
  [credentials]
  (AmazonSNSClient. credentials))

(defn list-topics
  "Lists all current topics"
  [client]
  (.. client listTopics getTopics))

(defn topic-attributes
  "Loads extended information for a given topic ARN."
  [client topic-arn]
  (->> (GetTopicAttributesRequest. topic-arn)
       (.getTopicAttributes client)
       (.getAttributes)))

(defn create-topic
  "Creates a topic named topic and returns the ARN"
  [client topic]
  (.createTopic client (CreateTopicRequest. topic)))

(defn list-subscriptions
  [client]
  (.. client listSubscriptions getSubscriptions))

(defn http-subscription
  "URL is the URL to receive the notification of the event"
  [url]
  (let [u (URL. url)]
    {:protocol (.getProtocol u)
     :endpoint url
     :format :json}))

(defn email-subscription
  "Email is the email to receive the notification of the event."
  [email & {:keys [json] :or {json false}}]
  (if (true? json)
    {:protocol "email-json"
     :format :json
     :endpoint email}
    {:protocol "email"
     :format :plain
     :endpoint email}))

(defn sqs-subscription
  "Builds an SNS subscription to an SQS queue, identified by it's ARN."
  [queue-arn]
  {:protocol "sqs"
   :format :json
   :endpoint queue-arn})

(defn subscribe
  "Creates a subscription to topic ARN. Subscription"
  [client topic-arn {:keys [protocol endpoint]}]
  (.subscribe client (SubscribeRequest. topic-arn protocol endpoint)))

(defn publish
  "Publishes message (and optional subject) to the topic specified by
   it's ARN."
  ([client topic-arn message]
     (.publish client (PublishRequest. topic-arn message)))
  ([client topic-arn subject message]
     (.publish client (PublishRequest. topic-arn message subject))))

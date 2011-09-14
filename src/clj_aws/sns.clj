(ns ^{:author "Paul Ingles"
      :description "Interface to Amazon's Simple Notification Service"}
  clj-aws.sns
  (:import [com.amazonaws.services.sns AmazonSNSClient]
           [com.amazonaws.services.sns.model Topic CreateTopicRequest DeleteTopicRequest GetTopicAttributesRequest SubscribeRequest PublishRequest]
           [java.net URL]))

(def endpoints {:us-east "sns.us-east-1.amazonaws.com"
                :us-west "sns.us-west-1.amazonaws.com"
                :eu-west "sns.eu-west-1.amazonaws.com"
                :ap-south "sns.ap-southeast-1.amazonaws.com"
                :ap-north "sns.ap-northeast-1.amazonaws.com"})

(defn client
  [credentials & {:keys [region] :or {region :us-east}}]
  (doto (AmazonSNSClient. credentials)
    (.setEndpoint (region endpoints))))

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

(defn delete-topic
  [client topic-arn]
  (.deleteTopic client (DeleteTopicRequest. topic-arn)))

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

(ns ^{:author "Paul Ingles"
      :doc "Consumer code for Amazon's Simple Queueing Service"}
  clj-aws.sqs
  (:import [com.amazonaws.services.sqs AmazonSQSClient]
           [com.amazonaws.services.sqs.model CreateQueueRequest DeleteQueueRequest SendMessageRequest ReceiveMessageRequest]))

(def endpoints {:us-east "sqs.us-east-1.amazonaws.com"
                :us-west "sqs.us-west-1.amazonaws.com"
                :eu-west "sqs.eu-west-1.amazonaws.com"
                :ap-south "sqs.ap-southeast-1.amazonaws.com"
                :ap-north "sqs.ap-northeast-1.amazonaws.com"})

(defn client
  "Creates the SQS client"
  [credentials & {:keys [region] :or {region :us-east}}]
  (doto (AmazonSQSClient. credentials)
    (.setEndpoint (region endpoints))))

(defn list-queues
  "Lists the URLs of all available queues"
  [client]
  (.. client listQueues getQueueUrls))

(defn delete-queue
  "Deletes the queue identified by the specified URL"
  [client queue-url]
  (.deleteQueue client (DeleteQueueRequest. queue-url)))

(defn create-queue
  "Creates a named queue"
  [client name]
  (.createQueue client (CreateQueueRequest. name)))

(defn send-message
  "Sends a String message to the queue specified by the URL"
  [client queue-url message]
  (.sendMessage client (SendMessageRequest. queue-url message)))

(defn receive-message
  "Receives messages from the specified queue"
  [client queue-url]
  (.getMessages (.receiveMessage client (ReceiveMessageRequest. queue-url))))


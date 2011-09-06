(ns ^{:author "Paul Ingles"
      :doc "Consumer code for Amazon's Simple Queueing Service"}
  clj-aws.sqs
  (:import [com.amazonaws.services.sqs AmazonSQSClient]
           [com.amazonaws.services.sqs.model CreateQueueRequest DeleteQueueRequest SendMessageRequest ReceiveMessageRequest]))

(defn client
  "Creates the SQS client"
  [credentials]
  (AmazonSQSClient. credentials))

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


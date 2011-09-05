(ns ^{:author "Paul Ingles"
      :doc "Consumer code for Amazon's Simple Email Service"}
  clj-aws.ses
  (:import [com.amazonaws.services.simpleemail AmazonSimpleEmailServiceClient]
           [com.amazonaws.services.simpleemail.model Body Content Message Destination SendEmailRequest]))

(defn client
  "Creates an Amazon SES client"
  [credentials]
  (AmazonSimpleEmailServiceClient. credentials))

(defn message
  "Creates a plaintext email message that can be sent.
   subject and body should be strings"
  [subject body]
  (Message. (Content. subject) (Body. (Content. body))))

(defn destination
  "Creates a destination for the message. to, cc, and bcc should be collections of strings."
  [to & {:keys [cc bcc]}]
  (doto (Destination.)
    (.setToAddresses to)
    (.setCcAddresses cc)
    (.setBccAddresses bcc)))

(defn send-email
  "Sends message from sender to destination. message should be "
  [client sender destination message]
  (.sendEmail client (SendEmailRequest. sender destination message)))

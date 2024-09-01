#!/bin/bash
awslocal sqs create-queue --queue-name insurance-quote-received
awslocal sqs create-queue --queue-name insurance-policy-created

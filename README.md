## Simple Managed File Transfer

Copy & Paste friendly cURLs ;-)

Get oauth token ... 
```bash
$ token_string=`curl  -d \
 'client_id=test.mft-agent-sender-1&client_secret=test.mft-agent-sender-1&grant_type=client_credentials' \
 -X POST "http://localhost:8080/oauth/token"` && \
 [[ $token_string =~ \"access_token\":\"(.*)\",\"t.* ]] && \
 token=${BASH_REMATCH[1]} && echo $token

```

Get job list ...
```bash
prosdl@mint-vm /tmp $ curl -H "Authorization: Bearer $token" -X GET localhost:8080/rest/jobs?mft-agent=test.mft-agent-sender-1[ {
  "_links" : [ {
    "href" : "http://localhost:8080/rest/jobs/1899598a-e235-44f3-a8b6-87999166b279",
    "rel" : "self"
  } ],
  "administrativeApplication" : "test-application",
  "created" : "2014-04-21T01:54:12.000+0000",
  "cronExpression" : "0/30 * * * * ?",
  "deleteAfterTransferPolicy" : "DELETE_FILE",
  "description" : "send_job for 'test:default-send-job-1'",
  "from" : "test.mft-agent-sender-1",
  "name" : "test:default-send-job-1",
  "srcDir" : "/tmp",
  "srcFilename" : "foo.dat",
  "to" : [ "test.mft-agent-receiver-1" ],
  "type" : "SendJob",
  "useLegacyPGLocking" : false,
  "uuid" : "1899598a-e235-44f3-a8b6-87999166b279"
} ]
```

Write file ...
```bash
$ curl -v -X POST -H "Authorization: Bearer $token" \
  -H "Accept: application/json" --data-binary @test.txt \
  localhost:8080/rest/storage/test.mft-agent-sender-1/outbox/1899598a-e235-44f3-a8b6-87999166b279
```

Read file ...
```bash
$ curl -v -X GET -H "Authorization: Bearer $token" \
  -H "Accept: application/octet-stream" \
  localhost:8080/rest/storage/test.mft-agent-sender-1/inbox/1899598a-e235-44f3-a8b6-87999166b279/96e341f2-2d74-47c8-9cce-cf59f418c844.mft \
  >foo.txt && ls -l foo.txt && cat foo.txt
```

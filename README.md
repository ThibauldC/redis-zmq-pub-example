# Zero MQ or Redis publisher example application
## Run application

You can decide whether to use Redis or ZMQ by passing the `queue` argument.

Create jar:
```
mvn clean install
```

### ZMQ (`--queue=zmq`):
```
java -jar pub-0.0.1-SNAPSHOT.jar --queue=zmq --publisher.channel=fake-channel --data.path=/path/to/data.json --zmq.min-subscribers=1
```

### Redis (`--queue=redis`):
```
java -jar pub-0.0.1-SNAPSHOT.jar --queue=redis --redis.host=10.10.10.6 --redis.port=6379 --publisher.channel=fake-channel  --data.path=/path/to/data.json
```

### Arguments
#### Common

- data.path: Path containing the JSON file with the data
- queue: choose between ZMQ or Redis (default: Redis)
- publisher.channel: channel on which to post the messages

#### ZMQ
- zmq.min-subscribers: minimum number of subscribers needed for the publisher to start sending its messages (default: 1)

#### Redis:
- redis.host: Host of Redis service
- redis.port: Port of Redis service

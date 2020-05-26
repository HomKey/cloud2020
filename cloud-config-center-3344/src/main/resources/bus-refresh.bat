::全局刷新
curl -X POST "http://localhost:3344/actuator/bus-refresh"
::定点刷新
curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"
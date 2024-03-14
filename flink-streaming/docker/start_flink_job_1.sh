/opt/flink/bin/taskmanager.sh start \
    -Djobmanager.rpc.address=flink_job1 \
    -Dtaskmanager.numberOfTaskSlots=10 &&
/opt/flink/bin/standalone-job.sh start-foreground \
    --job-classname io.openlineage.flink.FromTopicToPostgresApplication \
    -Djobmanager.rpc.address=flink_job1 \
    -Dexecution.attached=true